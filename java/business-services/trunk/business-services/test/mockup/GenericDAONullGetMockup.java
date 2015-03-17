/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nelson
 */
public class GenericDAONullGetMockup implements GenericDAO {

    private BusinessObject businessObject;

    public boolean exists(Serializable srlzbl, Class type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //retorna null a cualquer llamada
    public <T> T get(Serializable serializable, Class<T> type) {
        Object o = null;
        
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
