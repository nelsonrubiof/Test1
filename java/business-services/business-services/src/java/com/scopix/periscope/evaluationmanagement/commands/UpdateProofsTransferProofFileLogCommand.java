 /*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  UpdateProofsTransferProofFileLogCommand.java
 * 
 *  Created on 07-03-2013, 06:06:35 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Set;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateProofsTransferProofFileLogCommand {
    
    public void execute(Set<Integer> currentProofIds, Integer transferProofFileLogId) {
        TransferHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        
        dao.updateProofsTransferProofFileLogId(currentProofIds, transferProofFileLogId);
    }
}
