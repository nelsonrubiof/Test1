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
 *  TemplateManagementHibernateDAOMock.java
 * 
 *  Created on 07-09-2010, 05:27:48 PM
 * 
 */
package mockup;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class TemplateManagementHibernateDAOMock implements TemplateManagementHibernateDAO {

    public TemplateManagementHibernateDAOMock() {
    }

    public List<SituationTemplate> getSituationTemplateList(SituationTemplate situationTemplate) throws ScopixException {
        List<SituationTemplate> lista = new ArrayList<SituationTemplate>();
        lista.add(new SituationTemplate());
        lista.add(new SituationTemplate());
        lista.add(new SituationTemplate());
        return lista;
    }

    public List<MetricTemplate> getMetricTemplateForASituationTemplate(Integer situationTemplateId) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<MetricTemplate> getMetricTemplateList(MetricTemplate metricTemplate) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Product> getProductList(Product product) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
