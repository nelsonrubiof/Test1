package com.scopix.periscope.enums;

import java.io.Serializable;

/**
 * Enumeración para manejo de los tipos de evaluaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public enum EnumEvaluationType implements Serializable {

    YES_NO("YES_NO"), COUNTING("COUNTING"), MEASURE_TIME("MEASURE_TIME"), NUMBER_INPUT("NUMBER_INPUT"), NOT_DEFINED("NOT_DEFINED");

    private final String name;

    private EnumEvaluationType(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}