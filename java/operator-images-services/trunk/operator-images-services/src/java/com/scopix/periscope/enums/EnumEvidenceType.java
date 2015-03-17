package com.scopix.periscope.enums;

import java.io.Serializable;

/**
 * Enumeración para manejo de los tipos de evidencias
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public enum EnumEvidenceType implements Serializable {

    VIDEO("VIDEO"), IMAGE("IMAGE"), PROOF("PROOF"), // it's also an image but must be differenced for path building
    TEMPLATE("TEMPLATE"), // it's also an image but must be differenced for path building
    VTT("VTT"), SPRITE("SPRITE");

    private final String name;

    private EnumEvidenceType(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}