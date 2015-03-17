/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ExtractionServerDAO.java
 *
 * Created on 02-07-2008, 06:39:21 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.ArecontImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.AxisGenericVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.AxisP3301ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BrickcomImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco7ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco7VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141ExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter150ExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter212ExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.ExtractionServer;
import com.scopix.periscope.extractionmanagement.KumGoImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.NextLevelVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.PeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.VMSGatewayVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingExtractionRequest;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 *
 * @author marko.perich
 */
@SpringBean
public class EServerDAO extends DAOHibernate<ExtractionServer, Integer> {

    private static Logger log = Logger.getLogger(EServerDAO.class);

    public ExtractionServer getExtractionServerByServerId(Integer serverId) {
        ExtractionServer extractionServer = null;
        Criteria criteria = this.getSession().createCriteria(ExtractionServer.class);
        criteria.add(Restrictions.eq("serverId", serverId));
        List<ExtractionServer> extractionServers = criteria.list();
        if (extractionServers != null && !extractionServers.isEmpty()) {
            extractionServer = extractionServers.get(0);
        }
        return extractionServer;

    }

    /**
     * @deprecated se debe utilizar getExtractionPlanEnable(String storeName)
     */
    public ExtractionPlan getExtractionPlanEnable() {
        ExtractionPlan extractionPlan = null;
        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.addOrder(Order.desc("id"));
        criteria.add(Restrictions.isNull("expirationDate"));
        List<ExtractionPlan> extractionPlans = criteria.list();
        if (extractionPlans != null && !extractionPlans.isEmpty()) {
            extractionPlan = extractionPlans.get(0);
        }
        return extractionPlan;
    }

    public ExtractionPlan getExtractionPlanEnable(String storeName) {
        ExtractionPlan extractionPlan = null;
        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.addOrder(Order.desc("id"));
        criteria.add(Restrictions.isNull("expirationDate"));
        criteria.add(Restrictions.eq("storeName", storeName));
        List<ExtractionPlan> extractionPlans = criteria.list();
        if (extractionPlans != null && !extractionPlans.isEmpty()) {
            extractionPlan = extractionPlans.get(0);
        }
        return extractionPlan;
    }

