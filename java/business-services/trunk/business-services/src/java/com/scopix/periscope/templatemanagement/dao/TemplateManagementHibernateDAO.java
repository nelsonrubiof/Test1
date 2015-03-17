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
 *  TemplateManagementHibernateDAO.java
 * 
 *  Created on 08-09-2010, 11:30:54 AM
 * 
 */

package com.scopix.periscope.templatemanagement.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;

/**
 *
 * @author nelson
 */
public interface TemplateManagementHibernateDAO {

    List<MetricTemplate> getMetricTemplateForASituationTemplate(Integer situationTemplateId) throws ScopixException;

    List<MetricTemplate> getMetricTemplateList(MetricTemplate metricTemplate) throws ScopixException;

    List<Product> getProductList(Product product) throws ScopixException;

    List<SituationTemplate> getSituationTemplateList(SituationTemplate situationTemplate) throws ScopixException;

}
