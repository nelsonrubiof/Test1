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
 *  ManagerMock.java
 * 
 *  Created on 07-09-2010, 04:56:26 PM
 * 
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceExtractionServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSensorListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SendExtractionPlanCommand;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.commands.FormulasBySTAndStoreCommand;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.RequestedVideo;
import com.scopix.periscope.extractionplanmanagement.Situation;

import com.scopix.periscope.extractionplanmanagement.commands.AddMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.AddSituationCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CleanExtractionPlanMetricsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CreateExtractionPlanCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingByIdCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingBySTAndStoreCommand;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.commands.ActivateWizardCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.InactivateEPCCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveSensorsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateExtracionPlanMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateMetricOrderCommand;
import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.corporatestructuremanagement.Country;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderType;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.corporatestructuremanagement.PeriodInterval;
import com.scopix.periscope.corporatestructuremanagement.Place;
import com.scopix.periscope.corporatestructuremanagement.PlaceType;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.MetricRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationRequestDTO;
import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.evaluationmanagement.MetricEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.commands.AddIndicatorValuesCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedSituationEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationCommand;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeType;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanMetricDTO;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import com.scopix.periscope.templatemanagement.EvidenceType;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.MetricType;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.YesNoType;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateListCommand;
import com.scopix.periscope.templatemanagement.dto.MetricTemplateDTO;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mockup.evaluationmanagement.dao.ObservedSituationHibernateDAOMock;
import org.apache.commons.lang.time.DateUtils;
import org.easymock.EasyMock;

/**
 *
 * @author nelson
 */
public class ManagerMock {

    public static Store getNewStoreMock() {
        Store store = new Store();
        store.setId(1);
        return store;
    }

    public static GetSituationTemplateListCommand genSituationTemplateListCommand() {
        GetSituationTemplateListCommand command = new GetSituationTemplateListCommand();
        command.setDao(new TemplateManagementHibernateDAOMock());
        return command;
    }


