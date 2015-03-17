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
 * TransferEvidenceProviderCommand.java
 *
 * Created on 07-11-2012, 05:19:50 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferEvidenceProviderCommand {

    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferEvidenceProviderCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Transferencia de evidence providers a bw
     * 
     * @throws PeriscopeException 
     */
    public void execute() throws ScopixException {
        log.info(START);
        try {
            List<Integer> evidenceProviderIdListBW = getDaoBW().getEvidenceProviderList();

            List<EvidenceProvider> updateEvidenceProviderList = getDao().getEvidenceProviderListByIds(evidenceProviderIdListBW);

            //actualizar evidence providers en BW
            StringBuilder sqlBW = new StringBuilder();
            int cont = 0;

            if (updateEvidenceProviderList != null) {
                for (EvidenceProvider ep : updateEvidenceProviderList) {
                    sqlBW.append("UPDATE evidence_provider SET");
                    sqlBW.append(" name = '").append(ep.getName()).append("',");
                    sqlBW.append(" description = '").append(ep.getDescription()).append("'");
                    sqlBW.append(" WHERE");
                    sqlBW.append(" id = ").append(ep.getId()).append(";");

                    cont++;
                    if (cont > 500) {
                        cont = 0;
                        getDaoBW().executeSQLFacts(sqlBW.toString());
                        sqlBW.setLength(0);
                    }
                }
            }

            //obtener nuevos evidence providers
            List<EvidenceProvider> newEvidenceProvidersToBW = getDao().getEvidenceProviderListNotInIds(evidenceProviderIdListBW);

            //insertar los nuevos EP en BW
            for (EvidenceProvider ep : newEvidenceProvidersToBW) {
                sqlBW.append("INSERT INTO evidence_provider (id, name, description)");
                sqlBW.append(" VALUES (");
                sqlBW.append(" ").append(ep.getId()).append(",");
                sqlBW.append(" '").append(ep.getName()).append("',");
                sqlBW.append(" '").append(ep.getDescription()).append("'");
                sqlBW.append(");");

                cont++;
                if (cont > 500) {
                    cont = 0;
                    getDaoBW().executeSQLFacts(sqlBW.toString());
                    sqlBW.setLength(0);
                }
            }

            getDaoBW().executeSQLFacts(sqlBW.toString());
            sqlBW.setLength(0);
        } catch (DataAccessException ex) {
            log.error("DataAccessException = " + ex.getMessage());
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
}