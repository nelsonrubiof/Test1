/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.Product;
import java.util.List;

/**
 *
 * @author Cesar Abarza
 */
public class GetIndicatorNameListCommand {

    private ExtractionPlanCustomizingDAO dao;

    public List<Indicator> execute(AreaType areaType, Product product) {
        List<Indicator> indicators = getDao().getIndicatorNameList(areaType, product);
        return indicators;
    }

    public ExtractionPlanCustomizingDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanCustomizingDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ExtractionPlanCustomizingDAO dao) {
        this.dao = dao;
    }
}
