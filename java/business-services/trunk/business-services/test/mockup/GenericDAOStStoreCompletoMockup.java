/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nelson
 */
public class GenericDAOStStoreCompletoMockup implements GenericDAO {

    private BusinessObject businessObject;

    public boolean exists(Serializable srlzbl, Class type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public <T> T get(Serializable id, Class<T> type) {
        Object o = null;
        try {
            o = type.newInstance();
            if (o instanceof BusinessObject) {
                ((BusinessObject) o).setId((Integer) id);
                setBusinessObject((BusinessObject) o);
            }
            if (o instanceof Store) {
                o = ManagerMock.genStore((Integer) id, "Store " + id, "Store " + id);
                ((Store) o).setEvidenceExtractionServicesServer(ManagerMock.genEvidenceExtractionServicesServer(
                        (Integer) id, "Evidence Extraction Services Server " + id));

            }
            if (o instanceof SituationTemplate) {
                o = ManagerMock.genSituationTemplate((Integer) id, "SituationTemplate " + id);
                ((SituationTemplate) o).setAreaType(ManagerMock.genAreaType((Integer) id, "AreaType" + id, "AreaType" + id));
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
        throw new UnsupportedOperationException("Not supported yet.");
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
