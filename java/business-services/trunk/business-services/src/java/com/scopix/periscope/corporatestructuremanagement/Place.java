/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Place.java
 *
 * Created on 27-03-2008, 03:46:21 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Tavo
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Place extends BusinessObject {

    @Lob
    private String description;

    @Lob
    private String name;

    @ManyToOne()
    private Place parentPlace;

    @ManyToOne()
    private PlaceType placeType;

    @OneToMany(mappedBy = "parentPlace", fetch = FetchType.LAZY)
    private List<Place> places;

    public Place getParentPlace() {
        return parentPlace;
    }

    public void setParentPlace(Place parentPlace) {
        this.parentPlace = parentPlace;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public List<Place> getPlaces() {
        if (places == null) {
            places = new ArrayList<Place>();
        }
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
