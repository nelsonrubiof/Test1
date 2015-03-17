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
 * StringUtil.java
 *
 * Created on 30-06-2008, 04:43:55 PM
 *
 */
package com.scopix.periscope.periscopefoundation.util;

import java.util.HashMap;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
public class StringUtil {
    //CHECKSTYLE:OFF

    /**
     * Expresion regular para una IP
     */
    public static final String IP_ADDRESS =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    //CHECKSTYLE:ON
    /**
     * Expresion regular para un String
     */
    public static final String STRING = "^([a-zA-Z0-9._!%+-/]*)$";
    /**
     * Expresion regular para un Numero
     */
    public static final String NUMBER = "^([-+]?[0-9]*)$";
    /**
     * Expresion regular para un Float
     */
    public static final String FLOAT_NUMBER = "^([-+]?[0-9]*\\.?[0-9]*)$";
    /**
     * Expresion regular para un Date
     */
    public static final String DATE = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
    /**
     * Expresion regular para un Email
     */
    public static final String EMAIL = "^([a-zA-Z0-9._%+-]*@[a-zA-Z0-9.-]*\\.[a-zA-Z]{2,4})$";
    /**
     * Expresion regular para una Hora
     */
    public static final String HOUR = "^((0?[0-9]|1[0-9]|2[0123])(:[0-5]\\d){0,2})$";
    /**
     * Expresion regular para un Protocolo
     */
    public static final String PROTOCOL = "^((ht|f)tp(s?))$";
    /**
     * Expresion regular para un Puerto
     */
    public static final String PORT = "^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[0-5]?([0-9]){0,3}[0-9])$";

    /**
     * Return a HashMap from a String sepparated by ;
     *
     * @param data String de mapa
     * @return HashMap<String, String> con los valores llave, valor
     */
    public static HashMap<String, String> getHashMapFromString(String data) {
        HashMap<String, String> result = new HashMap<String, String>();
        if (data != null && data.length() > 0) {
            String[] firstSplit = data.split(";");
            if (firstSplit != null) {
                for (String s : firstSplit) {
                    String[] secondSplit = s.split("=");
                    if (secondSplit != null && secondSplit.length == 2) {
                        result.put(secondSplit[0], secondSplit[1]);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Validate a String with a simple pattern
     *
     * @param data String datos a validar
     * @param pattern cadena con cual validar el string
     * @return boolean si es valida o no la cadena
     */
    public static boolean validateString(String data, String pattern) {
        boolean result = false;
        if (data != null && pattern != null && data.length() > 0 && pattern.length() > 0) {
            result = data.matches(pattern);
        }
        return result;
    }

    /**
     * Validate a String with a date hour pattern
     *
     * @param data String con fecha
     * @param pattern cadena con cual validar fecha
     * @return String en formato de hora o Date segun sea el caso
     */
    public static String validateDateHour(String data, String pattern) {
        String format = null;
        if (data != null && pattern != null && data.length() > 0 && pattern.length() > 0) {
            boolean result = data.matches(pattern);
            if (result) {
                if (pattern.equals(DATE)) {
                    String separacion = (data.indexOf("-") >= 0 ? "-" : data.indexOf("/") >= 0 ? "/" : ".");
                    String[] split = data.split(separacion.equals(".") ? "\\." : separacion);
                    String pivot = "";
                    format = "";
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            pivot = "d";
                        } else if (i == 1) {
                            format += separacion;
                            pivot = "M";
                        } else if (i == 2) {
                            format += separacion;
                            pivot = "y";
                        }
                        for (int j = 0; j < split[i].length(); j++) {
                            format += pivot;
                        }
                    }
                } else if (pattern.equals(HOUR)) {
                    String[] split = data.split(":");
                    String pivot = "";
                    format = "";
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            pivot = "H";
                        } else if (i == 1) {
                            format += ":";
                            pivot = "m";
                        } else if (i == 2) {
                            format += ":";
                            pivot = "s";
                        }
                        for (int j = 0; j < split[i].length(); j++) {
                            format += pivot;
                        }
                    }
                }
            }
        }
        return format;
    }

    /**
     * Check a String against a complex pattern sepparated by ;
     *
     * @param data String con datos a chequear
     * @param pattern cadena de origen para validar
     * @return boolean si todas las cadenas coinciden
     */
    public static boolean checkPattern(String data, String pattern) {
        boolean result = true;
        if (data != null && pattern != null && data.length() > 0 && pattern.length() > 0) {
            String[] firstData = data.split(";");
            String[] firstPattern = pattern.split(";");
            for (int i = 0; i < firstPattern.length; i++) {
                String[] secondData = firstData[i].split("=");
                String[] secondPattern = firstPattern[i].split("=");
                result = (secondData[0].equals(secondPattern[0]));
                if (result) {
                    if ("[IP_ADDRESS]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(IP_ADDRESS));
                    } else if ("[STRING]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(STRING));
                    } else if ("[NUMBER]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(NUMBER));
                    } else if ("[FLOAT_NUMBER]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(FLOAT_NUMBER));
                    } else if ("[DATE]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(DATE));
                    } else if ("[EMAIL]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(EMAIL));
                    } else if ("[PROTOCOL]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(PROTOCOL));
                    } else if ("[PORT]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(PORT));
                    }
                }
                if (!result) {
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
