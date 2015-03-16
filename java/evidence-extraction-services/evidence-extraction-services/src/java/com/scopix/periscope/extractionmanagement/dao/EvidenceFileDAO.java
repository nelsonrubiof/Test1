/*
 * Copyright (c) 2007, SCOPIX. All rights reserved. This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and disclosure. It is protected by copyright, patent
 * and other intellectual and industrial property laws. Copy, reverse engineering, disassembly or decompilation of all or part of
 * it, except to the extent required to obtain interoperability with other independently created software as specified by a
 * license agreement, is prohibited. EvidenceFileDAO.java Created on 18-06-2008, 08:02:15 PM
 */
package com.scopix.periscope.extractionmanagement.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 *
 * @author marko.perich
 */
@SpringBean(rootClass = EvidenceFileDAO.class)
public class EvidenceFileDAO extends DAOHibernate<EvidenceFile, Integer> {

    private static Logger log = Logger.getLogger(EvidenceFileDAO.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EvidenceFile findEvidenceFileByFileNameLite(String filename) {
        log.info("start");
        EvidenceFile evidenceFile = null;
        log.info("end");
        return evidenceFile;
    }

    public EvidenceFile findEvidenceFileByFileName(String filename) throws ScopixException {
        log.info("start");
        EvidenceFile evidenceFile = null;
        StringBuilder sql = new StringBuilder("select ef.id as ef_id, ef.alternative_file_name, ef.downloaded_file,");
        sql.append(" ef.evidence_date ef_evidence_date, ef.file_creation_date, ef.filename, ef.no_check_nagios, ef.upload_date,");
        sql.append(" ef.evidence_extraction_request_id, eer.dtype eer_dtype, eer.id eer_id, eer.creation_timestamp,");
        sql.append(" eer.day_of_week, eer.evidence_date eer_evidence_date, eer.priorization, eer.process_id,");
        sql.append(" eer.remote_request_id, eer.requested_time, eer.type, eer.length_in_secs,");
        sql.append(" eer.allows_extraction_plan_to_past, eer.evidence_provider_id, eer.extraction_plan_id, eer.live, epc.id epc_id,");
        sql.append(" epc.expiration_date, epc.store_id, epc.store_name, epc.time_zone_id, epc.extraction_server_id,");
        sql.append(" ep.dtype ep_dtype, ep.id ep_id, ep.description, ep.device_id, ep.name, ep.unique_device_id,");
        sql.append(" ep.ip_address, ep.password, ep.user_name, ep.port, ep.protocol, ep.query, ep.uuid, ep.gallery_dir,");
        sql.append(" ep.extraction_plan_to_past, ep.provider, ep.provider_type, ep.loop_name, ep.camera_name,");
        sql.append(" ep.media_server_ip, ep.media_server_port, ep.media_server_protocol, ep.vsom_domain, ep.vsom_ip_address,");
        sql.append(" ep.vsom_pass, ep.vsom_port, ep.vsom_protocol, ep.vsom_user, ep.uuid, ep.framerate, ep.resolution,");
        sql.append(" ep.extraction_server_id ");
        sql.append(" from evidence_file ef, evidence_extraction_request eer, extraction_plan epc, evidence_provider ep");
        sql.append(" where ef.filename = ?");
        sql.append(" and eer.id = ef.evidence_extraction_request_id");
        sql.append(" and epc.id = eer.extraction_plan_id");
        sql.append(" and ep.id = eer.evidence_provider_id");

        log.debug("Query for: [" + sql + "]");

        List<Map<String, Object>> result = null;
        try {
            result = getJdbcTemplate().queryForList(sql.toString(), new Object[] { filename });
        } catch (Throwable e) {
            log.warn("Error getting evidenceFile." + e);
        }

        log.debug("Query Result is Empty: [" + (result == null) + "]");
        if (result != null && !result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            evidenceFile = new EvidenceFile();
            evidenceFile.setId((Integer) row.get("ef_id"));
            evidenceFile.setEvidenceDate((Date) row.get("ef_evidence_date"));
            ScopixUtilities.setterParam(evidenceFile, row, "alternative_file_name");
            ScopixUtilities.setterParam(evidenceFile, row, "downloaded_file");
            ScopixUtilities.setterParam(evidenceFile, row, "evidence_date ef_evidence_date");
            ScopixUtilities.setterParam(evidenceFile, row, "file_creation_date");
            ScopixUtilities.setterParam(evidenceFile, row, "filename");
            ScopixUtilities.setterParam(evidenceFile, row, "no_check_nagios");
            ScopixUtilities.setterParam(evidenceFile, row, "upload_date");
            ScopixUtilities.setterParam(evidenceFile, row, "evidence_extraction_request_id");

            EvidenceExtractionRequest eer = ScopixUtilities.findEvidenceExtractionRequestByClassName((String) row
                    .get("eer_dtype"));
            eer.setId((Integer) row.get("eer_id"));
            eer.setEvidenceDate((Date) row.get("eer_evidence_date"));
            ScopixUtilities.setterParam(eer, row, "creation_timestamp");
            ScopixUtilities.setterParam(eer, row, "day_of_week");
            ScopixUtilities.setterParam(eer, row, "priorization");
            ScopixUtilities.setterParam(eer, row, "process_id");
            ScopixUtilities.setterParam(eer, row, "remote_request_id");
            ScopixUtilities.setterParam(eer, row, "requested_time");
            eer.setType(EvidenceRequestType.valueOf((String) row.get("type")));
            ScopixUtilities.setterParam(eer, row, "length_in_secs");
            ScopixUtilities.setterParam(eer, row, "allows_extraction_plan_to_past");
            ScopixUtilities.setterParam(eer, row, "evidence_provider_id");
            ScopixUtilities.setterParam(eer, row, "extraction_plan_id");
            ScopixUtilities.setterParam(eer, row, "live");

            evidenceFile.setEvidenceExtractionRequest(eer);

            ExtractionPlan epc = new ExtractionPlan();
            epc.setId((Integer) row.get("epc_id"));
            ScopixUtilities.setterParam(epc, row, "expiration_date");
            ScopixUtilities.setterParam(epc, row, "store_id");
            ScopixUtilities.setterParam(epc, row, "store_name");
            ScopixUtilities.setterParam(epc, row, "time_zone_id");
            ScopixUtilities.setterParam(epc, row, "extraction_server_id");

            eer.setExtractionPlan(epc);

            EvidenceProvider ep = ScopixUtilities.findEvidenceProviderByClassName((String) row.get("ep_dtype"));
            epc.setId((Integer) row.get("ep_id"));
            ScopixUtilities.setterParam(ep, row, "description");
            ScopixUtilities.setterParam(ep, row, "device_id");
            ScopixUtilities.setterParam(ep, row, "name");
            ScopixUtilities.setterParam(ep, row, "unique_device_id");
            ScopixUtilities.setterParam(ep, row, "ip_address");
            ScopixUtilities.setterParam(ep, row, "password");
            ScopixUtilities.setterParam(ep, row, "user_name");
            ScopixUtilities.setterParam(ep, row, "port");
            ScopixUtilities.setterParam(ep, row, "protocol");
            ScopixUtilities.setterParam(ep, row, "query");
            ScopixUtilities.setterParam(ep, row, "uuid");
            ScopixUtilities.setterParam(ep, row, "gallery_dir");
            ScopixUtilities.setterParam(ep, row, "extraction_plan_to_past");
            ScopixUtilities.setterParam(ep, row, "provider");
            ScopixUtilities.setterParam(ep, row, "provider_type");
            ScopixUtilities.setterParam(ep, row, "loop_name");
            ScopixUtilities.setterParam(ep, row, "camera_name");
            ScopixUtilities.setterParam(ep, row, "media_server_ip");
            ScopixUtilities.setterParam(ep, row, "media_server_port");
            ScopixUtilities.setterParam(ep, row, "media_server_protocol");
            ScopixUtilities.setterParam(ep, row, "vsom_domain");
            ScopixUtilities.setterParam(ep, row, "vsom_ip_address");
            ScopixUtilities.setterParam(ep, row, "vsom_pass");
            ScopixUtilities.setterParam(ep, row, "vsom_port");
            ScopixUtilities.setterParam(ep, row, "vsom_protocol");
            ScopixUtilities.setterParam(ep, row, "vsom_user");
            ScopixUtilities.setterParam(ep, row, "uuid");
            ScopixUtilities.setterParam(ep, row, "framerate");
            ScopixUtilities.setterParam(ep, row, "resolution");
            ScopixUtilities.setterParam(ep, row, "extraction_server_id");
            eer.setEvidenceProvider(ep);

        }
        log.info("end with evidenceFile: [" + evidenceFile + "]");
        return evidenceFile;
    }

    // public EvidenceFile findEvidenceFileByFileName(String filename) {
    // EvidenceFile evidenceFile = null;
    // Criteria criteria = this.getSession().createCriteria(EvidenceFile.class);
    // criteria.add(Restrictions.eq("filename", filename));
    // List<EvidenceFile> evidenceFiles = criteria.list();
    // if (evidenceFiles != null && !evidenceFiles.isEmpty()) {
    // evidenceFile = evidenceFiles.get(0);
    // }
    // return evidenceFile;
    //
    // }

    public EvidenceFile findEvidenceFileByEERAndDate(Integer evidenceExtractionRequestId, Date date) throws ScopixException {
        EvidenceFile evidenceFile = null;
        SimpleDateFormat sdfParse = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat sdfFormat = new SimpleDateFormat("dd-MM-yyyy");
        Criteria criteria = this.getSession().createCriteria(EvidenceFile.class);
        criteria.add(Restrictions.eq("evidenceExtractionRequest.id", evidenceExtractionRequestId));
        try {
            criteria.add(Restrictions.between("evidenceDate", sdfParse.parse(sdfFormat.format(date) + " 00:00"),
                    sdfParse.parse(sdfFormat.format(date) + " 23:59")));
        } catch (ParseException ex) {
            log.error(ex, ex);
            throw new ScopixException("Error obteniendo evidenceFile: ", ex);
        }
        List<EvidenceFile> evidenceFiles = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        if (evidenceFiles != null && !evidenceFiles.isEmpty()) {
            evidenceFile = evidenceFiles.get(0);
        }
        return evidenceFile;

    }

    public List<EvidenceFile> findEvidenceFileByAlternativeName(String fileNameNextLevel) {
        Criteria criteria = this.getSession().createCriteria(EvidenceFile.class);
        criteria.add(Restrictions.eq("alternativeFileName", fileNameNextLevel));
        criteria.add(Restrictions.isNull("fileCreationDate"));
        List<EvidenceFile> evidenceFiles = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return evidenceFiles;
    }

    public List<EvidenceFile> getEvidenceFilesWereNotDownloaded() {
        // Criteria criteria = this.getSession().createCriteria(EvidenceFile.class);
        // criteria.add(Restrictions.isNotNull("alternativeFileName"));
        // criteria.add(Restrictions.ne("alternativeFileName", ""));
        // criteria.add(Restrictions.eq("downloadedFile", Boolean.FALSE));
        // List<EvidenceFile> evidenceFiles = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        StringBuilder sql = new StringBuilder();
        sql.append("select id, alternative_file_name from evidence_file ");
        sql.append(" where downloaded_file = false ");
        sql.append(" and  alternative_file_name  is not null ");
        sql.append(" and upload_date is null");
        sql.append(" order by evidence_date");

        List<EvidenceFile> evidenceFiles = new ArrayList<EvidenceFile>();
        List<Object[]> ret = getSession().createSQLQuery(sql.toString()).list();
        for (Object[] o : ret) {
            Integer evidenceFileId = (Integer) o[0];
            String alernativeFileName = (String) o[1];
            EvidenceFile ef = new EvidenceFile();
            ef.setAlternativeFileName(alernativeFileName);
            ef.setId(evidenceFileId);
            evidenceFiles.add(ef);

        }
        return evidenceFiles;
    }

    /**
     * Registra evidence file por ser descargado
     * 
     * @param fileName
     * @param evidenceExtractionRequest
     * @param evidenceDate
     * @return
     * @throws ScopixException
     */
    public EvidenceFile registerEvidenceFile(String fileName, EvidenceExtractionRequest evidenceExtractionRequest,
            Date evidenceDate) throws ScopixException {

        log.info("start, fileName: [" + fileName + "], " + "evidenceExtractionRequest: [" + evidenceExtractionRequest
                + "], evidenceDate:[" + evidenceDate + "]");

        EvidenceFile evidenceFile = this.findEvidenceFileByFileName(fileName);
        log.debug("evidenceFile: [" + evidenceFile + "]");

        if (evidenceFile == null) {
            // no existe, registra fecha de evidencia asociada y evidenceExtractionRequest
            evidenceFile = new EvidenceFile();
            evidenceFile.setEvidenceDate(evidenceDate);
            evidenceFile.setEvidenceExtractionRequest(evidenceExtractionRequest);
        } else {
            // ya existe, "limpia" campos que se actualizarán cuando sea descargada la evidencia
            evidenceFile.setFilename(null);
            evidenceFile.setDownloadedFile(null);
            evidenceFile.setFileCreationDate(null);
        }
        this.save(evidenceFile);
        log.info("end, evidenceFile: [" + evidenceFile + "]");
        return evidenceFile;
    }

    /**
     * Actualiza en BD el evidence file luego de ser descargado
     * 
     * @param evidenceFile
     * @param fileName
     * @return
     */
    public EvidenceFile updateEvidenceFile(EvidenceFile evidenceFile, String fileName) {
        log.info("start, fileName: [" + fileName + "], evidenceFile: [" + evidenceFile + "]");
        evidenceFile.setFilename(fileName); // nombre con extensión
        evidenceFile.setFileCreationDate(new Date());
        evidenceFile.setDownloadedFile(Boolean.TRUE);

        log.debug("actualizando evidence file, fileName: [" + evidenceFile.getFilename() + "], " + "creationDate: ["
                + evidenceFile.getFileCreationDate() + "], " + "downloaded: [" + evidenceFile.getDownloadedFile() + "]");

        this.save(evidenceFile);
        log.info("end, evidenceFile: [" + evidenceFile + "]");
        return evidenceFile;
    }
}