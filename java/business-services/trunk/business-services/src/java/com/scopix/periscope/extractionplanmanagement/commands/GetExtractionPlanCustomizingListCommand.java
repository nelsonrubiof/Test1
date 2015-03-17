/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar
 */
public class GetExtractionPlanCustomizingListCommand {

    public static final String ALL = "ALL";
    public static final String EDITION = "EDITION";
    public static final String SENT = "SENT";
    private ExtractionPlanCustomizingDAO dao;
    static Logger log = Logger.getLogger(GetExtractionPlanCustomizingListCommand.class);

    public List<ExtractionPlanCustomizing> execute(ExtractionPlanCustomizing epc, String estado) {
        List<ExtractionPlanCustomizing> wizardCustomizings = null;
        if (ALL.equals(estado)) {
            List<ExtractionPlanCustomizing> listaActivos = getDao().getExtractionPlanCustomizingActiveList(epc);
            List<ExtractionPlanCustomizing> listaNull = getDao().getExtractionPlanCustomizingNullList(epc);
            Map<String, ExtractionPlanCustomizing> merge = new HashMap<String, ExtractionPlanCustomizing>();
            for (ExtractionPlanCustomizing customizing : listaNull) {
                log.debug("key merge " + customizing.getSituationTemplate().getId() + "." + customizing.getStore().getId());
                merge.put(customizing.getSituationTemplate().getId() + "." + customizing.getStore().getId(), customizing);
            }
            for (ExtractionPlanCustomizing customizing : listaActivos) {
                if (!merge.containsKey(customizing.getSituationTemplate().getId() + "." + customizing.getStore().getId())) {
                    log.debug("key merge - " + customizing.getSituationTemplate().getId() + "." + customizing.getStore().getId());
                    merge.put(customizing.getSituationTemplate().getId() + "." + customizing.getStore().getId(), customizing);
                }
            }
            wizardCustomizings = new ArrayList<ExtractionPlanCustomizing>();
            for (ExtractionPlanCustomizing customizing : merge.values()) {
                wizardCustomizings.add(customizing);
            }
        } else if (SENT.equals(estado)) {
            wizardCustomizings = getDao().getExtractionPlanCustomizingActiveList(epc);
        } else if (EDITION.equals(estado)) {
            wizardCustomizings = getDao().getExtractionPlanCustomizingNullList(epc);
        } else {
            //en el caso que no venga estado conocido o sea null
            wizardCustomizings = getDao().getExtractionPlanCustomizingActiveOrNullList(epc);
        }

        //recorremos la lista resultado y ejecutamos isEmpty() para que la de providers de cada objeto sea cargada
        for (ExtractionPlanCustomizing wc : wizardCustomizings) {
            //tratamos de cargar las metricas para el epc
            wc.getExtractionPlanMetrics().isEmpty();
        }
        return wizardCustomizings;
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
