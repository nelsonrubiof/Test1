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
 * UserLoginHibernateDAO.java
 *
 * Created on 12-05-2008, 06:14:01 PM
 *
 */
package com.scopix.periscope.qualitycontrol.dao;

import com.scopix.periscope.businesswarehouse.transfer.commands.DeleteEvidencesAndProofsCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.RejectBWCommand;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.DateUtils;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.qualitycontrol.RejectedHistory;
import com.scopix.periscope.qualitycontrol.ScheduleRejectThread;
import com.scopix.periscope.qualitycontrol.dto.EvidenceFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.MetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedMetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedSituationFinishedDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.templatemanagement.EvidenceType;
import com.scopix.periscope.templatemanagement.MetricType;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = QualityControlHibernateDAOImpl.class)
public class QualityControlHibernateDAOImpl extends DAOHibernate<EvidenceEvaluation, Integer>
        implements QualityControlHibernateDAO {

    private Logger log = Logger.getLogger(QualityControlHibernateDAOImpl.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public List<ObservedMetricResultDTO> getEvidenceFinishedList(FilteringData filter) throws ScopixException {
        log.debug("start");
        List<ObservedMetricResultDTO> listResult = new ArrayList<ObservedMetricResultDTO>();
        Session session = null;
        try {
            //Al usar la sentencia "as <nombreAlias>", hibernate devuelve en la query el nombre con todo en minusculas
            //por lo tanto al recuperar dicho valor se debe hacer con el nombre en minusculas (de lo contrario no extrae
            //valor alguno), por ejemplo:
            //dto.setMetricType(MetricType.valueOf((String) m.get("metrictype")));
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct data.evidenceId, ");
            sql.append(" ee.id as evidenceEvaluationId, ");
            sql.append(" ee.evidence_result as evidenceEvaluationResult,  ");
            sql.append(" data.observedSituationId, ");
            sql.append("  data.situationId, ");
            sql.append("  data.metricId, ");
            sql.append("  data.metricName,  ");
            sql.append("  data.observedMetricId, ");
            sql.append("  data.metricTemplateId, ");
            sql.append("  data.metricType, ");
            sql.append("  data.evidenceType,  ");
            sql.append("  a.id AS areaId, ");
            sql.append("  a.description AS area, ");
            sql.append("  data.evidenceDate  AS evidenceDate,  ");
            sql.append("  ess.evidence_path || c.name || '/' || s.name || '/' AS evidencePrePath,  ");
            sql.append("  ess.proof_path || c.name || '/' || s.name || '/' AS proofPrePath,  ");
            sql.append("  data.evidencePath, ");
            sql.append("  data.flvPath, ");
            sql.append("  ee.cant_do_reason as cantDoReason, ");
            sql.append("  ee.evaluation_user as userName,  ");
            sql.append("  data.metricEvaluation AS metricResult, ");
            sql.append("  ep.description AS cameraName,  ");
            sql.append("  (select distinct repl.default_evidence_provider from relation_evidence_provider_location repl  ");
            sql.append("   where repl.evidence_provider_from_id = ep.id) as default_evidence_provider,  ");
            sql.append("  (select distinct repl.view_order from relation_evidence_provider_location repl  ");
            sql.append("   where repl.evidence_provider_from_id = ep.id) as view_order ");
            sql.append(" from  ");
            sql.append(" ( select distinct e.id AS evidenceId,  ");
            sql.append("      e.evidence_services_server_id,  ");
            sql.append("         e.evidence_date AS evidenceDate,  ");
            sql.append("         e.evidence_path AS evidencePath,  ");
            sql.append("         e.flv_path AS flvPath,  ");
            sql.append("         m.id AS metricId,  ");
            sql.append("         mt.description as metricName,  ");
            sql.append("         os.id AS observedSituationId,  ");
            sql.append("         os.situation_id as situationId,  ");
            sql.append("         mt.id AS metricTemplateId,  ");
            sql.append("         mt.metric_type_element AS metricType,  ");
            sql.append("         mt.evidence_type_element AS evidenceType,  ");
            sql.append("         m.extraction_plan_metric_id,  ");
            sql.append("         om.id as observedMetricId,  ");
            sql.append("         a.id as areaId, ");
            sql.append("         epc.store_id as storeId, ");
            sql.append("         reee.evidence_evaluation_id as evidenceEvaluationId, ");
            sql.append("         (select me.metric_evaluation_result  ");
            sql.append("          from metric_evaluation me  ");
            sql.append("     where om.id= me.observed_metric_id) as metricEvaluation, ");
            sql.append("      (select distinct ep2.id ");
            sql.append("   from evidence e2, ");
            sql.append("    rel_evidence_request_evidence rere2, ");
            sql.append("    evidence_request er2, ");
            sql.append("    evidence_provider ep2 ");
            sql.append("   where e2.id = e.id ");
            sql.append("    and rere2.evidence_id = e2.id ");
            sql.append("    and rere2.evidence_request_id = er2.id ");
            sql.append("    and er2.evidence_provider_id = ep2.id) as evidenceProviderId ");
            sql.append("    from  ");
            sql.append("         pending_evaluation pe,  ");
            sql.append("         observed_situation os,  ");
            sql.append("         rel_observed_metric_evidence rome,  ");
            sql.append("         observed_metric om,  ");
            sql.append("         metric m,  ");
            sql.append("         metric_template mt, ");
            sql.append("         evidence e ");
            sql.append("          left outer join rel_evidence_evaluation_evidence reee  ");
            sql.append("    on (reee.evidence_id = e.id) , ");
            sql.append("         extraction_plan_metric epm, ");
            sql.append("         extraction_plan_customizing epc, ");
            sql.append("      place a ");
            sql.append("    where   ");
            sql.append("         pe.evaluation_queue = 'OPERATOR'  ");
            sql.append("         and pe.evaluation_state = 'FINISHED'  ");
            sql.append("         and pe.observed_situation_id = os.id  ");
            sql.append("         and om.observed_situation_id = os.id  ");
            sql.append("         and rome.observed_metric_id = om.id  ");
            sql.append("         and rome.evidence_id = e.id  ");
            sql.append("         and om.metric_id = m.id  ");
            sql.append("         and m.metric_template_id = mt.id  ");
            sql.append("         and m.extraction_plan_metric_id = epm.id ");
            sql.append("         and epm.extraction_plan_customizing_id = epc.id ");
            sql.append("         and epc.area_type_id = a.area_type_id ");
            if (filter != null) {
                if (filter.getDate() != null) {
                    sql.append("         and os.observed_situation_date = '");
                    sql.append(DateFormatUtils.format(filter.getDate(), "yyyy-MM-dd")).append("' ");
                }
                if (filter.getArea() != null) {
                    sql.append("         and a.id =").append(filter.getArea());
                }
                if (filter.getStore() != null) {
                    sql.append("         and epc.store_id = ").append(filter.getStore());
                }
            }
            sql.append("    )  as data  ");
            sql.append("    left outer join rel_extraction_plan_metric_evidence_provider repmep  ");
            sql.append("     on (repmep.extraction_plan_metric_id = data.extraction_plan_metric_id ");
            sql.append("     and repmep.evidence_provider_id = data.evidenceProviderId) ");
            sql.append(" left outer join evidence_evaluation ee  ");
            sql.append("  on (ee.observed_metric_id = data.observedMetricId  ");
            sql.append("  and ee.id = data.evidenceEvaluationId  ");
            sql.append("  and ee.rejected = false), ");
            sql.append("    evidence_provider ep,  ");
            sql.append("    place a,  ");
            sql.append("    place s,  ");
            sql.append("    place c,  ");
            sql.append("    evidence_services_server ess  ");
            sql.append("where  ");
            sql.append("  ep.area_id = data.areaId ");
            sql.append("     and ep.store_id = data.storeId ");
            sql.append("     and ep.area_id = a.id  ");
            sql.append("     and a.store_id = s.id  ");
            sql.append("     and s.corporate_id = c.id  ");
            sql.append("     and ess.id = data.evidence_services_server_id  ");
            sql.append("     and repmep.evidence_provider_id = ep.id  ");
            sql.append("ORDER BY observedMetricId, evidenceId, evidenceDate, situationId ASC");

            session = this.getSession();
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            Integer id = null;
            ObservedMetricResultDTO dto = null;
            List<EvidenceDTO> evidenceDTOs = null;
            Set<Integer> evidenceId = new HashSet<Integer>();
            for (Map m : list) {
                if (id == null || !id.equals((Integer) m.get("observedmetricid"))) {
                    if (dto != null) {
                        dto.setEvidences(evidenceDTOs);
                        listResult.add(dto);
                    }
                    evidenceId.clear();
                    id = (Integer) m.get("observedmetricid");
                    dto = new ObservedMetricResultDTO();
                    dto.setSituationId((Integer) m.get("situationid"));
                    dto.setMetricId((Integer) m.get("metricid"));
                    dto.setMetricName((String) m.get("metricname"));
                    dto.setObservedMetricId((Integer) m.get("observedmetricid"));
                    dto.setMetricTemplateId((Integer) m.get("metrictemplateid"));
                    dto.setMetricType(MetricType.valueOf((String) m.get("metrictype")));
                    dto.setAreaId((Integer) m.get("areaid"));
                    dto.setArea((String) m.get("area"));
                    dto.setEvidenceDate((Date) m.get("evidencedate"));
                    dto.setEvidencePrePath((String) m.get("evidenceprepath"));
                    dto.setProofPrePath((String) m.get("proofprepath"));
                    dto.setUserName((String) m.get("username"));
                    if (m.get("metricresult") != null) {
                        dto.setMetricResult((Integer) m.get("metricresult"));
                    } else {
                        dto.setMetricResult(-1);
                    }

                    evidenceDTOs = new ArrayList<EvidenceDTO>();
                    EvidenceDTO evidenceDTO = generateDataEvidenceDTO(m);
                    evidenceDTOs.add(evidenceDTO);
                    evidenceId.add(evidenceDTO.getEvidenceId());

                } else {
                    if (!evidenceId.contains((Integer) m.get("evidenceid"))) {
                        EvidenceDTO evidenceDTO = generateDataEvidenceDTO(m);
                        if (evidenceDTOs == null) {
                            evidenceDTOs = new ArrayList<EvidenceDTO>();
                        }
                        evidenceDTOs.add(evidenceDTO);
                        evidenceId.add(evidenceDTO.getEvidenceId());
                    }
                    if (dto != null && dto.getUserName() == null) {
                        dto.setUserName((String) m.get("username"));
                    }
                }
            }
            if (dto != null) {
                dto.setEvidences(evidenceDTOs);
                listResult.add(dto);
            }
            listResult = this.addProofToResult(filter, listResult);
        } catch (Exception e) {
            log.error("error = " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.evidence", e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.debug("end, result = " + listResult);
        return listResult;
    }

    @Override
    public List<ObservedMetricResultDTO> getEvidenceFinishedListLowes(FilteringData filter) throws ScopixException {
        log.info("start");
        List<ObservedMetricResultDTO> listResult = new ArrayList<ObservedMetricResultDTO>();
        Session session = null;
        try {
            //Al usar la sentencia "as <nombreAlias>", hibernate devuelve en la query el nombre con todo en minusculas
            //por lo tanto al recuperar dicho valor se debe hacer con el nombre en minusculas (de lo contrario no extrae
            //valor alguno), por ejemplo:
            //dto.setMetricType(MetricType.valueOf((String) m.get("metrictype")));
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT ");
            sql.append("e.id AS evidenceId,   ");
            sql.append("ee.id AS evidenceEvaluationId,   ");
            sql.append("ee.evidence_result AS evidenceEvaluationResult,   ");
            sql.append("os.id AS observedSituationId,  ");
            sql.append("os.situation_id as situationId,  ");
            sql.append("m.id AS metricId,   ");
            sql.append("mt.description as metricName,  ");
            sql.append("om.id AS observedMetricId,   ");
            sql.append("mt.id AS metricTemplateId,   ");
            sql.append("mt.metric_type_element AS metricType,   ");
            sql.append("mt.evidence_type_element AS evidenceType,   ");
            sql.append("a.id AS areaId,   ");
            sql.append("a.description AS area,   ");
            sql.append("e.evidence_date AS evidenceDate,   ");
            sql.append("ess.evidence_path || c.name || '/' || s.name || '/' AS evidencePrePath,   ");
            sql.append("ess.proof_path || c.name || '/' || s.name || '/' AS proofPrePath,   ");
            sql.append("e.evidence_path AS evidencePath,   ");
            sql.append("e.flv_path AS flvPath,   ");
            sql.append("ee.cant_do_reason AS cantDoReason,   ");
            sql.append("ee.evaluation_user AS userName ,   ");
            sql.append("(SELECT metric_evaluation_result FROM metric_evaluation where ");
            sql.append("observed_metric_id = om.id) AS metricResult,  ");
            sql.append("ep.description AS cameraName, ");
            sql.append("(SELECT DISTINCT repl.default_evidence_provider FROM relation_evidence_provider_location repl WHERE ");
            sql.append("repl.evidence_provider_from_id = ep.id) AS default_evidence_provider, ");
            sql.append("(SELECT DISTINCT repl.view_order FROM relation_evidence_provider_location repl WHERE ");
            sql.append("repl.evidence_provider_from_id = ep.id) AS view_order ");
            sql.append("FROM   ");
            sql.append("evidence_evaluation ee,   ");
            sql.append("evidence e,   ");
            sql.append("observed_metric om,   ");
            sql.append("observed_situation os,  ");
            sql.append("metric m,   ");
            sql.append("metric_template mt,   ");
            sql.append("place a,   ");
            sql.append("evidence_services_server ess,   ");
            sql.append("place c,   ");
            sql.append("place s,  ");
            sql.append("rel_evidence_request_evidence rer,   ");
            sql.append("evidence_request er,   ");
            sql.append("evidence_provider ep, ");
            sql.append("rel_observed_metric_evidence rome, ");
            sql.append("pending_evaluation pe ");
            sql.append("WHERE  ");
            sql.append("ee.observed_metric_id = om.id ");
            sql.append("AND rome.observed_metric_id = om.id ");
            sql.append("AND e.id = rome.evidence_id ");
            sql.append("AND os.id = om.observed_situation_id ");
            sql.append("AND m.id = om.metric_id ");
            sql.append("AND mt.id = m.metric_template_id ");
            sql.append("AND m.area_id = a.id  ");
            sql.append("AND s.id = a.store_id   ");
            sql.append("AND c.id = s.corporate_id  ");
            sql.append("AND ess.id = e.evidence_services_server_id  ");
            sql.append("AND e.id = rer.evidence_id  ");
            sql.append("AND rer.evidence_request_id = er.id  ");
            sql.append("AND er.evidence_provider_id = ep.id ");
            sql.append("AND ee.rejected = false  ");
            sql.append("AND ee.observed_metric_id = om.id ");
            sql.append("AND pe.observed_situation_id = os.id ");
            sql.append("AND pe.evaluation_queue = 'OPERATOR' ");
            sql.append("AND pe.evaluation_state = 'FINISHED' ");

            if (filter != null) {
                if (filter.getDate() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sql.append(" AND om.observed_metric_date = '").append(sdf.format(filter.getDate())).append("'");
                }
                if (filter.getArea() != null) {
                    sql.append(" AND a.id = ").append(filter.getArea());
                }
                if (filter.getStore() != null) {
                    sql.append(" AND a.store_id = ").append(filter.getStore());
                }
            }
            sql.append(" ORDER BY om.id, e.id, e.evidence_date, os.situation_id ASC");
            session = this.getSession();
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();

            Integer id = null;
            ObservedMetricResultDTO dto = null;
            List<EvidenceDTO> evidenceDTOs = null;
            for (Map m : list) {
                if (id == null || !id.equals((Integer) m.get("observedmetricid"))) {
                    if (dto != null) {
                        dto.setEvidences(evidenceDTOs);
                        listResult.add(dto);
                    }
                    id = (Integer) m.get("observedmetricid");
                    dto = new ObservedMetricResultDTO();
                    dto.setSituationId((Integer) m.get("situationid"));
                    dto.setMetricId((Integer) m.get("metricid"));
                    dto.setMetricName((String) m.get("metricname"));
                    dto.setObservedMetricId((Integer) m.get("observedmetricid"));
                    dto.setMetricTemplateId((Integer) m.get("metrictemplateid"));
                    dto.setMetricType(MetricType.valueOf((String) m.get("metrictype")));
                    dto.setAreaId((Integer) m.get("areaid"));
                    dto.setArea((String) m.get("area"));
                    dto.setEvidenceDate((Date) m.get("evidencedate"));
                    dto.setEvidencePrePath((String) m.get("evidenceprepath"));
                    dto.setProofPrePath((String) m.get("proofprepath"));
                    dto.setUserName((String) m.get("username"));
                    if (m.get("metricresult") != null) {
                        dto.setMetricResult((Integer) m.get("metricresult"));
                    } else {
                        dto.setMetricResult(-1);
                    }

                    evidenceDTOs = new ArrayList<EvidenceDTO>();
                    EvidenceDTO evidenceDTO = generateDataEvidenceDTO(m);
                    evidenceDTOs.add(evidenceDTO);

                } else {
                    EvidenceDTO evidenceDTO = generateDataEvidenceDTO(m);
                    if (evidenceDTOs == null) {
                        evidenceDTOs = new ArrayList<EvidenceDTO>();
                    }
                    evidenceDTOs.add(evidenceDTO);
                }
            }
            if (dto != null) {
                dto.setEvidences(evidenceDTOs);
                listResult.add(dto);
            }
            listResult = this.addProofToResult(filter, listResult);
        } catch (Exception e) {
            log.error("error = " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.evidence", e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.info("end, result = " + listResult.size());
        return listResult;
    }

    private EvidenceDTO generateDataEvidenceDTO(Map m) {
        EvidenceDTO evidenceDTO = new EvidenceDTO();
        evidenceDTO.setEvidenceId((Integer) m.get("evidenceid"));
        evidenceDTO.setEvidenceEvaluationId((Integer) m.get("evidenceevaluationid"));
        evidenceDTO.setCamera((String) m.get("cameraname"));
        evidenceDTO.setEvidenceDate((Date) m.get("evidencedate"));
        evidenceDTO.setEvidenceEvaluationResult((Integer) m.get("evidenceevaluationresult"));
        evidenceDTO.setEvidencePath((String) m.get("evidencepath"));
        evidenceDTO.setCantDoReason((String) m.get("cantdoreason"));
        if (m.get("evidencetype").equals("KUMGO_IMAGE")) {
            evidenceDTO.setEvidenceType(EvidenceType.valueOf("IMAGE"));
        } else {
            evidenceDTO.setEvidenceType(EvidenceType.valueOf((String) m.get("evidencetype")));
        }
        evidenceDTO.setFlvPath((String) m.get("flvpath"));
        evidenceDTO.setDefaultEvidenceProvider(m.get("default_evidence_provider") != null ? (Boolean) m.get(
                "default_evidence_provider") : null);
        evidenceDTO.setViewOrder((Integer) m.get("view_order"));
        return evidenceDTO;
    }

    private List<ObservedMetricResultDTO> addProofToResult(FilteringData filter, List<ObservedMetricResultDTO> listParam) throws
            ScopixException {
        log.debug("start");
        Session session = null;
        try {
            //Buscar los proofs y agregarselos a la evidencia correspondiente
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT  ");
            sql.append("p.proof_date,  ");
            sql.append("p.id as proof_id,  ");
            sql.append("p.path_with_marks,  ");
            sql.append("p.path_without_marks,  ");
            sql.append("p.proof_order, ee.id as evidence_evaluation_id,  ");
            sql.append("om.id as observed_metric_id,  ");
            sql.append("ee.pending_evaluation_id,   ");
            sql.append("p.evidence_id, ");
            sql.append("p.proof_result ");
            sql.append("FROM  ");
            sql.append("proof p, ");
            sql.append("evidence_evaluation ee, ");
            sql.append("observed_metric om, ");
            sql.append("metric m, ");
            sql.append("place a ");
            sql.append("WHERE m.id = om.metric_id ");
            sql.append("AND ee.observed_metric_id = om.id ");
            sql.append("AND a.id = m.area_id ");
            sql.append("AND p.evidence_evaluation_id = ee.id ");
            sql.append("AND ee.rejected = false ");
            if (filter != null) {
                if (filter.getDate() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sql.append(" AND om.observed_metric_date = '");
                    sql.append(sdf.format(filter.getDate()));
                    sql.append("'");
                }
                if (filter.getArea() != null) {
                    sql.append(" AND a.id = ");
                    sql.append(filter.getArea());
                }
                if (filter.getStore() != null) {
                    sql.append(" AND a.store_id = ");
                    sql.append(filter.getStore());
                }
            }
            sql.append(" ORDER BY om.id, p.evidence_id");
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            Collections.sort(listParam);
            for (Map m : list) {
                Integer omId = (Integer) m.get("observed_metric_id");
                Integer evidenceId = (Integer) m.get("evidence_id");
                ProofDTO proofDTO = new ProofDTO();
                proofDTO.setProofId((Integer) m.get("proof_id"));
                proofDTO.setOrder((Integer) m.get("proof_order"));
                proofDTO.setPathWithMarks((String) m.get("path_with_marks"));
                proofDTO.setPathWithoutMarks((String) m.get("path_without_marks"));
                proofDTO.setProofResult((Integer) m.get("proof_result"));

                ObservedMetricResultDTO omrdto = new ObservedMetricResultDTO();
                omrdto.setObservedMetricId(omId);
                int index = Collections.binarySearch(listParam, omrdto);
                if (index >= 0) {
                    omrdto = listParam.get(index);
                    List<EvidenceDTO> evidences = omrdto.getEvidences();
                    Collections.sort(listParam);
                    EvidenceDTO evidenceDTO = new EvidenceDTO();
                    evidenceDTO.setEvidenceId(evidenceId);
                    index = Collections.binarySearch(evidences, evidenceDTO);
                    if (index >= 0) {
                        evidenceDTO = evidences.get(index);
                        evidenceDTO.getProofs().add(proofDTO);
                    }
                }
            }
        } catch (Exception e) {
            log.error("error = " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.evidence", e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e, e);
            }
        }
        log.debug("end, result = " + listParam);
        return listParam;
    }

    @Override
    public List<ProofDTO> getProofPerEvidenceEvaluation(int evidenceEvaluationId) throws ScopixException {
        log.debug("start");
        List<ProofDTO> proofDTOs = null;
        try {
            StringBuilder hql = new StringBuilder();
            hql.append("SELECT DISTINCT ");
            hql.append("p.pathWithMarks AS pathWithMarks, ");
            hql.append("p.pathWithoutMarks AS pathWithoutMarks, ");
            hql.append("p.proofOrder AS order ");
            hql.append("FROM ");
            hql.append("Proof p ");
            hql.append("WHERE ");
            hql.append("p.evidenceEvaluation.id = ");
            hql.append(evidenceEvaluationId);
            proofDTOs = this.getSession().createQuery(hql.toString()).
                    setResultTransformer(Transformers.aliasToBean(ProofDTO.class)).list();
        } catch (Exception e) {
            log.error("error = " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("label.proofPerEvidence", e);
        }
        log.debug("end, result = " + proofDTOs);
        return proofDTOs;
    }

    @Override
    public List<EvidenceFinishedDTO> getEvidenceFinishedList(Date start, Date end, boolean rejected) throws ScopixException {
        List<EvidenceFinishedDTO> listResult = new ArrayList<EvidenceFinishedDTO>();
        Session session = null;
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        Connection con = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct ");
            sql.append("p.description as area , ");
            sql.append("e.evidence_date, ");
            sql.append("ee.evidence_result, ");
            sql.append("ee.cant_do_reason, ");
            sql.append("om.id as metric_id, ");
            sql.append("e.evidence_path, ");
            sql.append("ep.description as camera, ");
            sql.append("mt.metric_type_element, ");
            sql.append("mt.evaluation_instruction, ");
            sql.append("ee.evaluation_user, ");
            sql.append("ee.rejected, ");
            sql.append("ee.evaluation_date, ");
            sql.append("ee.id AS evidence_evaluation_id, ");
            sql.append("ee.init_evaluation, ");
            sql.append("ee.end_evaluation, ");
            sql.append("ee.evaluation_time_in_seconds, ");
            sql.append("store.description AS store_name ");
            sql.append("from evidence_evaluation ee, ");
            sql.append("evidence e, ");
            sql.append("observed_metric om, ");
            sql.append("metric m, ");
            sql.append("metric_template mt, ");
            sql.append("place p, ");
            sql.append("place store, ");
            sql.append("rel_evidence_request_evidence rel, ");
            sql.append("evidence_request er, ");
            sql.append("evidence_provider ep, ");
            sql.append("rel_evidence_evaluation_evidence reee ");
            sql.append("where reee.evidence_id = e.id ");
            sql.append("and reee.evidence_evaluation_id = ee.id ");
            sql.append("and ee.observed_metric_id = om.id ");
            sql.append("and om.metric_id = m.id ");
            sql.append("and m.metric_template_id = mt.id ");
            sql.append("and m.area_id = p.id ");
            sql.append("and rel.evidence_id = e.id ");
            sql.append("and rel.evidence_request_id = er.id ");
            sql.append("and er.evidence_provider_id = ep.id ");
            sql.append("and e.evidence_date >= '");
            sql.append(sdf.format(start));
            sql.append(" 00:00:00' ");
            sql.append("and e.evidence_date <= '");
            sql.append(sdf.format(end));
            sql.append(" 23:59:59' ");
            sql.append("and m.store_id = store.id ");

            if (!rejected) {
                sql.append("and ee.rejected = false ");
            }
            session = this.getSession();

            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            Set<Integer> eeId = new HashSet<Integer>();
            for (Map m : list) {
                if (!eeId.contains((Integer) m.get("evidence_evaluation_id"))) {
                    EvidenceFinishedDTO dto = new EvidenceFinishedDTO();
                    dto.setArea((String) m.get("area"));
                    dto.setEvidenceDate((Date) m.get("evidence_date"));
                    dto.setEvidenceEvaluationResult((Integer) m.get("evidence_result"));
                    dto.setCantDoReason((String) m.get("cant_do_reason"));
                    dto.setObservedMetricId((Integer) m.get("metric_id"));
                    dto.setEvidencePath((String) m.get("evidence_path"));
                    dto.setProvider((String) m.get("camera"));
                    dto.setMetricType(MetricType.valueOf((String) m.get("metric_type_element")));
                    dto.setEvaluationInstruction((String) m.get("evaluation_instruction"));
                    dto.setUserName((String) m.get("evaluation_user"));
                    dto.setRejected((Boolean) m.get("rejected"));
                    dto.setEvaluationDate((Date) m.get("evaluation_date"));
                    dto.setInitEvaluationDate((Date) m.get("init_evaluation"));
                    dto.setEndEvaluationDate((Date) m.get("end_evaluation"));
                    dto.setEvaluationTimeInSeconds(m.get("evaluation_time_in_seconds") == null
                            ? null : ((BigInteger) m.get("evaluation_time_in_seconds")).longValue());
                    dto.setStoreName((String) m.get("store_name"));
                    listResult.add(dto);

                    eeId.add((Integer) m.get("evidence_evaluation_id"));
                }
            }
        } catch (Exception e) {
            log.error("Error " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("tab.qualitycontrol.summary", e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }
        log.debug("end, result = " + listResult);

        return listResult;
    }

    @Override
    public void rejectsEvaluations(List<Integer> observedMetricIds, String comments) throws ScopixException {
        log.debug("start");
        Session session = null;
        StringBuilder sql = new StringBuilder();
        List<Integer> indicatorValuesList = new ArrayList<Integer>();
        List<Integer> observedSituationEvaluationList = new ArrayList<Integer>();
        List<Integer> pendingEvaluationIds = new ArrayList<Integer>();

        try {
            String idParameter = observedMetricIds.toString().replaceAll("(\\[)|(\\])", "");

            log.debug("idparameters = " + idParameter);
            session = this.getSession();

            //select indicator values
            sql.append("select id from indicator_values");
            sql.append(" where");
            sql.append(" observed_situation_id in ");
            sql.append(" (");
            sql.append("         select distinct observed_situation_id from observed_metric");
            sql.append("         where");
            sql.append("         id in (");
            sql.append(idParameter);
            sql.append(")");
            sql.append(" )");
            Query query = session.createSQLQuery(sql.toString());
            List<Integer> list = query.list();
            for (Integer val : list) {
                indicatorValuesList.add(val);
            }
            //select observed situation evaluation
            sql = new StringBuilder();
            sql.append("select id from observed_situation_evaluation");
            sql.append(" where");
            sql.append(" observed_situation_id in ");
            sql.append(" (");
            sql.append("           select distinct observed_situation_id from observed_metric");
            sql.append("           where");
            sql.append("           id in (");
            sql.append(idParameter);
            sql.append(")");
            sql.append(" )");
            query = session.createSQLQuery(sql.toString());
            list = query.list();
            for (Integer val : list) {
                observedSituationEvaluationList.add(val);
            }
            ScheduleRejectThread scheduleRejectThread = new ScheduleRejectThread();
            scheduleRejectThread.setObservedSituationEvaluationList(observedSituationEvaluationList);
            scheduleRejectThread.setIndicatorValuesList(indicatorValuesList);
            scheduleRejectThread.setObservedMetricIds(observedMetricIds);

            scheduleRejectThread.start();

            //Esta seccion sacarla a un thread aparte con un numero de reintentos.
            //Call RejectBWCommand
//            RejectBWCommand bWCommand = new RejectBWCommand();
//            bWCommand.execute(observedSituationEvaluationList, indicatorValuesList);
//
//            DeleteEvidencesAndProofsCommand deleteEAP = new DeleteEvidencesAndProofsCommand();
//            deleteEAP.execute(observedMetricIds);
            //delete observed situation evaluation
            sql = new StringBuilder();
            sql.append("delete from observed_situation_evaluation");
            sql.append(" where");
            sql.append(" observed_situation_id in");
            sql.append(" (");
            sql.append("         select distinct observed_situation_id from observed_metric");
            sql.append("         where");
            sql.append("         id in (");
            sql.append(idParameter);
            sql.append(")");
            sql.append(" )");
            session.createSQLQuery(sql.toString()).executeUpdate();

            //delete indicator values
            sql = new StringBuilder();
            sql.append("delete from indicator_values");
            sql.append(" where");
            sql.append(" observed_situation_id in");
            sql.append(" (");
            sql.append("         select distinct observed_situation_id from observed_metric");
            sql.append("         where");
            sql.append("         id in (").append(idParameter).append(")");
            sql.append(" )");

            session.createSQLQuery(sql.toString()).executeUpdate();

            //update evaluation state = null on observed situation
            sql = new StringBuilder();
            sql.append("update observed_situation set evaluation_state = null");
            sql.append(" where");
            sql.append(" id in ");
            sql.append(" (");
            sql.append("     select observed_situation_id from observed_metric");
            sql.append("     where");
            sql.append("     id in (").append(idParameter).append(")");
            sql.append(" )");
            session.createSQLQuery(sql.toString()).executeUpdate();

            //delete metric evaluation from observed metric
            session.createSQLQuery("delete from metric_evaluation"
                    + " where"
                    + " observed_metric_id in "
                    + " (" + idParameter + ")").executeUpdate();

            //update evaluation state = null on observed metric
            session.createSQLQuery("update observed_metric set evaluation_state = null"
                    + " where"
                    + " id in"
                    + " (" + idParameter + ")").executeUpdate();

            //find pending evaluations associated to evidence evaluation
            sql = new StringBuilder();
            sql.append("select distinct pe.id as pending_evaluation_id from pending_evaluation pe, observed_situation os, ");
            sql.append("observed_metric om ");
            sql.append("where pe.observed_situation_id = os.id ");
            sql.append("and om.observed_situation_id = os.id ");
            sql.append("and om.id in (").append(idParameter).append(") ");

            query = session.createSQLQuery(sql.toString());
            list = query.list();

            //add comments on reject history with pending evaluation associated.
            for (Integer val : list) {
                pendingEvaluationIds.add(val);

                PendingEvaluation pe = new PendingEvaluation();
                pe.setId(val);

                RejectedHistory rj = new RejectedHistory();
                //Esta hora corresponde a la hora del servidor de la aplicación java. Ojo cuando la BD se ubique en un 
                //servidor distinto al de la aplicación java
                rj.setRejectDate(DateUtils.now());
                rj.setPendingEvaluation(pe);
                rj.setRejectComment(comments);

                session.save(rj);
            }
            session.flush();
            //update rejected = true on evidence evaluation
            session.createSQLQuery("update evidence_evaluation set rejected = true where"
                    + " observed_metric_id in (" + idParameter + ")").executeUpdate();

            session.createSQLQuery("update evidence_evaluation set rejected = true, pending_evaluation_id = null where"
                    + " pending_evaluation_id in (" + pendingEvaluationIds.toString().replaceAll("(\\[)|(\\])", "") + ")").
                    executeUpdate();

            //update state of pending evaluation
            session.createSQLQuery("update pending_evaluation set evaluation_state = 'ENQUEUED', priority = 1, "
                    + "operator_queue_id = -1 where id in ("
                    + pendingEvaluationIds.toString().replaceAll("(\\[)|(\\])", "")
                    + ")").executeUpdate();

        } catch (Exception e) {
            log.error("Error " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("tab.qualitycontrol.summary", e);
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }

        log.debug("end");
    }

    @Override
    public List<ObservedSituationFinishedDTO> getObservedSituationFinishedList(FilteringData filters) {
        log.info("start");
        if (filters != null) {
            log.debug("filters [area:" + filters.getArea() + "][store:" + filters.getStore() + "]"
                    + "[date:" + filters.getDate() + "]");
        }
        List<ObservedSituationFinishedDTO> result = new ArrayList<ObservedSituationFinishedDTO>();
        StringBuilder sql = new StringBuilder();
//        sql.append("select distinct os.id, ");
//        sql.append("  p.description as product, ");
//        sql.append("     st.name,");
//        sql.append("     e.evidence_date,");
//        sql.append("     ee.evaluation_user");
//        sql.append(" from observed_situation os,");
//        sql.append("   pending_evaluation pe,   ");
//        sql.append("   product p, ");
//        sql.append("   situation s, ");
//        sql.append("   situation_template st,");
//        sql.append("   evidence e,");
//        sql.append("   evidence_evaluation ee,");
//        sql.append("   rel_evidence_evaluation_evidence reee,");
//        sql.append("   place a,");
//        sql.append("   observed_metric om,");
//        sql.append("   metric m");
//        sql.append(" where ");
//        sql.append("   pe.evaluation_state = 'FINISHED'");
//        sql.append("   and (pe.evaluation_queue = 'OPERATOR' or pe.evaluation_queue = 'AUTOMATIC_ANALITICS')");
//        sql.append("   and pe.observed_situation_id = os.id   ");
//        sql.append("   and os.situation_id = s.id ");
//        sql.append("   and s.situation_template_id = st.id ");
//        sql.append("   and st.product_id = p.id ");
//        sql.append("   and ee.pending_evaluation_id = pe.id");
//        sql.append("   and ee.rejected = false");
//        sql.append("   and reee.evidence_evaluation_id = ee.id");
//        sql.append("   and reee.evidence_id = e.id");
//        sql.append("   and st.area_type_id = a.area_type_id");
//        sql.append("   and ee.observed_metric_id = om.id");
//        sql.append("   and om.metric_id = m.id");
//        sql.append("   and m.store_id = a.store_id");
//        sql.append("   and m.area_id = a.id");
//
//        if (filters != null) {
//            if (filters.getArea() != null) {
//                sql.append("  and a.id = ").append(filters.getArea());
//            }
//            if (filters.getStore() != null) {
//                sql.append("  and a.store_id = ").append(filters.getStore());
//            }
//            if (filters.getDateFilter() != null) {
//                sql.append("   and om.observed_metric_date = '").append(filters.getDateFilter()).append("' ");
//            }
//            if (filters.getInitialTime() != null) {
//                sql.append("  and e.evidence_date >= '").append(filters.getDateFilter()).append(" ");
//                sql.append(filters.getInitialTime()).append(":00").append("' ");
//            }
//            if (filters.getEndTime() != null) {
//                sql.append("   and e.evidence_date <= '").append(filters.getDateFilter()).append(" ");
//                sql.append(filters.getEndTime()).append(":59").append("' ");
//            }
//        }

        sql.append("select distinct os.id, ");
        sql.append("    p.description as product, ");
        sql.append("    st.name,");
        sql.append("    om.evidence_date, ");
        sql.append("    ee.evaluation_user ");
        sql.append(" from observed_situation os, ");
        sql.append("    pending_evaluation pe,   ");
        sql.append("    product p, ");
        sql.append("    situation s, ");
        sql.append("    situation_template st, ");
        sql.append("    evidence_evaluation ee, ");
        sql.append("    observed_metric om,");
        sql.append("    metric m");
        sql.append(" where ");
        sql.append("    pe.evaluation_state = 'FINISHED' ");
        sql.append("    and (pe.evaluation_queue = 'OPERATOR' or pe.evaluation_queue = 'AUTOMATIC_ANALITICS') ");
        sql.append("    and pe.observed_situation_id = os.id   ");
        sql.append("    and os.situation_id = s.id ");
        sql.append("    and s.situation_template_id = st.id ");
        sql.append("    and st.product_id = p.id ");
        sql.append("    and ee.pending_evaluation_id = pe.id ");
        sql.append("    and ee.rejected = false ");
        sql.append("    and ee.observed_metric_id = om.id ");
        sql.append("    and om.metric_id = m.id ");
        if (filters != null) {
            if (filters.getArea() != null) {
                sql.append("    and m.area_id = ").append(filters.getArea());
            }
            if (filters.getStore() != null) {
                sql.append("    and m.store_id = ").append(filters.getStore());
            }
            if (filters.getDateFilter() != null) {
                sql.append("    and om.observed_metric_date = '").append(filters.getDateFilter()).append("' ");
            }
            if (filters.getInitialTime() != null) {
                sql.append("    and om.evidence_date >= '").append(filters.getDateFilter()).append(" ");
                sql.append(filters.getInitialTime()).append(":00' ");
            }
            if (filters.getEndTime() != null) {
                sql.append("    and om.evidence_date <= '").append(filters.getDateFilter()).append(" ");
                sql.append(filters.getEndTime()).append(":59' ");
            }
        }

        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
        for (Map<String, Object> row : list) {
            ObservedSituationFinishedDTO dto = new ObservedSituationFinishedDTO();
            dto.setObservedSituationId((Integer) row.get("id"));
            dto.setProduct((String) row.get("product"));
            dto.setEvidenceDate(DateFormatUtils.format((Date) row.get("evidence_date"), "HH:mm"));
            dto.setEvaluationUser((String) row.get("evaluation_user"));
            dto.setSituationTemplateName((String) row.get("name"));
            result.add(dto);
        }
        log.info("end");
        return result;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MetricResultDTO> getMetricResultByObservedSituation(Integer situationFinishedId) {
        log.info("start");
        log.debug("osId:" + situationFinishedId);
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("  m.id as metricId, ");
        sql.append("  mt.description as metricName, ");
        sql.append("  mt.id as metricTemplateId, ");
        sql.append("  mt.operator_description as operatorDescription, ");
        sql.append("  om.id as observedMetricId, ");
        sql.append("  mt.metric_type_element as metricType, ");
        sql.append("  me.metric_evaluation_result as metricResult,");
        sql.append("  st.name as situtionTemplateName, ");
        sql.append("  ess.proof_path || c.name || '/' || store.name || '/' as proofPrePath, ");
        sql.append("  ess.evidence_path || c.name || '/' || store.name || '/' as evidencePrePath, ");
        sql.append("  a.id as areaId, ");
        sql.append("  a.description as area ");
        sql.append("from ");
        sql.append("  observed_metric om ");
        sql.append("  left outer join metric_evaluation me on (me.observed_metric_id = om.id), ");
        sql.append("  observed_situation os, ");
        sql.append("  metric m, ");
        sql.append("  metric_template mt, ");
        sql.append("  situation s, ");
        sql.append("  situation_template st, ");
        sql.append("  place a, ");
        sql.append("  place store, ");
        sql.append("  place c, ");
        sql.append("  evidence_services_server ess ");
        sql.append("where ");
        sql.append("  os.id =").append(situationFinishedId);
        sql.append("  and om.observed_situation_id = os.id ");
        sql.append("  and m.id = om.metric_id ");
        sql.append("  and mt.id = m.metric_template_id ");
        sql.append("  and s.id = os.situation_id  ");
        sql.append("  and st.id = s.situation_template_id ");
        sql.append("  and a.id = m.area_id ");
        sql.append("  and store.id = a.store_id ");
        sql.append("  and c.id = store.corporate_id ");
        sql.append("  and ess.id = store.evidence_services_server_id ");
        sql.append(" order by m.metric_order");

        List<MetricResultDTO> result = new ArrayList<MetricResultDTO>();
        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
        for (Map<String, Object> row : list) {
            MetricResultDTO dto = new MetricResultDTO();
            dto.setMetricId((Integer) row.get("metricid"));
            dto.setMetricName((String) row.get("metricname"));
            dto.setMetricTemplateId((Integer) row.get("metrictemplateid"));
            dto.setObservedMetricId((Integer) row.get("observedmetricid"));
            dto.setMetricResult((Integer) row.get("metricresult"));
            dto.setMetricType((String) row.get("metricType"));
            dto.setSitutionTemplateName((String) row.get("situtiontemplatename"));
            dto.setProofPrePath((String) row.get("proofprepath"));
            dto.setEvidencePrePath((String) row.get("evidenceprepath"));
            dto.setAreaId((Integer) row.get("areaid"));
            dto.setArea((String) row.get("area"));
            dto.setDescriptionOperator((String) row.get("operatordescription"));

            result.add(dto);
        }
        log.info("end");
        return result;
    }

    private void addProofToEvidence(EvidenceDTO dto) {
        StringBuilder sql = new StringBuilder();
        sql.append("select p.id as proof_id,  ");
        sql.append(" p.proof_order, ");
        sql.append(" p.path_with_marks, ");
        sql.append(" p.path_without_marks, ");
        sql.append(" p.proof_result ");
        sql.append(" from proof  p ");
        sql.append(" where evidence_id = ").append(dto.getEvidenceId());
        sql.append(" and evidence_evaluation_id = ").append(dto.getEvidenceEvaluationId());

        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
        for (Map<String, Object> row : list) {
            ProofDTO proofDTO = new ProofDTO();
            proofDTO.setProofId((Integer) row.get("proof_id"));
            proofDTO.setOrder((Integer) row.get("proof_order"));
            proofDTO.setPathWithMarks((String) row.get("path_with_marks"));
            proofDTO.setPathWithoutMarks((String) row.get("path_without_marks"));
            proofDTO.setProofResult((Integer) row.get("proof_result"));
            dto.getProofs().add(proofDTO);
        }
    }

    @Override
    public List<EvidenceDTO> getEvidencesByMetric(Integer metricId, Integer situationFinishId) {
        List<EvidenceDTO> lista = new ArrayList<EvidenceDTO>();
        StringBuilder sql = new StringBuilder();

        sql.append("select ");
        sql.append(" data1.evidenceId, data2.evidenceevaluationid, data1.cameraName, data1.evidenceDate, ");
        sql.append(" data2.evidenceEvaluationResult, data1.evidencePath, data2.cantDoReason, data1.evidenceType, data1.flvPath,");
        sql.append(" (select distinct repl.default_evidence_provider from relation_evidence_provider_location repl  ");
        sql.append("   where repl.evidence_provider_from_id = data1.epid) as default_evidence_provider,  ");
        sql.append("  (select distinct repl.view_order from relation_evidence_provider_location repl  ");
        sql.append("   where repl.evidence_provider_from_id = data1.epid) as view_order ");

        sql.append("from ");
        sql.append(" (Select ep.id as epid, ");
        sql.append("     e.id as evidenceId, ");
        sql.append("  e.evidence_date AS evidenceDate, ");
        sql.append("  ep.description as cameraName,e.evidence_path AS evidencePath, mt.evidence_type_element AS evidenceType, ");
        sql.append("  e.flv_path AS flvPath ");
        sql.append(" from observed_metric om,  ");
        sql.append("  metric m, ");
        sql.append("     extraction_plan_metric epm, ");
        sql.append("     rel_extraction_plan_metric_evidence_provider repmep, ");
        sql.append("     evidence_provider ep, ");
        sql.append("     rel_observed_metric_evidence rome, ");
        sql.append("     evidence e, ");
        sql.append("     rel_evidence_request_evidence rere, ");
        sql.append("     evidence_request er, metric_template mt ");
        sql.append(" where om.metric_id = ").append(metricId);
        sql.append(" and om.observed_situation_id = ").append(situationFinishId);
        sql.append(" and om.metric_id =  m.id  ");
        sql.append(" and m.extraction_plan_metric_id = epm.id ");
        sql.append(" and repmep.extraction_plan_metric_id = epm.id ");
        sql.append(" and ep.id = repmep.evidence_provider_id ");
        sql.append(" and rome.observed_metric_id = om.id ");
        sql.append(" and rome.evidence_id = e.id ");
        sql.append(" and rere.evidence_id = e.id ");
        sql.append(" and rere.evidence_request_id = er.id ");
        sql.append(" and er.evidence_provider_id = ep.id ");
        sql.append(" and er.metric_id = om.metric_id ");
        sql.append(" and mt.id = m.metric_template_id ");
        sql.append(" ) as data1 ");
        sql.append(" left outer join ");
        sql.append(" (select distinct  ");
        sql.append("  ep.id as epid, ");
        sql.append("     e.id as evidenceId, ");
        sql.append("  ee.id as evidenceEvaluationId, ");
        sql.append("  ee.evidence_result as evidenceEvaluationResult, ee.cant_do_reason as cantDoReason ");
        sql.append(" from observed_metric om, ");
        sql.append("   evidence_evaluation ee, ");
        sql.append("      rel_evidence_evaluation_evidence reee, ");
        sql.append("      evidence e, ");
        sql.append("      rel_evidence_request_evidence rere, ");
        sql.append("      evidence_request er, ");
        sql.append("      evidence_provider ep ");
        sql.append(" where om.metric_id = ").append(metricId);
        sql.append(" and om.observed_situation_id = ").append(situationFinishId);
        sql.append(" and ee.observed_metric_id = om.id ");
        sql.append(" and ee.rejected = false ");
        sql.append(" and reee.evidence_evaluation_id = ee.id ");
        sql.append(" and reee.evidence_id = e.id ");
        sql.append(" and rere.evidence_id = e.id ");
        sql.append(" and rere.evidence_request_id = er.id ");
        sql.append(" and er.evidence_provider_id = ep.id ");
        sql.append(" and er.metric_id = om.metric_id) as data2 ");
        sql.append(" on data1.epid = data2.epid");

        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
        Set<Integer> evId = new HashSet<Integer>();
        for (Map<String, Object> row : list) {
            if (!evId.contains((Integer) row.get("evidenceid"))) {
                EvidenceDTO evidenceDto = generateDataEvidenceDTO(row);
                addProofToEvidence(evidenceDto);
                lista.add(evidenceDto);
                evId.add(evidenceDto.getEvidenceId());
            }
        }
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("viewOrder", Boolean.FALSE);
        SortUtil.sortByColumn(cols, lista);
        return lista;
    }

    @Override
    public EvidenceDTO getEvidenceDTO(Integer evidenceId, Integer evidenceEvaluationId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select  ");
        sql.append(" data1.evidenceId, data2.evidenceevaluationid, data1.cameraName, data1.evidenceDate,  ");
        sql.append(" data2.evidenceEvaluationResult, data1.evidencePath, data2.cantDoReason, data1.evidenceType, data1.flvPath,");
        sql.append(" (select distinct repl.default_evidence_provider from relation_evidence_provider_location repl   ");
        sql.append("   where repl.evidence_provider_from_id = data1.epid) as default_evidence_provider,   ");
        sql.append("  (select distinct repl.view_order from relation_evidence_provider_location repl   ");
        sql.append("   where repl.evidence_provider_from_id = data1.epid) as view_order  ");
        sql.append(" ");
        sql.append("from  ");
        sql.append(" (Select distinct ep.id as epid,  ");
        sql.append("    e.id as evidenceId,  ");
        sql.append("    e.evidence_date AS evidenceDate,  ");
        sql.append("    ep.description as cameraName,e.evidence_path AS evidencePath, mt.evidence_type_element AS evidenceType,");
        sql.append("    e.flv_path AS flvPath  ");
        sql.append(" from observed_metric om,   ");
        sql.append("  metric m,  ");
        sql.append("     extraction_plan_metric epm,  ");
        sql.append("     rel_extraction_plan_metric_evidence_provider repmep,  ");
        sql.append("     evidence_provider ep,  ");
        sql.append("     rel_observed_metric_evidence rome,  ");
        sql.append("     evidence e,  ");
        sql.append("     rel_evidence_request_evidence rere,  ");
        sql.append("     evidence_request er, metric_template mt  ");
        sql.append(" where  ");
        sql.append("     e.id = ").append(evidenceId);
        sql.append("     and om.metric_id =  m.id   ");
        sql.append("     and m.extraction_plan_metric_id = epm.id  ");
        sql.append("     and repmep.extraction_plan_metric_id = epm.id  ");
        sql.append("     and ep.id = repmep.evidence_provider_id  ");
        sql.append("     and rome.observed_metric_id = om.id  ");
        sql.append("     and rome.evidence_id = e.id  ");
        sql.append("     and rere.evidence_id = e.id  ");
        sql.append("     and rere.evidence_request_id = er.id  ");
        sql.append("     and er.evidence_provider_id = ep.id  ");
        sql.append("     and er.metric_id = om.metric_id  ");
        sql.append("     and mt.id = m.metric_template_id  ");
        sql.append(" ) as data1  ");
        sql.append(" left outer join  ");
        sql.append("     (select distinct   ");
        sql.append("      ep.id as epid,  ");
        sql.append("      e.id as evidenceId,  ");
        sql.append("      ee.id as evidenceEvaluationId,  ");
        sql.append("      ee.evidence_result as evidenceEvaluationResult, ee.cant_do_reason as cantDoReason  ");
        sql.append("     from observed_metric om,  ");
        sql.append("       evidence_evaluation ee,  ");
        sql.append("       rel_evidence_evaluation_evidence reee,  ");
        sql.append("       evidence e,  ");
        sql.append("       rel_evidence_request_evidence rere,  ");
        sql.append("       evidence_request er,  ");
        sql.append("       evidence_provider ep  ");
        sql.append("     where  ");
        sql.append("     e.id = ").append(evidenceId);
        if (evidenceEvaluationId != null && evidenceEvaluationId > 0) {
            sql.append("     and ee.id = ").append(evidenceEvaluationId);
        }
        sql.append("     and ee.observed_metric_id = om.id  ");
        sql.append("     and ee.rejected = false  ");
        sql.append("     and reee.evidence_evaluation_id = ee.id  ");
        sql.append("     and reee.evidence_id = e.id  ");
        sql.append("     and rere.evidence_id = e.id  ");
        sql.append("     and rere.evidence_request_id = er.id  ");
        sql.append("     and er.evidence_provider_id = ep.id  ");
        sql.append("     and er.metric_id = om.metric_id) as data2  ");
        sql.append("  on data1.epid = data2.epid");

        EvidenceDTO evidenceDto = null;
        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());
        Set<Integer> evId = new HashSet<Integer>();
        for (Map<String, Object> row : list) {
            if (!evId.contains((Integer) row.get("evidenceid"))) {
                evidenceDto = generateDataEvidenceDTO(row);
                addProofToEvidence(evidenceDto);
                evId.add(evidenceDto.getEvidenceId());
            }
        }
        return evidenceDto;
    }

    @Override
    public void rejectsEvaluationsByObservedSituation(Set<Integer> observedSituationIds, String comments)
            throws ScopixException {
        log.debug("start");
        Session session = null;
        StringBuilder sql = new StringBuilder();
        List<Integer> indicatorValuesList = new ArrayList<Integer>();
        List<Integer> observedSituationEvaluationList = new ArrayList<Integer>();
        List<Integer> pendingEvaluationIds = new ArrayList<Integer>();

        try {

            log.debug("idparameters = " + StringUtils.join(observedSituationIds, ","));
            session = this.getSession();

            //select indicator values
            sql.append("select id from indicator_values");
            sql.append(" where");
            sql.append(" observed_situation_id in ");
            sql.append(" (");
            sql.append(StringUtils.join(observedSituationIds, ","));
            sql.append(" )");
            Query query = session.createSQLQuery(sql.toString());
            List<Integer> list = query.list();
            for (Integer val : list) {
                indicatorValuesList.add(val);
            }
            //select observed situation evaluation
            sql.setLength(0);
            sql.append("select id from observed_situation_evaluation");
            sql.append(" where");
            sql.append(" observed_situation_id in ");
            sql.append(" (");
            sql.append(StringUtils.join(observedSituationIds, ","));
            sql.append(" )");
            query = session.createSQLQuery(sql.toString());
            list = query.list();
            for (Integer val : list) {
                observedSituationEvaluationList.add(val);
            }
            //Call RejectBWCommand
//            RejectBWCommand bWCommand = new RejectBWCommand();
//            bWCommand.execute(observedSituationEvaluationList, indicatorValuesList);

            sql.setLength(0);
            sql.append("select id from observed_metric where ");
            sql.append(" observed_situation_id in ");
            sql.append(" (");
            sql.append(StringUtils.join(observedSituationIds, ","));
            sql.append(" )");
            query = session.createSQLQuery(sql.toString());
            List<Integer> observedMetricIds = query.list();
//            DeleteEvidencesAndProofsCommand deleteEAP = new DeleteEvidencesAndProofsCommand();
//            deleteEAP.execute(observedMetricIds);

            ScheduleRejectThread scheduleRejectThread = new ScheduleRejectThread();
            scheduleRejectThread.setObservedSituationEvaluationList(observedSituationEvaluationList);
            scheduleRejectThread.setIndicatorValuesList(indicatorValuesList);
            scheduleRejectThread.setObservedMetricIds(observedMetricIds);
            scheduleRejectThread.init();
            scheduleRejectThread.start();

            //delete observed situation evaluation
            sql = new StringBuilder();
            sql.append("delete from observed_situation_evaluation");
            sql.append(" where");
            sql.append(" observed_situation_id in");
            sql.append(" (");
            sql.append(StringUtils.join(observedSituationIds, ","));
            sql.append(" )");
            session.createSQLQuery(sql.toString()).executeUpdate();

            //delete indicator values
            sql = new StringBuilder();
            sql.append("delete from indicator_values");
            sql.append(" where");
            sql.append(" observed_situation_id in");
            sql.append(" (");
            sql.append(StringUtils.join(observedSituationIds, ","));
            sql.append(" )");

            session.createSQLQuery(sql.toString()).executeUpdate();

            //update evaluation state = null on observed situation
            sql = new StringBuilder();
            sql.append("update observed_situation set evaluation_state = null");
            sql.append(" where");
            sql.append(" id in ");
            sql.append(" (");
            sql.append(StringUtils.join(observedSituationIds, ","));
            sql.append(" )");
            session.createSQLQuery(sql.toString()).executeUpdate();

            //delete metric evaluation from observed metric
            session.createSQLQuery("delete from metric_evaluation"
                    + " where"
                    + " observed_metric_id in "
                    + " ( select id from observed_metric where observed_situation_id "
                    + "    in (" + StringUtils.join(observedSituationIds, ",") + " ) "
                    + ")").executeUpdate();

            //update evaluation state = null on observed metric
            session.createSQLQuery("update observed_metric set evaluation_state = null"
                    + " where"
                    + " observed_situation_id in"
                    + " (" + StringUtils.join(observedSituationIds, ",") + ")").executeUpdate();

            //find pending evaluations associated to evidence evaluation
            sql = new StringBuilder();
            sql.append("select distinct pe.id as pending_evaluation_id ");
            sql.append(" from pending_evaluation pe, observed_situation os ");
            sql.append("where pe.observed_situation_id = os.id ");
            sql.append("and os.id in(").append(StringUtils.join(observedSituationIds, ",")).append(") ");

            query = session.createSQLQuery(sql.toString());
            list = query.list();

            //add comments on reject history with pending evaluation associated.
            for (Integer val : list) {
                pendingEvaluationIds.add(val);

                PendingEvaluation pe = new PendingEvaluation();
                pe.setId(val);

                RejectedHistory rj = new RejectedHistory();
                //Esta hora corresponde a la hora del servidor de la aplicación java. Ojo cuando la BD se ubique en un
                //servidor distinto al de la aplicación java
                rj.setRejectDate(DateUtils.now());
                rj.setPendingEvaluation(pe);
                rj.setRejectComment(comments);

                session.save(rj);
            }
            session.flush();
            //update rejected = true on evidence evaluation
            session.createSQLQuery("update evidence_evaluation set rejected = true where"
                    + " observed_metric_id in ("
                    + " select id from observed_metric  where observed_situation_id "
                    + "    in (" + StringUtils.join(observedSituationIds, ",") + ")"
                    + ")").executeUpdate();

            session.createSQLQuery("update evidence_evaluation set rejected = true, pending_evaluation_id = null  where"
                    + " pending_evaluation_id in (" + pendingEvaluationIds.toString().replaceAll("(\\[)|(\\])", "") + ")").
                    executeUpdate();

            //update state of pending evaluation
            session.createSQLQuery("update pending_evaluation set evaluation_state = 'ENQUEUED', priority = 1, "
                    + "operator_queue_id = -1 where id in ("
                    + pendingEvaluationIds.toString().replaceAll("(\\[)|(\\])", "")
                    + ")").executeUpdate();

        } catch (Exception e) {
            log.error("Error " + e, e);
            //"periscopeexception.list.error", new String[]{
            throw new ScopixException("tab.qualitycontrol.summary");
        } finally {
            try {
                this.releaseSession(session);
            } catch (Exception e) {
                log.error("Error " + e.getMessage(), e);
            }
        }

        log.debug("end");
    }
}
