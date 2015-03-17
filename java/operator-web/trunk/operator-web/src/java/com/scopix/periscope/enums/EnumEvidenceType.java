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

    NO_SET("NOT_SET"), VIDEO("VIDEO"), IMAGE("IMAGE");

    private final String name;

    private EnumEvidenceType(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}