    public List<EvidenceExtractionRequest> getEvidenceExtractionRequestForAday(Integer extractionPlanId, int day, Date date) {
        List<EvidenceExtractionRequest> evidenceExtractionRequests = new ArrayList<EvidenceExtractionRequest>();
        Session session = this.getSession();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("  eer.id, eer.requested_time, eer.type, eer.creation_timestamp, eer.remote_request_id, ");
            sql.append("  ef.file_creation_date, ef.upload_date, eer.dtype, eer.allows_extraction_plan_to_past ");
            sql.append("  , eer.evidence_date");
            
            sql.append(" from ");
            sql.append("  evidence_extraction_request eer ");
            sql.append("    left outer join evidence_file ef on( ");
            sql.append("      ef.evidence_extraction_request_id = eer.id and ");
            sql.append("      ef.evidence_date >='").append(DateFormatUtils.format(date, "yyyy-MM-dd 00:00:00")).append("' and ");
            sql.append("      ef.evidence_date <='").append(DateFormatUtils.format(date, "yyyy-MM-dd 23:59:29")).append("' ");
            sql.append("    )");
            sql.append(" where extraction_plan_id=").append(extractionPlanId);
            sql.append(" and day_of_week = ").append(day);
            sql.append(" order by requested_time asc");
            List<Object[]> result = session.createSQLQuery(sql.toString()).list();
            for (Object[] o : result) {
                Integer id = (Integer) o[0];
                Date requestedTime = (Date) o[1];
                String type = (String) o[2];
                Date creationTimestamp = (Date) o[3];
                Integer remoteRequestId = (Integer) o[4];
                Date fileCreatrionDate = (Date) o[5];
                Date uploadDate = (Date) o[6];
                String dtype = (String) o[7];
                Boolean allowsExtractionPlanToPast = (Boolean) o[8];
                Date evidenceDate = (Date) o[9];
                EvidenceExtractionRequest eer = getNewClassByDtype(dtype); //new EvidenceExtractionRequest()
                if (eer instanceof VMSGatewayVideoExtractionRequest) {
                    ((VMSGatewayVideoExtractionRequest) eer).setAllowsExtractionPlanToPast(allowsExtractionPlanToPast);
                }
                eer.setId(id);
                eer.setRequestedTime(requestedTime);
                eer.setType(EvidenceRequestType.valueOf(type));
                eer.setCreationTimestamp(creationTimestamp);
                eer.setRemoteRequestId(remoteRequestId);
                eer.setEvidenceDate(evidenceDate);
                if (fileCreatrionDate != null) {
                    eer.setEvidenceFiles(new ArrayList<EvidenceFile>());
                    EvidenceFile ef = new EvidenceFile();
                    ef.setFileCreationDate(fileCreatrionDate);
                    ef.setUploadDate(uploadDate);
                    eer.getEvidenceFiles().add(ef);
                }
                evidenceExtractionRequests.add(eer);
            }
        } catch (Exception e) {
            log.error("error " + e.getMessage(), e);
        } finally {
            this.releaseSession(session);
        }
        return evidenceExtractionRequests;
    }

    public ExtractionPlan getExtractionPlanForADate(Date date) {
        ExtractionPlan extractionPlan = null;
        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.addOrder(Order.asc("expirationDate"));
        criteria.add(Restrictions.isNotNull("expirationDate"));
        criteria.add(Restrictions.gt("expirationDate", date));
        List<ExtractionPlan> eps = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        if (eps != null && !eps.isEmpty()) {
            extractionPlan = (ExtractionPlan) eps.get(0);
        }
        return extractionPlan;
    }

    public List<ExtractionPlan> getExtractionPlanListForADate(Date date, String storeName) {
        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.addOrder(Order.asc("expirationDate"));
        criteria.add(Restrictions.or(Restrictions.isNull("expirationDate"), Restrictions.gt("expirationDate", date)));
        criteria.add(Restrictions.eq("storeName", storeName));
        List<ExtractionPlan> eps = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return eps;
    }

    /**
     * retorna todos los storeNames de extraction plan que se deben inicializar
     */
    public List<String> getStoreNamesAvailables() {
        List<String> result = null;

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select store_name from extraction_plan  where expiration_date  is null");
            result = getSession().createSQLQuery(sql.toString()).list();
        } catch (DataAccessResourceFailureException e) {
            result = new ArrayList<String>();
            log.warn("No es posible recuperar Store Names Availables [DataAccessResourceFailureException]" + e);
        } catch (IllegalStateException e) {
            result = new ArrayList<String>();
            log.warn("No es posible recuperar Store Names Availables [IllegalStateException]" + e);
        } catch (HibernateException e) {
            result = new ArrayList<String>();
            log.warn("No es posible recuperar Store Names Availables [HibernateException]" + e);
        } finally {
            this.releaseSession(getSession());
        }
        return result;
    }

    private EvidenceExtractionRequest getNewClassByDtype(String dtype) throws ScopixException {
        EvidenceExtractionRequest eer = null;
        if (dtype.equals(BrickcomImageExtractionRequest.class.getSimpleName())) {
            eer = new BrickcomImageExtractionRequest();
        } else if (dtype.equals(AxisP3301ImageExtractionRequest.class.getSimpleName())) {
            eer = new AxisP3301ImageExtractionRequest();
        } else if (dtype.equals(BroadwareImageExtractionRequest.class.getSimpleName())) {
            eer = new BroadwareImageExtractionRequest();
        } else if (dtype.equals(CognimaticsPeopleCounter141ExtractionRequest.class.getSimpleName())) {
            eer = new CognimaticsPeopleCounter141ExtractionRequest();
        } else if (dtype.equals(NextLevelVideoExtractionRequest.class.getSimpleName())) {
            eer = new NextLevelVideoExtractionRequest();
        } else if (dtype.equals(PeopleCountingExtractionRequest.class.getSimpleName())) {
            eer = new PeopleCountingExtractionRequest();
        } else if (dtype.equals(VMSGatewayVideoExtractionRequest.class.getSimpleName())) {
            eer = new VMSGatewayVideoExtractionRequest();
        } else if (dtype.equals(ArecontImageExtractionRequest.class.getSimpleName())) {
            eer = new ArecontImageExtractionRequest();
        } else if (dtype.equals(CognimaticsPeopleCounter212ExtractionRequest.class.getSimpleName())) {
            eer = new CognimaticsPeopleCounter212ExtractionRequest();
        } else if (dtype.equals(AxisGenericVideoExtractionRequest.class.getSimpleName())) {
            eer = new AxisGenericVideoExtractionRequest();
        } else if (dtype.equals(KumGoImageExtractionRequest.class.getSimpleName())) {
            eer = new KumGoImageExtractionRequest();
        } else if (dtype.equals(BroadwareVideoExtractionRequest.class.getSimpleName())) {
            eer = new BroadwareVideoExtractionRequest();
        } else if (dtype.equals(CognimaticsPeopleCounter150ExtractionRequest.class.getSimpleName())) {
            eer = new CognimaticsPeopleCounter150ExtractionRequest();
        } else if (dtype.equals(Cisco7VideoExtractionRequest.class.getSimpleName())) {
            eer = new Cisco7VideoExtractionRequest();
        } else if (dtype.equals(BroadwareHTTPVideoExtractionRequest.class.getSimpleName())) {
            eer = new BroadwareHTTPVideoExtractionRequest();
        } else if (dtype.equals(Cisco7ImageExtractionRequest.class.getSimpleName())) {
            eer = new Cisco7ImageExtractionRequest();
        } else if (dtype.equals(CiscoPeopleCountingExtractionRequest.class.getSimpleName())) {
            eer = new CiscoPeopleCountingExtractionRequest();
        } else if (dtype.equals(VadaroPeopleCountingExtractionRequest.class.getSimpleName())) {
            eer = new VadaroPeopleCountingExtractionRequest();
        } else {
            throw new ScopixException("dtype " + dtype + " no definido");
        }
        return eer;
    }
}
