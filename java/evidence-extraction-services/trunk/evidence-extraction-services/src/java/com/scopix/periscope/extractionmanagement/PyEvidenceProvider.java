/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * PyEvidenceProvider.java
 *
 * Created on 10-06-2008, 11:16:06 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import javax.persistence.Entity;

/**
 *
 * @author marko.perich
 */
@Entity
public class PyEvidenceProvider extends EvidenceProvider {

    private String galleryDir;
}