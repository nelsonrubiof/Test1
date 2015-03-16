/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Classes implementing this interface must obtain the requested evidence from the evidence provider 
 * and put it into the folder parametrized in system.properties, entry UploadJob.uploadLocalDir, 
 * to be uploaded to evidence server.
 *
 * @author marko.perich
 */
public interface ProviderAdaptor<EV extends EvidenceExtractionRequest, EP extends EvidenceProvider> {

    void prepareEvidence(EV evidenceRequest, EP evidenceProvider) throws ScopixException;
}
