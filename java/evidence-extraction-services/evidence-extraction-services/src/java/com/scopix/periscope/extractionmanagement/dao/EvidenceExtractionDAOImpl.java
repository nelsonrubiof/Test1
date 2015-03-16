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
 * EvidenceExtractionDAOImpl.java
 *
 * Created on 03-04-2009, 10:40:32 AM
 *
 */
package com.scopix.periscope.extractionmanagement.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.RequestTimeZone;
import com.scopix.periscope.extractionmanagement.ScopixJob;
import com.scopix.periscope.extractionmanagement.ScopixListenerJob;
import com.scopix.periscope.extractionmanagement.SituationMetricExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = EvidenceExtractionDAOImpl.class)
public class EvidenceExtractionDAOImpl extends DAOHibernate<BusinessObject, Integer> implements EvidenceExtractionDAO {

    Logger log = Logger.getLogger(EvidenceExtractionDAOImpl.class);

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource ds) {
        setJdbcTemplate(new JdbcTemplate(ds));
    }

    /**
     * Obtiene las situaciones a partir del ID del sensor.
     *
     * @param camId ID del sensor
     * @return Lista de situaciones
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    @Override
    public List<SituationRequest> findSituationsForASensor(String sensorId) throws ScopixException {
        List<SituationRequest> listSituations = null;
        try {
            Criteria criteria = this.getSession().createCriteria(SituationRequest.class);
            criteria.createCriteria("situationSensors").add(Restrictions.eq("name", sensorId));
            criteria.createCriteria("extractionPlan").add(Restrictions.isNull("expirationDate"));
            //.add(Restrictions.eq("storeName", storeName));
            listSituations = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        } catch (Exception e) {
            throw new ScopixException(e);
        }

        return listSituations;
    }

    /**
     * Obtiene las situaciones a partir del ID de la camara.
     *
     * @param camId ID de la camara
     * @return Lista de situaciones
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    @Override
    public List<SituationRequest> findSituationsForAEvidenceProvider(String camId) throws ScopixException {
        List<SituationRequest> listSituations = null;
        try {
            Criteria criteria = this.getSession().createCriteria(SituationRequest.class);
            criteria.createCriteria("metricRequests").createCriteria("evidenceProviderRequests").add(Restrictions.eq(
                "uniqueDeviceId", camId));
            criteria.createCriteria("extractionPlan").add(Restrictions.isNull("expirationDate"));
            //.add(Restrictions.eq("storeName", storeName));
            listSituations = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        } catch (Exception e) {
            throw new ScopixException(e);
        }

        return listSituations;
    }

    /**
     * Obtiene un numero unico que servira de identificacion para el proceso de evidencia automatica
     *
     * @return numero de identificacion
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    @Override
    public Integer getProcessId() throws ScopixException {
        Session session = this.getSession();
        Integer id = 0;
        try {
            String sqlId = "select nextval('get_process_id_seq') as SEC";
            BigInteger idTemp = (BigInteger) session.createSQLQuery(sqlId).uniqueResult();
            id = idTemp.intValue();
        } catch (Exception e) {
            throw new ScopixException(e);
        } finally {
            this.releaseSession(session);
            log.debug("end");
        }
        return id;
    }

    @Override
    public List<SituationMetricExtractionRequest> getSituationMetricExtractionRequest(Integer erId) throws ScopixException {

        Criteria criteria = this.getSession().createCriteria(SituationMetricExtractionRequest.class);
        criteria.createCriteria("evidenceExtractionRequest").add(Restrictions.eq("id", erId));
        List<SituationMetricExtractionRequest> list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return list;
    }

    @Override
    public List<String> getEvidenceExtractionRequestByPriorization(Vector<String> fileNames) { //, String storeName
        log.info("start");
        //recuperar nombres de archivos priorizados segun entrada de files
        StringBuilder sql = new StringBuilder();
        sql.append("select ef.filename, ef.evidence_date, ");
        sql.append(" (case when (eer.priorization is null) then 99  else eer.priorization end )as priorization, ");
        sql.append(" ep.store_name");
        sql.append(" from evidence_file ef, evidence_extraction_request eer, extraction_plan ep ");
        sql.append(" where ef.filename  in ('").append(StringUtils.join(fileNames, "','")).append("') ");
        sql.append(" and eer.id = ef.evidence_extraction_request_id ");
        sql.append(" and eer.extraction_plan_id = ep.id ");
        //sql.append(" and ep.store_name = '").append(storeName.toString()).append("'");
        sql.append(" order by eer.priorization asc, ef.evidence_date asc, ep.store_name ");
        List<Object[]> ret = getSession().createSQLQuery(sql.toString()).list();
        List<String> retString = new LinkedList<String>();
        for (Object[] o : ret) {
            String fileName = (String) o[0];
            retString.add(fileName);
        }
        log.info("end");
        return retString;
    }

    @Override
    public List<SituationRequest> findSituationsForASensor(String sensorId, Date date) throws ScopixException {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct sr.id, sr.duration, sr.frecuency, sr.last_process, sr.priorization, sr.random_camera, ");
        sql.append(" sr.situation_template_id, sr.extraction_plan_id, ");
        sql.append("(ep.expiration_date AT TIME ZONE ep.time_zone_id) as expiration_date_store ");
        sql.append(" from situation_request sr , extraction_plan ep, situation_sensor ss ");
        sql.append(" where ss.name = '").append(sensorId).append("' ");
        sql.append(" and sr.id = ss.situation_request_id ");
        sql.append(" and sr.extraction_plan_id = ep.id ");
        sql.append("and ((ep.expiration_date AT TIME ZONE ep.time_zone_id) >=");
        sql.append("'").append(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss")).append("' ");
        sql.append("or ep.expiration_date is null) ");
        sql.append(" order by expiration_date_store");
        List<SituationRequest> aux = getSession().createSQLQuery(sql.toString()).addEntity(SituationRequest.class).
            list();
        List<SituationRequest> listSituations = new ArrayList<SituationRequest>();
        Date expirationDate = null;
        //recuperamos solo los SituationRequest que corresponden al Primer EP encontrado
        for (SituationRequest sr : aux) {
            if (expirationDate == null || expirationDate.equals(sr.getExtractionPlan().getExpirationDate())) {
                listSituations.add(sr);
                expirationDate = sr.getExtractionPlan().getExpirationDate();
            }
        }
        log.info("end");
        return listSituations;
    }

    @Override
    public List<SituationRequest> findSituationsForAEvidenceProvider(String camId, Date date) throws ScopixException {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct sr.id, sr.duration, sr.frecuency, sr.last_process, sr.priorization, sr.random_camera, ");
        sql.append(" sr.situation_template_id, sr.extraction_plan_id, ");
        sql.append(" (ep.expiration_date AT TIME ZONE ep.time_zone_id) as expiration_date_store ");
        sql.append(" from situation_request sr, extraction_plan ep, metric_request mr, evidence_provider_request epr ");
        sql.append(" where epr.unique_device_id = '").append(camId).append("' ");
        sql.append(" and mr.id = epr.metric_request_id ");
        sql.append(" and sr.id = mr.situation_request_id ");
        sql.append(" and sr.extraction_plan_id = ep.id ");
        sql.append(" and ((ep.expiration_date AT TIME ZONE ep.time_zone_id) >=");
        sql.append("'").append(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss")).append("' or ep.expiration_date is null)");
        sql.append(" order by expiration_date_store");
        List<SituationRequest> aux = getSession().createSQLQuery(sql.toString()).addEntity(SituationRequest.class).
            list();
        List<SituationRequest> listSituations = new ArrayList<SituationRequest>();
        Date expirationDate = null;
        //recuperamos solo los SituationRequest que corresponden al Primer EP encontrado
        for (SituationRequest sr : aux) {
            if (expirationDate == null || expirationDate.equals(sr.getExtractionPlan().getExpirationDate())) {
                listSituations.add(sr);
                expirationDate = sr.getExtractionPlan().getExpirationDate();
            }
        }
        log.info("end");
        return listSituations;
    }

    @Override
    public List<ScopixJob> getAllJobs() {
        List<ScopixJob> l = new ArrayList<ScopixJob>();
        StringBuilder sql = new StringBuilder();

        //todo revisar interval para people counter  18 minute
//        sql.append("select distinct eer.day_of_week, ep.store_name, eer.requested_time, ");
//        sql.append(" case when provider.dtype like '%PeopleCounter%' then timezone(ep.time_zone_id, ");
//        sql.append(" eer.requested_time at TIME ZONE  '").append(TimeZone.getDefault().getID()).append("')::time without time zone + interval ' 18 minute' ");
//        sql.append(" when eer.length_in_secs is null then timezone(ep.time_zone_id, ");
//        sql.append("  eer.requested_time at TIME ZONE  '").append(TimeZone.getDefault().getID()).append("')::time without time zone ");
//        sql.append("  else timezone(ep.time_zone_id, eer.requested_time at TIME ZONE '").append(TimeZone.getDefault().getID()).append("' ");
//        sql.append(" )::time without time zone + interval ' 1 second' * eer.length_in_secs end as hour_execution, ");
//        sql.append(" ep.time_zone_id ");
//        sql.append(" from evidence_extraction_request eer, extraction_plan ep, evidence_provider provider ");
//        sql.append(" where  ");
//        sql.append(" extraction_plan_id = ep.id ");
//        sql.append(" and ep.expiration_date is null ");
//        sql.append(" and eer.type != 'REAl_RANDOM' "); //se excluyen los random
//        sql.append(" and provider.id = eer.evidence_provider_id ");
//        sql.append(" order by 1,3 ");
        sql.append("select distinct eer.day_of_week, ep.store_name, eer.requested_time, eer.dtype, ");
        sql.append(" case when (provider.dtype like '%PeopleCounter%' or provider.dtype like '%PeopleCounting%') then ");
        sql.append("	timezone('").append(TimeZone.getDefault().getID()).append("',");
        sql.append(" ((date_trunc('day' ,now())::date || ' ' || eer.requested_time)::timestamp without time zone) ");
        sql.append("at TIME ZONE ep.time_zone_id");
        sql.append("	)::time without time zone + interval ' 18 minute' ");
        sql.append("      when eer.length_in_secs is null then ");
        sql.append("	timezone('").append(TimeZone.getDefault().getID()).append("',");
        sql.append(" ((date_trunc('day' ,now())::date || ' ' || eer.requested_time)::timestamp without time zone) ");
        sql.append("at TIME ZONE ep.time_zone_id)::time without time zone ");
        sql.append("	else ");
        sql.append("	timezone('").append(TimeZone.getDefault().getID()).append("',");
        sql.append(" ((date_trunc('day' ,now())::date || ' ' || eer.requested_time)::timestamp without time zone) ");
        sql.append("at TIME ZONE ep.time_zone_id)::time without time zone + interval ' 1 second' * eer.length_in_secs ");
        sql.append(" + interval ' 5 minute'"); //se agregan 3 min para todos los videos
        sql.append("	end ");
        sql.append("      as hour_execution, ");
        sql.append(" ep.time_zone_id ");
        sql.append(" from evidence_extraction_request eer, extraction_plan ep, evidence_provider provider ");
        sql.append(" where  ");
        sql.append(" extraction_plan_id = ep.id ");
        sql.append(" and ep.expiration_date is null ");
        sql.append(" and eer.type = 'SCHEDULED'  ");
        sql.append(" and provider.id = eer.evidence_provider_id ");
        sql.append(" order by 1,3");

        List<Map<String, Object>> ret = getJdbcTemplate().queryForList(sql.toString());
        Map<String, ScopixJob> jobs = new HashMap<String, ScopixJob>();

        for (Map<String, Object> row : ret) {
            Integer dayOfWeek = (Integer) row.get("day_of_week");
            String request = (String) DateFormatUtils.format((Date) row.get("requested_time"), "HH:mm:ss");
            String execution = (String) DateFormatUtils.format((Date) row.get("hour_execution"), "HH:mm:ss");
            String timeZone = (String) row.get("time_zone_id");
            String storeName = (String) row.get("store_name");
            String dtype = (String) row.get("dtype");

            //se determina diferencia horaria para calcular dia de ejecucion
            //diff 5 o -5
            double diff = TimeZoneUtils.getDiffInHoursTimeZone(timeZone);
            //(-5)
            dayOfWeek = TimeZoneUtils.calculateDayOfWeek(execution, diff, dayOfWeek);

            String key = dayOfWeek + "|" + execution;
            ScopixJob job = jobs.get(key);
            if (job == null) {
                job = new ScopixJob(dayOfWeek, execution);
                l.add(job);
                jobs.put(key, job);
            }

            RequestTimeZone rt = new RequestTimeZone(request, timeZone, storeName, dtype);
            job.getRequestTimeZones().add(rt);
        }
        return l;
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EvidenceExtractionRequest> getEvidenceExtractionRequestByRequestTimeZone(List<RequestTimeZone> requestTimeZones,
        Integer dayOfWeek) throws ScopixException {
        List<EvidenceExtractionRequest> ret = new ArrayList<EvidenceExtractionRequest>();

        StringBuilder sql = new StringBuilder();
        for (RequestTimeZone requestTimeZone : requestTimeZones) {
            if (sql.length() != 0) {
                sql.append(" union all ");
            }
            sql.append("select ");
            sql.append(" ep.store_name, ep.time_zone_id, "); //extraction plan
            sql.append(" provider.id as provider_id, provider.dtype as provider_dtype, provider.protocol, "); //provider
            sql.append(" provider.ip_address, provider.port, provider.user_name, provider.password, ");
            sql.append(" eer.id as eer_id, eer.dtype as eer_dtype, eer.type, eer.creation_timestamp, "); // evidence Extraction
            sql.append(" eer.evidence_date, eer.requested_time, eer.priorization ");
            sql.append(" from evidence_extraction_request  eer, extraction_plan ep, evidence_provider provider ");
            sql.append(" where ");
            sql.append(" eer.extraction_plan_id = ep.id ");
            sql.append(" and ep.store_name ='").append(requestTimeZone.getStoreName()).append("' ");
            sql.append(" and ep.expiration_date isnull ");
            sql.append(" and eer.requested_time  = '").append(requestTimeZone.getRequest()).append("' ");
            sql.append(" and eer.day_of_week  = ").append(dayOfWeek);
            sql.append(" and eer.evidence_provider_id = provider.id ");

            if (requestTimeZone.getDtype() != null) {
                sql.append(" and eer.dtype = '").append(requestTimeZone.getDtype()).append("' ");
            }
        }
        sql.append(" order by priorization ");
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql.toString());
        for (Map<String, Object> row : rows) {

            ExtractionPlan ep = new ExtractionPlan();
            ep.setStoreName((String) row.get("store_name"));
            ep.setTimeZoneId((String) row.get("time_zone_id"));

            EvidenceProvider provider = ScopixUtilities.findEvidenceProviderByClassName((String) row.get("provider_dtype"));
            provider.setId((Integer) row.get("provider_id"));
            ScopixUtilities.setterInObject(provider, (String) row.get("protocol"), "protocol");
            ScopixUtilities.setterInObject(provider, (String) row.get("ip_address"), "ipAddress");
            ScopixUtilities.setterInObject(provider, (String) row.get("port"), "port");
            ScopixUtilities.setterInObject(provider, (String) row.get("user_name"), "userName");
            ScopixUtilities.setterInObject(provider, (String) row.get("password"), "password");

            EvidenceExtractionRequest eer = ScopixUtilities.findEvidenceExtractionRequestByClassName((String) row.get("eer_dtype"));
            eer.setExtractionPlan(ep);
            eer.setEvidenceProvider(provider);
            eer.setId((Integer) row.get("eer_id"));
            eer.setType(EvidenceRequestType.valueOf((String) row.get("type")));

            eer.setCreationTimestamp((Date) row.get("creation_timestamp"));
            eer.setEvidenceDate((Date) row.get("evidence_date"));
            eer.setRequestedTime((Date) row.get("requested_time"));

            ret.add(eer);
            //llenar objeto
            //crear ep y asignarlo
            //crear provider y asignarlo
        }

        return ret;
    }

    @Override
    public List<ScopixJob> getRealRandomJobs() {
        List<ScopixJob> ret = new ArrayList<ScopixJob>();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct srr.day_of_week, ep.store_name, srr.initial_time, timezone(ep.time_zone_id,");
        sql.append("srr.initial_time at TIME ZONE '").append(TimeZone.getDefault().getID()).append("')::time without time zone ");
        sql.append(" as hour_execution, ep.time_zone_id ");
        sql.append("from situation_request_range srr, situation_request sr, extraction_plan ep ");
        sql.append("where srr.range_type ='REAL_RANDOM' ");
        sql.append("and sr.id = srr.situation_request_id ");
        sql.append("and ep.id = sr.extraction_plan_id ");
        sql.append("and ep.expiration_date is null ");
        sql.append("order by 1,3 ");

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql.toString());
        Map<String, ScopixJob> jobs = new HashMap<String, ScopixJob>();
        for (Map<String, Object> row : rows) {
            Integer dayOfWeek = (Integer) row.get("day_of_week");
            String request = (String) DateFormatUtils.format((Date) row.get("initial_time"), "HH:mm:ss");
            String execution = (String) DateFormatUtils.format((Date) row.get("hour_execution"), "HH:mm:ss");
            String timeZone = (String) row.get("time_zone_id");
            String storeName = (String) row.get("store_name");
            //se determina diferencia horaria para calcular dia de ejecucion
            //diff 5 o -5
            double diff = TimeZoneUtils.getDiffInHoursTimeZone(timeZone);
            //(-5)
            dayOfWeek = TimeZoneUtils.calculateDayOfWeek(execution, diff, dayOfWeek);

            String key = dayOfWeek + "|" + execution;
            ScopixJob job = jobs.get(key);
            if (job == null) {
                job = new ScopixJob(dayOfWeek, execution);
                ret.add(job);
                jobs.put(key, job);
            }

            RequestTimeZone rt = new RequestTimeZone(request, timeZone, storeName, null);
            job.getRequestTimeZones().add(rt);
        }
        return ret;
    }

    @Override
    public List<ScopixListenerJob> getListenerJobs(String storeName) {
        List<ScopixListenerJob> ret = new ArrayList<ScopixListenerJob>();
        StringBuilder sql = new StringBuilder();
        //loop_name for vadaro listener is queue_name
        sql.append("select distinct ep.store_name, ep.time_zone_id, evp.protocol, evp.ip_address, evp.port, evp.name, evp.loop_name");
        sql.append(" from situation_request_range srr, situation_request sr, extraction_plan ep, evidence_provider_request epr, evidence_provider evp ");
        sql.append("where srr.range_type ='AUTOMATIC_EVIDENCE' ");
        sql.append("and sr.id = srr.situation_request_id ");
        sql.append("and ep.id = sr.extraction_plan_id ");
        sql.append("and epr.situation_request_id = sr.id ");
        sql.append("and epr.evidence_provider_id = evp.id ");
        sql.append("and evp.dtype = 'VadaroEvidenceProvider' ");
        sql.append("and ep.expiration_date is null ");
        sql.append(" and ep.store_name = '" + storeName + "' ");
        sql.append("order by 1,3 ");

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql.toString());
        for (Map<String, Object> row : rows) {
            String storeNameListener = (String) row.get("store_name");
            String timeZone = (String) row.get("time_zone_id");
            String protocol = (String) row.get("protocol");
            String ipAddress = (String) row.get("ip_address");
            String port = (String) row.get("port");
            String provider = (String) row.get("name");
            String queue = (String) row.get("loop_name");

            ret.add(new ScopixListenerJob(protocol, ipAddress, port, storeNameListener, timeZone, provider, queue));
        }
        return ret;
    }

    @Override
    public List<SituationRequestRange> getSituationRequestRangeRealRandomByRequestTimeZone(
        List<RequestTimeZone> requestTimeZones, Integer dayOfWeek) {
        log.info("start");
        List<SituationRequestRange> situationRequestRanges = new ArrayList<SituationRequestRange>();
        StringBuilder sql = new StringBuilder();
        for (RequestTimeZone rtz : requestTimeZones) {
            if (sql.length() > 0) {
                sql.append(" union all ");
            }
            sql.append("select srr.* , ep.store_name, ep.id as ep_id, ep.time_zone_id from ");
            sql.append("extraction_plan ep, ");
            sql.append("situation_request sr, ");
            sql.append("situation_request_range srr ");
            sql.append("where srr.day_of_week  = ").append(dayOfWeek).append(" ");
            sql.append("and srr.initial_time ='").append(rtz.getRequest()).append("' ");
            sql.append("and sr.id = srr.situation_request_id ");
            sql.append("and ep.id = sr.extraction_plan_id ");
            sql.append("and ep.expiration_date isnull ");
            sql.append("and srr.range_type='REAL_RANDOM' ");
            sql.append("and ep.store_name in('").append(rtz.getStoreName()).append("')");
        }

        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql.toString());
        for (Map<String, Object> row : rows) {
            SituationRequestRange range = new SituationRequestRange();
            range.setInitialTime((Date) row.get("initial_time"));
            range.setEndTime((Date) row.get("end_time"));
            range.setDuration((Integer) row.get("duration"));
            range.setDayOfWeek((Integer) row.get("day_of_week"));
            range.setFrecuency((Integer) row.get("frecuency"));
            range.setSamples((Integer) row.get("samples"));

            SituationRequest sr = new SituationRequest();
            sr.setId((Integer) row.get("situation_request_id"));

            ExtractionPlan ep = new ExtractionPlan();
            ep.setId((Integer) row.get("ep_id"));
            ep.setStoreName((String) row.get("store_name"));
            ep.setTimeZoneId((String) row.get("time_zone_id"));

            range.setSituationRequest(sr);
            sr.setExtractionPlan(ep);

            situationRequestRanges.add(range);

        }
        log.info("end " + situationRequestRanges.size());
        return situationRequestRanges;
    }
}
