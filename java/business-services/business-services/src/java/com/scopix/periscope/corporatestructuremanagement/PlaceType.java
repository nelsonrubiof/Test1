/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * PlaceType.java
 *
 * Created on 27-03-2008, 04:14:09 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import org.hibernate.validator.NotNull;

/**
 *
 * @author Tavo
 */
@Entity
public class PlaceType extends BusinessObject {

    @NotNull
    @Lob
    private String description;

    @OneToMany(mappedBy = "placeType", fetch = FetchType.LAZY)
    private List<Place> place;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Place> getPlace() {
        if (place == null) {
            place = new ArrayList<Place>();
        }
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }
}
