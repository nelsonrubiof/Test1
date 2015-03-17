package com.scopix.periscope.enums;

import java.io.Serializable;

/**
 * Enumeración para manejo de los tipos de marcas de una evidencia
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public enum EnumMarkType implements Serializable {
    CIRCLE("CIRCLE"), SQUARE("SQUARE");

    private final String name;

    private EnumMarkType(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}