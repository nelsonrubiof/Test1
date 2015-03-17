/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderType;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nelson
 */
public class GenericDAOMockup implements GenericDAO {

    private BusinessObject businessObject;

    public boolean exists(Serializable srlzbl, Class type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T get(Serializable serializable, Class<T> type) {
        Object o = null;
        try {
            o = type.newInstance();
            if (o instanceof BusinessObject) {
                ((BusinessObject) o).setId((Integer) serializable);
                setBusinessObject((BusinessObject) o);
            }
            if (o instanceof ObservedSituation) {
                ManagerMock.appendDatosObservedSituation((ObservedSituation) o);
            }
            if (o instanceof EvidenceProvider ) {
                o = ManagerMock.genEvidenceProvider((Integer) serializable, "EP " + serializable, "EP " + serializable);
                (( EvidenceProvider)o).setEvidenceProviderType(ManagerMock.genEvidenceProviderType());
            }
            if (o instanceof EvidenceExtractionServicesServer) {
                o = ManagerMock.genEvidenceExtractionServicesServer((Integer) serializable, "SERVER MOCK", "192.168.4.202", "111",
                        "user", "pwd", "9999", "888", "http://localhost");
            }

        } catch (InstantiationException e) {
            //no hacemos nada
        } catch (IllegalAccessException e) {
            //no hacemos nada
        }
        return (T) o;
    }

    public <T> List<T> getAll(Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(Object o) {
        //ni hacemos nada
    }

    public void remove(Serializable srlzbl, Class type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save(BusinessObject bo) {
        setBusinessObject(bo);
    }

    public void save(BusinessObject... bos) {
        //no hacemos nada
    }

    public void save(List list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BusinessObject getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(BusinessObject businessObject) {
        this.businessObject = businessObject;
    }
}
