/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * TransferProofsDataCommand.java
 *
 * Created on 07-11-2012, 05:38:13 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.EvidencesAndProofs;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferProofsDataCommand {

    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferProofsDataCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;
    private GetEvidencesAndProofsForTransferCommand evidencesAndProofsForTransferCommand;

    /**
     * Transferencia de los datos de proofs a bw
     *
     * @param endDate
     * @param uploadPivotDate
     * @throws PeriscopeException
     */
    public void execute(Date endDate, Date uploadPivotDate) throws ScopixException {
        log.info(START);

        Date initDate = getDao().getInitialTransferDateForProofData(endDate, uploadPivotDate);

        if (initDate == null) {
            log.info("sin Proofs para subir");
            return;
        }

        Calendar calIterator = Calendar.getInstance();
        Calendar calEndDate = Calendar.getInstance();
        calIterator.setTime(initDate);
        calEndDate.setTime(endDate);
        calEndDate.add(Calendar.DATE, 1);

        while (calIterator.before(calEndDate)) {
            log.debug("calIterator: " + calIterator.getTime());
            List<EvidencesAndProofs> evidenceAndProofsList = new ArrayList<EvidencesAndProofs>();

            List<Integer> omIds = getDao().getObservedMetricIds(calIterator.getTime());

            for (Integer i : omIds) {
                log.debug("omId: " + i);
                evidenceAndProofsList.addAll(getEvidencesAndProofsForTransferCommand().execute(i));
            }

            insertEvidencesAndProofs(evidenceAndProofsList);

            calIterator.add(Calendar.DATE, 1);
        }

        log.info(END);
    }

    /**
     * Se traspasan los datos de proofs a bw
     *
     * @param list
     * @throws PeriscopeException
     */
    private void insertEvidencesAndProofs(List<EvidencesAndProofs> list) throws ScopixException {
        log.info(START + ", list size: " + list.size());
        StringBuilder sqlInsertEAP = new StringBuilder();
        StringBuilder sqlProofsBW = new StringBuilder();
        StringBuilder sqlProofsBS = new StringBuilder();
        List<Integer> proofIds = null;
        int cont = 0;

        try {
            for (EvidencesAndProofs eap : list) {
                Integer idEvidenceProof = getDao().getNextIdEvidenceAndProof();
                cont++;

                //el id de esta tabla corresponde al observed metric id
                sqlInsertEAP.append("INSERT INTO evidences_and_proofs (");
                sqlInsertEAP.append(" id, observed_metric_id, metric_type, evidence_provider_id, store_hierarchy_id, evidence_date");
                sqlInsertEAP.append(") VALUES (");
                sqlInsertEAP.append(idEvidenceProof).append(",");
                sqlInsertEAP.append(eap.getObservedMetricId()).append(",");
                sqlInsertEAP.append(" '").append(eap.getMetricType()).append("',");
                sqlInsertEAP.append(eap.getCameraId()).append(",");
                sqlInsertEAP.append(eap.getStoreId()).append(",");
                sqlInsertEAP.append(" timestamp '").append(DateFormatUtils.format(eap.getEvidenceDate(), "yyyy-MM-dd")).append("');");

                proofIds = new ArrayList<Integer>();
                for (ProofBW pbw : eap.getProofs()) {
                    cont++;
                    log.debug("[pbwId " + pbw.getId() + "]");

                    sqlProofsBW.append("DELETE FROM proof WHERE id = ").append(pbw.getId()).append(";");
                    sqlProofsBW.append("INSERT INTO proof (id, path_with_marks, path_without_marks, proof_order, proof_result,");
                    sqlProofsBW.append(" evidence_provider_id, proof_available, evidences_and_proofs_id)");
                    sqlProofsBW.append(" VALUES (");
                    sqlProofsBW.append(pbw.getId()).append(",");
                    sqlProofsBW.append(" '").append(StringUtils.replaceChars(pbw.getPathWithMarks(), '\\', '/')).append("',");
                    sqlProofsBW.append(" '").append(StringUtils.replaceChars(pbw.getPathWithoutMarks(), '\\', '/')).append("',");
                    sqlProofsBW.append(pbw.getProofOrder()).append(",");
                    sqlProofsBW.append(pbw.getProofResult()).append(",");
                    sqlProofsBW.append(eap.getCameraId()).append(",");
                    sqlProofsBW.append(pbw.isProofAvailable()).append(",");
                    sqlProofsBW.append(idEvidenceProof);
                    sqlProofsBW.append(");");

                    proofIds.add(pbw.getId());
                }

                if (proofIds.size() > 0) {
                    sqlProofsBS.append("UPDATE proof SET sent_tomisdata = true WHERE id in (");
                    sqlProofsBS.append(StringUtils.join(proofIds, ",")).append(");");
                }

                if (cont > 500) {
                    cont = 0;
                    getDaoBW().executeSQLFacts(sqlInsertEAP.toString());
                    getDaoBW().executeSQLFacts(sqlProofsBW.toString());
                    getDao().executeSQL(sqlProofsBS.toString());
                    sqlInsertEAP.setLength(0);
                    sqlProofsBW.setLength(0);
                    sqlProofsBS.setLength(0);
                }
            }

            getDaoBW().executeSQLFacts(sqlInsertEAP.toString());
            getDaoBW().executeSQLFacts(sqlProofsBW.toString());
            getDao().executeSQL(sqlProofsBS.toString());
        } catch (SQLException ex) {
            log.error("SQLException", ex);
            throw new ScopixException(ex);
        } catch (DataAccessException ex) {
            log.error("DataAccessException", ex);
            throw new ScopixException(ex);
        }

        log.info(END);
    }

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }

    public TransferBWHibernateDAO getDaoBW() {
        if (daoBW == null) {
            daoBW = SpringSupport.getInstance().findBeanByClassName(TransferBWHibernateDAO.class);
        }
        return daoBW;
    }

    public void setDaoBW(TransferBWHibernateDAO daoBW) {
        this.daoBW = daoBW;
    }

    public GetEvidencesAndProofsForTransferCommand getEvidencesAndProofsForTransferCommand() {
        if (evidencesAndProofsForTransferCommand == null) {
            evidencesAndProofsForTransferCommand = new GetEvidencesAndProofsForTransferCommand();
        }
        return evidencesAndProofsForTransferCommand;
    }

    public void setEvidencesAndProofsForTransferCommand(
            GetEvidencesAndProofsForTransferCommand evidencesAndProofsForTransferCommand) {
        this.evidencesAndProofsForTransferCommand = evidencesAndProofsForTransferCommand;
    }
}