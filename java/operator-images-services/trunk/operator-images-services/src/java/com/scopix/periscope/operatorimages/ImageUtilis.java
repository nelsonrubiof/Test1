/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * ImageUtilis.java
 * 
 * Created on 26-04-2013, 11:31:48 AM
 */
package com.scopix.periscope.operatorimages;

import java.awt.Color;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public final class ImageUtilis {

    private static Logger log = Logger.getLogger(ImageUtilis.class);

    /**
     * Retorna un objeto Color dado el color solicitado
     *
     * @param colorName nombre a recupera ej red o RED
     * @return Color
     */
    public static Color getColorFromName(String colorName) {
        Color color = null;
        try {
            Field field = Class.forName("java.awt.Color").getField(colorName);
            color = (Color) field.get(null);
        } catch (Exception e) {
            log.warn(e);
        }
        return color;
    }

    /**
     * Retorna un Color dado un ARGB
     *
     *
     * @param argb String del hexadecimal para ARGB FF000000 negro
     * @return Color para el ARGB
     */
    public static Color getColorFromRGB(String argb) {
        long value = Long.parseLong(argb, 16);
        int blue = (int) (value & 255);
        int green = (int) ((value >> 8) & 255);
        int red = (int) ((value >> 16) & 255);
        int alpha = (int) ((value >> 24) & 255);
        Color c = new Color(red, green, blue, alpha);
        return c;
    }
}
