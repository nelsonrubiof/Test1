/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mockup;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author nelson
 */
public class ObserverMetricMock {

    public static ObservedMetric genNewObservedMetric() {

        ObservedMetric om = new ObservedMetric();
        Evidence e = new Evidence();
        e.setEvidencePath("test.xml");
        Calendar c = Calendar.getInstance();
        c.set(2010, 7, 1, 17, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        e.setEvidenceDate(c.getTime());
        EvidenceServicesServer ess = new EvidenceServicesServer();
        ess.setEvidencePath("//127.0.0.1/periscope.data/");
        e.setEvidenceServicesServer(ess);
        Metric metric = new Metric();
        Area area = new Area();
        Corporate corporate = new Corporate();
        corporate.setName("corpTest");
        Store store = new Store();
        store.setName("stTest");
        metric.setArea(area);
        area.setStore(store);
        store.setCorporate(corporate);
        om.setMetric(metric);

        om.getEvidences().add(e);
        return om;

    }

    public static ObservedMetric genObservedMetric(int year, int month , int day, int hour, int min) {
        ObservedMetric om = new ObservedMetric();
        Evidence e = new Evidence();
        e.setEvidencePath("test.xml");
        Calendar c = Calendar.getInstance();
        c.set(year, month -1, day, hour, min, 0);
        c.set(Calendar.MILLISECOND, 0);
        e.setEvidenceDate(c.getTime());
        EvidenceServicesServer ess = new EvidenceServicesServer();
        ess.setEvidencePath("//127.0.0.1/periscope.data/");
        e.setEvidenceServicesServer(ess);
        Metric metric = new Metric();
        Area area = new Area();
        Corporate corporate = new Corporate();
        corporate.setName("corpTest");
        Store store = new Store();
        store.setName("stTest");
        metric.setArea(area);
        area.setStore(store);
        store.setCorporate(corporate);
        om.setMetric(metric);

        om.getEvidences().add(e);
        return om;
    }
}