    public static GetStoreCommand genStoreCommand() {
        GetStoreCommand command = new GetStoreCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static GetStoreCommand genStoreCommand2() {
        GetStoreCommand command = new GetStoreCommand();
        command.setDao(new GenericDAOStStoreCompletoMockup());
        return command;
    }

    /**
     * ExtractionPlanCustomizing para probar la creacion de evidence request en el managerWizard
     */
    public static ExtractionPlanCustomizing genExtractionPlanCustomizing2Rangos1DetalleCU() {
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(1);
        //debemos crear un situation template asociado        
        epc.setSituationTemplate(genSituationTemplate(Boolean.TRUE, null, null, null, 1, null, "Situation Template Mock 1", null,
                null));
        epc.setStore(genStore(null, null, null, null, null, null, null, null, null, 1, null, null, null, "Store Mock 1", null,
                null, null, null, null, null));
        epc.setAreaType(genAreaType(1, "Area Type Mock 1", "Area Type Mock 1"));
        epc.setExtractionPlanMetrics(genListaExtractionPlanMetricsVIDEO(2, epc));
        appendProvidersAllPlanMetrics(epc);

        epc.getExtractionPlanRanges().add(generateExtractionPlanRango(1, null, 1, null, epc, 1, null, null, 10, 0, 15, 0));
        epc.getExtractionPlanRanges().add(generateExtractionPlanRango(2, null, 2, null, epc, 1, null, null, 15, 0, 18, 0));
        return epc;
    }

    public static ExtractionPlanCustomizing genExtractionPlanCustomizing2Rangos1DetalleCUYSensores() {
        ExtractionPlanCustomizing epc = genExtractionPlanCustomizing2Rangos1DetalleCU();
        epc.setSensors(genSensores(1));
        return epc;
    }

    public static ExtractionPlanCustomizing genExtractionPlanCustomizing2Sensors() {
        ExtractionPlanCustomizing epc = genExtractionPlanSimple(1, true, Boolean.FALSE);
        epc.getSensors().add(genSensor(1, "Sensor 1", "Sensor 1"));
        epc.getSensors().add(genSensor(2, "Sensor 3", "Sensor 3"));
        return epc;
    }

    public static ExtractionPlanCustomizing genExtractionPlanCustomizing2Rangos1DetalleCUSTConMetricTemplate() {
        ExtractionPlanCustomizing epc = genExtractionPlanCustomizing2Rangos1DetalleCU();
        SituationTemplate st = genSituationTemplate(1, "Situation Template 1");
        st.getMetricTemplate().add(genMetricTemplate(1, "Metric Template 1", "Metric Template 1"));
        st.getMetricTemplate().add(genMetricTemplate(2, "Metric Template 2", "Metric Template 2"));
        epc.setSituationTemplate(st);
        return epc;
    }

    /**
     * Generta un ExtractionPlanCustomizing con st id 1 y store id 1
     */
    public static ExtractionPlanCustomizing genExtractionPlanSimple(Integer id, boolean oneEvaluation, Boolean active) {
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(id);
        epc.setOneEvaluation(oneEvaluation);
        epc.setActive(active);
        epc.setStore(genStore(1, "Store 1", "Store 1"));
        epc.setSituationTemplate(genSituationTemplate(1, "Situation Template 1"));
        return epc;
    }

    public static ExtractionPlanCustomizing genExtractionPlanCustomizing2Rangos2DetallesForDays(Integer[] days) {
        ExtractionPlanCustomizing epc = genExtractionPlanSimple(1, true, Boolean.TRUE);
        int pos = 1;
        for (int i = 0; i < 2; i++) {
            for (Integer day : days) {
                ExtractionPlanRange range = genExtractionPlanRange(pos++, day, 30, 300, 1);
                range.getExtractionPlanRangeDetails().add(genExtractionPlanRangeDetail(Math.round(pos), new Date()));
                range.getExtractionPlanRangeDetails().add(genExtractionPlanRangeDetail(Math.round(pos), new Date()));
                epc.getExtractionPlanRanges().add(range);
            }
        }
        return epc;
    }

    public static List<Sensor> genSensores(int cantidad) {
        List<Sensor> sensors = new ArrayList<Sensor>();
        for (int i = 1; i <= cantidad; i++) {
            Sensor s = genSensor(i, "Sendor Mock " + i, "Sendor Mock " + i);
            sensors.add(s);
        }
        return sensors;
    }

    public static Sensor genSensor(Integer id, String name, String descripcion) {
        Sensor sensor = new Sensor();
        sensor.setId(id);
        sensor.setName(name);
        sensor.setDescription(descripcion);
        return sensor;
    }

    public static SituationTemplate genSituationTemplate(Boolean active, AreaType areaType, String esben, List<Formula> formulas,
            Integer id, List<MetricTemplate> metricTemplates, String name, Product product, List<Situation> situations) {
        SituationTemplate st = new SituationTemplate();
//        st.setId(1);
//        st.setName("Situation Template Mock 1");
        st.setActive(active);
        st.setAreaType(areaType);
        st.setEvidenceSpringBeanEvaluatorName(esben);
        st.setFormulas(formulas);
        st.setId(id);
        st.setMetricTemplate(metricTemplates);
        st.setName(name);
        st.setProduct(product);
        st.setSituations(situations);

        return st;
    }

    public static SituationTemplate genSituationTemplate(Integer id, String name) {
        SituationTemplate st = new SituationTemplate();
        st.setId(id);
        st.setName(name);
        return st;
    }

    /**
     * rango de 10:00 - 15:00
     */
    public static ExtractionPlanRange generateExtractionPlanRango(Integer id, ExtractionPlanRangeType eprt, Integer dow,
            Integer duration, ExtractionPlanCustomizing epc, Integer numDetalles, Integer frecuency, Integer samples,
            int horaIni, int minIni, int horaFin, int minFin) {
        ExtractionPlanRange epr = genExtractionPlanRange(id, dow, frecuency, duration, samples);

        epr.setExtractionPlanCustomizing(epc);

        epr.setExtractionPlanRangeType(eprt);
        Calendar c = Calendar.getInstance();
        c.set(0, 0, 0, horaIni, minIni, 0);
        c.set(Calendar.MILLISECOND, 0);
        epr.setInitialTime(c.getTime());

        Calendar c2 = Calendar.getInstance();
        c2.set(0, 0, 0, horaFin, minFin, 0);
        c2.set(Calendar.MILLISECOND, 0);
        epr.setEndTime(c2.getTime());

        c.set(0, 0, 0, horaIni, minIni, 0);
        // de terminsta creamos los detalles segun la cantidad inidcada antes
        for (int i = 1; i <= numDetalles; i++) {
            ExtractionPlanRangeDetail detail = genExtractionPlanRangeDetail((i * id), c.getTime());
            detail.setExtractionPlanRange(epr);
            epr.getExtractionPlanRangeDetails().add(detail);
        }
        return epr;
    }

    public static ExtractionPlanRange genExtractionPlanRange(Integer id, Integer dow, Integer frecuency, Integer duration,
            Integer samples, int horaIni, int minIni, int horaFin, int minFin) {
        ExtractionPlanRange extractionPlanRange = genExtractionPlanRange(id, dow, frecuency, duration, samples);
        Date ini = new Date();
        Date fin = new Date();
        ini = DateUtils.setHours(ini, horaIni);
        ini = DateUtils.setMinutes(ini, minIni);

        fin = DateUtils.setHours(fin, horaFin);
        fin = DateUtils.setMinutes(fin, minFin);
        extractionPlanRange.setInitialTime(ini);
        extractionPlanRange.setEndTime(fin);
        return extractionPlanRange;
    }

    public static ExtractionPlanRange genExtractionPlanRange(Integer id, Integer dow, Integer frecuency, Integer duration,
            Integer samples) {
        ExtractionPlanRange planRange = new ExtractionPlanRange();
        planRange.setId(id);
        planRange.setDayOfWeek(dow);
        planRange.setFrecuency(frecuency);
        planRange.setDuration(duration);
        planRange.setSamples(samples);
        return planRange;
    }

    public static ExtractionPlanRangeDetail genExtractionPlanRangeDetail(int id, Date iniDetail) {
        ExtractionPlanRangeDetail detail = new ExtractionPlanRangeDetail();
        detail.setId(id);
        detail.setTimeSample(iniDetail);
        return detail;
    }

    public static AddSituationCommand genAddSituationCommand() {
        AddSituationCommand command = new AddSituationCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static Store genStore(Integer id, String name, String descripcion) {
        Store store = new Store();
        store.setId(id);
        store.setName(name);
        store.setDescription(descripcion);
        return store;
    }

    public static Store genStore(String address, List<Area> areas, Corporate corporate, Country country, String descripcion,
            EvidenceExtractionServicesServer eess, List<EvidenceProvider> eps, EvidenceServicesServer ees, List<Formula> formulas,
            Integer id, Double latitude, Double longitude, List<Metric> metrics, String name,
            List<OperatorQueueDetail> operatorQueueDetails, Place place, List<PeriodInterval> intervals, PlaceType placeType,
            List<Place> places, Region region) {
        Store s = new Store();
        s.setAddress(address);
        s.setAreas(areas);
        s.setCorporate(corporate);
        s.setCountry(country);
        s.setDescription(descripcion);
        s.setEvidenceExtractionServicesServer(eess);
        s.setEvidenceProviders(eps);
        s.setEvidenceServicesServer(ees);
        s.setFormulas(formulas);
        s.setId(id);
        s.setLatitudeCoordenate(latitude);
        s.setLongitudeCoordenate(longitude);
        s.setMetrics(metrics);
        s.setName(name);
        s.setOperatorQueueDetails(operatorQueueDetails);
        s.setParentPlace(place);
        s.setPeriodIntervals(intervals);
        s.setPlaceType(placeType);
        s.setPlaces(places);
        s.setRegion(region);
        return s;
    }

    public static AreaType genAreaType(List<Area> areas, String descripcion, Integer id, List<IndicatorProductAndAreaType> ipaats,
            String name, List<OperatorQueueDetail> oqds, List<SituationTemplate> sts) {
        AreaType at = genAreaType(id, name, descripcion);
//        at.setId(1);
//        at.setName("Area Type Mock 1");

        at.setAreas(areas);
        at.setIpaats(ipaats);
        //at.setOperatorQueueDetails(oqds);
        at.setSituationTemplates(sts);

        return at;
    }

    public static AreaType genAreaType(Integer id, String name, String descripcion) {
        AreaType at = new AreaType();
        at.setId(id);
        at.setName(name);
        at.setDescription(descripcion);
        return at;
    }

    public static Metric getMetricTest() {
        return genMetric(1, 2, "TEMPLATE METRIC 1 10:00 area", "FS", null);
    }

    /**
     * 
     * @param id
     * @param order
     * @param descripcion
     * @param variableName
     * @param store
     * @return
     */
    public static Metric genMetric(Integer id, Integer order, String descripcion, String variableName, Store store) {
        Metric m = new Metric();
        m.setId(id);
        m.setMetricOrder(order);
        m.setDescription(descripcion);
        m.setMetricVariableName(variableName);
        m.setStore(store);
        return m;
    }

    /**
     * Retorna un ExtractionPlanMetric con
     */
    public static ExtractionPlanMetric genExtractionPlanMetricSoloMT() {
        ExtractionPlanMetric epm = new ExtractionPlanMetric();
        epm.setMetricTemplate(genMetricTemplate(null, null, null, null, 1, null, null, null, "TEMPLATE METRIC 1", null, null,
                null));
        //epm.setExtractionPlanCustomizing(genExtractionPlanCustomizing2Rangos1DetalleCU());
        return epm;
    }

    public static MetricTemplate genMetricTemplate(Integer id, String name, String descripcion) {
        MetricTemplate mt = new MetricTemplate();
        mt.setId(id);
        mt.setName(name);
        mt.setDescription(descripcion);
        return mt;
    }

    public static MetricTemplate genMetricTemplate(String descripcion, String ei, String esben, EvidenceType ete, Integer id,
            String msben, MetricType metricType, List<Metric> metrics, String name, String operatosDescripcion,
            List<SituationTemplate> sts, YesNoType YesNoType) {
        MetricTemplate mt = new MetricTemplate();
//        mt.setName("TEMPLATE METRIC 1");
        mt.setDescription(descripcion);
        mt.setEvaluationInstruction(ei);
        mt.setEvidenceSpringBeanEvaluatorName(esben);
        mt.setEvidenceTypeElement(ete);
        mt.setId(id);
        mt.setMetricSpringBeanEvaluatorName(msben);
        mt.setMetricTypeElement(metricType);
        mt.setMetrics(metrics);
        mt.setName(name);
        mt.setOperatorDescription(operatosDescripcion);
        mt.setSituationTemplates(sts);
        mt.setYesNoType(YesNoType);
        return mt;
    }

    public static AddMetricCommand genAddMetricCommand() {
        AddMetricCommand command = new AddMetricCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static Area genArea(String descripcion, Integer id, String name, Store store) {
        Area a = new Area();
//        a.setId(1);
//        a.setDescription("area");
//        a.setName("area");
        a.setAreaType(genAreaType(null, null, null, null, null, null, null));
        a.setDescription(descripcion);
        a.setId(id);
        a.setName(name);
        a.setStore(store);

        return a;
    }

    /**
     * genera una situacion con is y descripcion recibos por paramatros con un ST id 1 y name Situation Template Mock 1 activa
     */
    public static Situation genSituation(Integer id, String descripcion) {
        Situation s = new Situation();
        s.setId(id);
        s.setDescription(descripcion);
        s.setSituationTemplate(genSituationTemplate(true,
                genAreaType(null, "Area Type Mock", 1, null, "Area Type Mock", null, null), null, null, 1, null,
                "Situation Template Mock 1", genProduct(1, "Produc Mock"), null));
        return s;
    }

    public static Product genProduct(Integer id, String descripcion) {
        Product p = new Product();
        p.setId(id);
        p.setDescription(descripcion);
        return p;
    }

    /**
     * crea una lista de EvidenceProvider con largo recibido por parametro
     */
    public static List<EvidenceProvider> genListaEvidenceProvider(int cantidad, EvidenceProviderType ept) {
        List<EvidenceProvider> evidenceProviders = new ArrayList<EvidenceProvider>();
        for (int i = 1; i <= cantidad; i++) {
            EvidenceProvider ep = genEvidenceProvider(i, "Descripcion ep_" + i, null, "Name ep_" + i,
                    genStore(null, null, null, null, "Store 1", null, null, null, null, 1, null, null, null,
                    "Store 1", null, null, null, null, null, null), "definitionData " + i, ept);
            evidenceProviders.add(ep);
        }
        return evidenceProviders;
    }

    public static List<ExtractionPlanMetric> genListaExtractionPlanMetricsVIDEO(int cant, ExtractionPlanCustomizing epc) {
        List<ExtractionPlanMetric> extractionPlanMetrics = new ArrayList<ExtractionPlanMetric>();
        MetricTemplate metricTemplate = genMetricTemplate(null, null, null, EvidenceType.VIDEO, 1, null, null, null,
                "TEMPLATE METRIC 1", null, null, null);
        for (int i = 1; i <= cant; i++) {
            ExtractionPlanMetric epm = genExtractionPlanMetricSinMetricTemplate(i, i, "FS");
            epm.setMetricTemplate(metricTemplate);
            epm.setExtractionPlanCustomizing(epc);
            extractionPlanMetrics.add(epm);
        }
        return extractionPlanMetrics;
    }

    public static EvidenceProvider genEvidenceProvider(Integer id, String name, String descipcion) {
        EvidenceProvider ep = new EvidenceProvider();
        ep.setId(id);
        ep.setName(name);
        ep.setDescription(descipcion);
        return ep;
    }

    public static EvidenceProvider genEvidenceProvider(int id, String descripcion, List<ExtractionPlanMetric> epms,
            String name, Store store, String definitionData, EvidenceProviderType ept) {
        EvidenceProvider ep = genEvidenceProvider(id, name, descripcion);
        Area a = genArea("area", 1, "area", null);
        ep.getAreas().add(a);

        ep.setExtractionPlanMetrics(epms);
        ep.setStore(store);
        ep.setDefinitionData(definitionData);
        ep.setEvidenceProviderType(ept);
        return ep;
    }


    public static GetAreaListCommand genAreaListCommand() {
        GetAreaListCommand command = new GetAreaListCommand();
        command.setDao(new CorporateStructureManagementHibernateDAOMock());
        return command;
    }

    public static ExtractionPlanMetric genExtractionPlanMetricSinMetricTemplate(Integer id, Integer order, String metricVariableName) {
        ExtractionPlanMetric epm = new ExtractionPlanMetric();
        epm.setId(id);
        epm.setEvaluationOrder(order);
        epm.setMetricVariableName(metricVariableName);
        return epm;
    }

    public static UpdateExtracionPlanMetricCommand genUpdateExtracionPlanMetricCommand() {
        UpdateExtracionPlanMetricCommand command = new UpdateExtracionPlanMetricCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static UpdateMetricOrderCommand genUpdateMetricOrderCommand() {
        UpdateMetricOrderCommand command = new UpdateMetricOrderCommand();
        command.setDao(new ExtractionPlanCustomizingDAOMock());
        return command;
    }

    public static ActivateWizardCustomizingCommand genActivateWizardCustomizingCommand() {
        ActivateWizardCustomizingCommand command = new ActivateWizardCustomizingCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static AddIndicatorValuesCommand genAddIndicatorValuesCommand() {
        AddIndicatorValuesCommand command = new AddIndicatorValuesCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    /**
     * Agrega datos basicos a un ObservedSituation
     */
    public static void appendDatosObservedSituation(ObservedSituation observedSituation) {
        observedSituation.setObservedMetrics(genObservedMetrics(1));
        observedSituation.setSituation(genSituation(1, "Situacion 1 "));
    }

    public static List<ObservedMetric> genObservedMetrics(int cant) {
        List<ObservedMetric> lista = new ArrayList<ObservedMetric>();
        for (int i = 1; i <= cant; i++) {
            ObservedMetric om = genObservedMetric(i);
            lista.add(om);
        }
        return lista;
    }

    public static ObservedMetric genObservedMetric(Integer id) {
        ObservedMetric om = new ObservedMetric();
        om.setId(id);
        om.setMetric(genMetric(1, 1, "Metric Mock observed metric 1 ", "A", genStore(null, null, null, null, null, null, null,
                null, null, 1, null, null, null, "Store Mock observed metric 1", null, null, null, null, null, null)));
        om.setMetricEvaluation(genMetricEvaluation(1, 1));

        return om;
    }

    public static GetExtractionPlanCustomizingBySTAndStoreCommand genExtractionPlanCustomizingCommand() {
        GetExtractionPlanCustomizingBySTAndStoreCommand command = new GetExtractionPlanCustomizingBySTAndStoreCommand();
        command.setDao(new ExtractionPlanCustomizingDAOMock());
        return command;
    }

    public static GetObservedSituationCommand genObservedSituationCommand() {
        GetObservedSituationCommand command = new GetObservedSituationCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static AddObservedSituationEvaluationCommand genObservedSituationEvaluationCommand() {
        AddObservedSituationEvaluationCommand command = new AddObservedSituationEvaluationCommand();
        command.setDao(new GenericDAOMockup());
        return command;

    }

    public static Formula genFormula(Integer id, String descripcion, String demoninator, String formula, String variables) {
        Formula f = new Formula();
        f.setId(id);
        f.setDescription(descripcion);
        f.setDenominator(demoninator);
        f.setFormula(formula);
        f.setVariables(variables);
        return f;
    }

    private static MetricEvaluation genMetricEvaluation(Integer id, Integer metricEvaluationResult) {
        MetricEvaluation me = new MetricEvaluation();
        me.setId(id);
        me.setMetricEvaluationResult(metricEvaluationResult);
        return me;
    }

    public static ObservedSituation genObservedSituation(Integer id) {
        ObservedSituation observedSituation = new ObservedSituation();
        observedSituation.setObservedMetrics(genObservedMetrics(1));
        observedSituation.setId(id);
        observedSituation.setSituation(genSituation(1, "Situacion Mock 1"));
        return observedSituation;
    }

    /**
     * Genera un EvidenceExtractionServicesServer con un Store [Id:1][name:Store Mock 1][Descripcion: Scopix]
     * en el store se crea una lista de EvidenceProvider que contiene 2 elementos
     */
    public static EvidenceExtractionServicesServer genEvidenceExtractionServicesServer(Integer id, String name) {
        EvidenceExtractionServicesServer eess = new EvidenceExtractionServicesServer();
        eess.setId(id);
        eess.setName(name);

        Store st = EasyMock.createMock(Store.class);
        st.setId(id);
        eess.getStores().add(st);

        return eess;
    }

    public static EvidenceExtractionServicesServer genEvidenceExtractionServicesServer(Integer id, String name, String sshAddress,
            String sshLocalTunnelPort, String sshUser, String sshPassword, String sshPort, String sshRemoteTunnelPort,
            String url) {
        EvidenceExtractionServicesServer server = genEvidenceExtractionServicesServer(id, name);
        server.setSshAddress(sshAddress);
        server.setSshLocalTunnelPort(sshLocalTunnelPort);
        server.setSshUser(sshUser);
        server.setSshPassword(sshPassword);
        server.setSshPort(sshPort);
        server.setSshRemoteTunnelPort(sshRemoteTunnelPort);
        server.setUrl(url);
        Store store = genStore(1, "Store Mock 1", "Scopix");
        store.setEvidenceProviders(genListaEvidenceProvider(2, genEvidenceProviderType()));
        server.getStores().add(store);
        server.setEvidenceServicesServer(genEvidenceServicesServer(1));
        return server;
    }

    public static EvidenceServicesServer genEvidenceServicesServer(Integer id) {
        EvidenceServicesServer evidenceServicesServer = new EvidenceServicesServer();
        evidenceServicesServer.setId(id);


        return evidenceServicesServer;
    }

    /**
     * Crea un Objeto EvidenceRequest del tipo RequestedVideo con time 15:00
     * para el dia Martes
     * y de duracion 5
     * @param id
     * @return
     */
    public static EvidenceRequest genEvidenceRequestVideo(Integer id) {
        RequestedVideo er = new RequestedVideo();
        er.setId(id);
        Calendar c = Calendar.getInstance();
        c.set(2010, 2, 1, 15, 0, 0);
        er.setEvidenceTime(c.getTime());
        //Martes
        er.setDay(3);
        er.setDuration(5);

        return er;
    }

    public static EvidenceRequestDTO genEvidenceRequestVideoDTO(Integer id) {
        EvidenceRequestDTO er = new EvidenceRequestDTO();
        //idPRovider
        er.setDeviceId(id);
        Calendar c = Calendar.getInstance();
        c.set(2010, 2, 1, 15, 0, 0);
        er.setRequestedTime(c.getTime());
        //Martes
        er.setDayOfWeek(3);
        er.setDuration(5);
        er.setBusinessServicesRequestId(id * 2);
        er.setRequestType(RequestedVideo.class.getSimpleName());

        return er;
    }

    /**
     * retorn un EvidenceProviderType de tipo MOCK
     */
    public static EvidenceProviderType genEvidenceProviderType() {
        EvidenceProviderType ept = new EvidenceProviderType();
        ept.setDescription("Tipo Mock");
        return ept;

    }

    /**
     * crea un Wizard Manager con todos lo command seteados a mock
     */
    public static ExtractionPlanManager genWizardManager() {
        ExtractionPlanManager wm = new ExtractionPlanManager();
        //retorna true a todas las peticiones de permisos
        wm.setSecurityManager(new SecurityManagerMock());
        wm.setActivateWizardCustomizingCommand(genActivateWizardCustomizingCommand());
        wm.setAddMetricCommand(genAddMetricCommand());
        wm.setAddSituationCommand(genAddSituationCommand());
        wm.setAreaListCommand(genAreaListCommand());
        wm.setGetSituationTemplateListCommand(genSituationTemplateListCommand());
        wm.setGetStoreCommand(genStoreCommand());
        wm.setUpdateExtracionPlanMetricCommand(genUpdateExtracionPlanMetricCommand());
        wm.setUpdateMetricOrderCommand(genUpdateMetricOrderCommand());

        return wm;
    }

    public static ExtractionPlanManager genWizardManager2() {
        ExtractionPlanManager wm = new ExtractionPlanManager();
        //retorna true a todas las peticiones de permisos
        wm.setSecurityManager(new SecurityManagerMock());
        wm.setActivateWizardCustomizingCommand(genActivateWizardCustomizingCommand());
        wm.setAddMetricCommand(genAddMetricCommand());
        wm.setAddSituationCommand(genAddSituationCommand());
        wm.setAreaListCommand(genAreaListCommand());
        wm.setGetSituationTemplateListCommand(genSituationTemplateListCommand());
        wm.setGetStoreCommand(genStoreCommand());
        wm.setUpdateExtracionPlanMetricCommand(genUpdateExtracionPlanMetricCommand());
        wm.setUpdateMetricOrderCommand(genUpdateMetricOrderCommand());

        return wm;
    }

    /**
     * Retorna una lista con n SituationRequestDTO indicados
     */
    public static List<SituationRequestDTO> genListaSituationRequestDTOs(int cantidad) {
        List<SituationRequestDTO> dTOs = new ArrayList<SituationRequestDTO>();
        for (int i = 1; i <= cantidad; i++) {
            SituationRequestDTO dTO = genSituationRequestDTO(1, 30, 5);
            dTOs.add(dTO);
        }
        return dTOs;

    }

    public static SituationRequestDTO genSituationRequestDTO(Integer situationTemplateId, Integer frecuency, Integer duracion) {
        SituationRequestDTO dTO = new SituationRequestDTO();
        dTO.setSituationTemplateId(situationTemplateId);
        dTO.setFrecuency(frecuency);
        dTO.setDuration(duracion);
        return dTO;
    }

    public static SituationRequestDTO genSituationRequestDTOConMetricRequestDTO(Integer situationTemplateId, Integer frecuency,
            Integer duracion, Integer metricTemplateId) {
        SituationRequestDTO srdto = genSituationRequestDTO(situationTemplateId, frecuency, duracion);
        srdto.getMetricRequestDTOs().add(genMetricRequestDTO(metricTemplateId));
        return srdto;
    }

    public static MetricRequestDTO genMetricRequestDTO(Integer metricTemplateId) {
        MetricRequestDTO mrdto = new MetricRequestDTO();
        mrdto.setMetricTemplateId(metricTemplateId);
        return mrdto;
    }

    /**
     * Crear una lista de 2 situation templates
     */
    public static List<SituationTemplate> genSituationTemplateList2() {
        List<SituationTemplate> lista = new ArrayList<SituationTemplate>();
        lista.add(genSituationTemplate(Boolean.TRUE, null, null, null, 1, null, "ST 1", null, null));
        lista.add(genSituationTemplate(Boolean.TRUE, null, null, null, 2, null, "ST 2", null, null));
        return lista;
    }

    public static SituationTemplateDTO genSituationTemplateDTO(Integer id, String name) {
        SituationTemplateDTO dto = new SituationTemplateDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }

    public static GetStoreListCommand genStoreListCommand() {
        GetStoreListCommand command = new GetStoreListCommand();
        command.setDao(new CorporateStructureManagementHibernateDAOMock());
        return command;
    }

    public static StoreDTO genStoreDTO(Integer id, String name, String description) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(id);
        storeDTO.setName(name);
        storeDTO.setDescription(description);
        return storeDTO;
    }

    public static PeriodInterval genPeriodInterval(Integer id, String initTime, String endTime, boolean monday, boolean tuesday,
            boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        PeriodInterval interval = new PeriodInterval();
        interval.setId(id);
        interval.setInitTime(initTime);
        interval.setEndTime(endTime);

        interval.setMonday(monday);
        interval.setTuesday(tuesday);
        interval.setWednesday(wednesday);
        interval.setThursday(thursday);
        interval.setFriday(friday);
        interval.setSaturday(saturday);
        interval.setSunday(sunday);


        return interval;
    }

    public static GetStoreListCommand genStoreListCommandConPeriod() {
        GetStoreListCommand command = new GetStoreListCommand();
        command.setDao(new CorporateStructureManagementHibernateDAO2Mock());
        return command;
    }

    public static GetExtractionPlanCustomizingListCommand genExtractionPlanCustomizingListCommand() {
        GetExtractionPlanCustomizingListCommand command = new GetExtractionPlanCustomizingListCommand();
        command.setDao(new ExtractionPlanCustomizingDAOMock());
        return command;
    }

    /**
     * retorna un Commnad el cual retorna una lista con un solo EPC en Edicion
     */
    public static GetExtractionPlanCustomizingListCommand genExtractionPlanCustomizingListCommand2() {
        GetExtractionPlanCustomizingListCommand command = new GetExtractionPlanCustomizingListCommand();
        command.setDao(new ExtractionPlanCustomizingDAOMockConSensor());
        return command;
    }

    public static ExtractionPlanCustomizingDTO genExtractionPlanCustomizingDTO(Integer id, Boolean active, Integer areaTypeId,
            String areaTypeName, Boolean oneEvaluation, Integer situationTemplateId, Integer storeId) {
        ExtractionPlanCustomizingDTO dto = new ExtractionPlanCustomizingDTO();
        dto.setId(id);
        dto.setActive(active);
        dto.setAreaTypeId(areaTypeId);
        dto.setAreaType(areaTypeName);

        dto.setOneEvaluation(oneEvaluation);
        dto.setSituationTemplateId(situationTemplateId);
        dto.setStoreId(storeId);

        return dto;
    }

    /**
     * crea un Command con un dao que genera ST con id y nombre adjuntando un AreaType y  Store con nombre y descripcion
     */
    public static CreateExtractionPlanCustomizingCommand genCreateExtractionPlanCustomizingCommand() {
        CreateExtractionPlanCustomizingCommand command = new CreateExtractionPlanCustomizingCommand();
        command.setDao(new GenericDAOStStoreCompletoMockup());
        return command;
    }

    /**
     * retorna un ExtractionPlanCustomizingByIdCommand que no retorna EPC
     */
    public static GetExtractionPlanCustomizingByIdCommand genExtractionPlanCustomizingByIdCommandRetNull() {
        GetExtractionPlanCustomizingByIdCommand command = new GetExtractionPlanCustomizingByIdCommand();
        command.setDao(new GenericDAONullGetMockup());
        return command;
    }

    public static GetExtractionPlanCustomizingByIdCommand genExtractionPlanCustomizingByIdCommand() {
        GetExtractionPlanCustomizingByIdCommand command = new GetExtractionPlanCustomizingByIdCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static GetExtractionPlanCustomizingByIdCommand genExtractionPlanCustomizingByIdCommand2() {
        GetExtractionPlanCustomizingByIdCommand command = new GetExtractionPlanCustomizingByIdCommand();
        command.setDao(new GenericDAOEpcCompletoNoActivoMockup());
        return command;
    }

    public static GetExtractionPlanCustomizingByIdCommand genExtractionPlanCustomizingByIdEPCCompletoCommand() {
        GetExtractionPlanCustomizingByIdCommand command = new GetExtractionPlanCustomizingByIdCommand();
        command.setDao(new GenericDAOEpcCompleto());
        return command;
    }

    public static GetEvidenceExtractionServicesServerCommand genEvidenceExtractionServicesServerCommand() {
        GetEvidenceExtractionServicesServerCommand command = new GetEvidenceExtractionServicesServerCommand();
        command.setDao(new GenericDAOEESSMockup());
        return command;
    }

    public static SendExtractionPlanCommand genSendExtractionPlanCommand() {
        SendExtractionPlanCommand command = new SendExtractionPlanCommand();
        command.setDaoCorporateStructureManager(new CorporateStructureManagementHibernateDAOMock());
        command.setGenericDao(new GenericDAOMockup());
        command.setWizardManager(genWizardManager2());
        command.setEvidenceProviders(genListaEvidenceProvider(2, genEvidenceProviderType()));
        return command;

    }

    public static GetEvidenceProviderListCommand genEvidenceProviderListCommand() {
        GetEvidenceProviderListCommand command = new GetEvidenceProviderListCommand();
        command.setDao(new CorporateStructureManagementHibernateDAOMock());
        return command;
    }

    public static EvidenceProviderDTO genEvidenceProviderDTO(Integer id, String descripcion) {
        EvidenceProviderDTO providerDTO = new EvidenceProviderDTO();
        providerDTO.setId(id);
        providerDTO.setDescription(descripcion);
        return providerDTO;
    }

    public static GetSensorListCommand genSensorListCommand() {
        GetSensorListCommand command = new GetSensorListCommand();
        command.setDao(new CorporateStructureManagementHibernateDAOMock());
        return command;
    }

    public static SituationSensorDTO genSituationSensorDTO(Integer id, String name, String descripcion) {
        SituationSensorDTO sensorDTO = new SituationSensorDTO();
        sensorDTO.setId(id);
        sensorDTO.setName(name);
        sensorDTO.setDescription(descripcion);
        return sensorDTO;
    }

    public static MetricTemplateDTO genMetricTemplateDTO(Integer id, String name, String descripcion) {
        MetricTemplateDTO metricTemplateDTO = new MetricTemplateDTO();
        metricTemplateDTO.setId(id);
        metricTemplateDTO.setName(name);
        metricTemplateDTO.setDescription(descripcion);
        return metricTemplateDTO;
    }

    public static FormulasBySTAndStoreCommand genFormulasBySTAndStoreCommand() {
        FormulasBySTAndStoreCommand command = new FormulasBySTAndStoreCommand();
        command.setDao(new ObservedSituationHibernateDAOMock());
        return command;
    }

    public static SaveSensorsCommand genSaveSensorsCommand() {
        SaveSensorsCommand command = new SaveSensorsCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    public static CleanExtractionPlanMetricsCommand genCleanExtractionPlanMetricsCommand() {
        CleanExtractionPlanMetricsCommand command = new CleanExtractionPlanMetricsCommand();
        command.setDao(new ExtractionPlanCustomizingDAOMock());
        return command;
    }

    public static SaveExtractionPlanMetricCommand genSaveExtractionPlanMetricCommand() {
        SaveExtractionPlanMetricCommand command = new SaveExtractionPlanMetricCommand();
        command.setDao(new GenericDAOListMockup());
        return command;
    }

    public static List<ExtractionPlanMetricDTO> genExtractionPlanMetricDTOs(int cantidad) {
        List<ExtractionPlanMetricDTO> metricDTOs = new ArrayList<ExtractionPlanMetricDTO>();
        for (int i = 1; i <= cantidad; i++) {
            ExtractionPlanMetricDTO dto = genExtractionPlanMetricDTO(i, i, i, "NV" + i);
            metricDTOs.add(dto);
        }
        return metricDTOs;
    }

    public static ExtractionPlanMetricDTO genExtractionPlanMetricDTO(Integer id, Integer order, Integer metricTemplateId,
            String metricVariableName) {
        ExtractionPlanMetricDTO metricDTO = new ExtractionPlanMetricDTO();
        metricDTO.setId(id);
        metricDTO.setEvaluationOrder(order);
        metricDTO.setMetricTemplateId(metricTemplateId);
        metricDTO.setMetricVariableName(metricVariableName);
        metricDTO.setEvidenceProviderDTOs(new ArrayList<EvidenceProviderDTO>());
        return metricDTO;
    }

    public static InactivateEPCCustomizingCommand genInactivateEPCCustomizingCommand() {
        InactivateEPCCustomizingCommand command = new InactivateEPCCustomizingCommand();
        command.setDao(new GenericDAOMockup());
        return command;
    }

    private static void appendProvidersAllPlanMetrics(ExtractionPlanCustomizing epc) {
        for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
            epm.setEvidenceProviders(genListaEvidenceProvider(2, genEvidenceProviderType()));
        }
    }
}
