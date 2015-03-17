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
 * ConvertDTOToObject.java
 * 
 * Created on 06-06-2013, 05:45:15 PM
 */
package com.scopix.periscope.operatorimages.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class ConvertDTOToObject {

    private static Logger log = Logger.getLogger(ConvertDTOToObject.class);
    /**
     * Formamto de Fecha para conversiones
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * convierte un Objeto en otro
     *
     * @param origen objeto de Origen
     * @param destino objeto destino
     * @return Object objeto destino retornado
     */
    @SuppressWarnings(value = "unchecked")
    public static Object convert(Object origen, Object destino) {
        log.info("start");
        if (origen != null) {
            if (origen instanceof String) {
                destino = origen;
            } else {
                // recorremos todos los field de origen y los tratamos de traspasar a destino
                for (Field f : origen.getClass().getDeclaredFields()) { // recorremos los field y verificamos que exista un

                    try {
                        String get = "get" + StringUtils.capitalize(f.getName());
                        String set = "set" + StringUtils.capitalize(f.getName());
                        Method methodOrigen = findMethod(origen.getClass(), get);
                        if (methodOrigen == null) {
                            continue;
                        }
                        Object valueOrigen = methodOrigen.invoke(origen);
                        Method methodDestino = findMethod(destino.getClass(), set);
                        if (methodDestino == null) {
                            continue;
                        }
                        Class typeDestino = methodDestino.getParameterTypes()[0];
                        // log.debug("[typeDestino:" + typeDestino + "][valueOrigen:" + valueOrigen + "]");
                        if (valueOrigen instanceof List) {
                            // recuperamos la clase que sera nuestra nueva lista
                            Class classList = getParameterArgClass(methodDestino);
                            // verificar y recorre la lista
                            List l = converToList((List) valueOrigen, classList);
                            methodDestino.invoke(destino, l);
                        } else {
                            // verificamos si son del mismo tipo se espera solo un parametro en los set
                            if (typeDestino == valueOrigen.getClass()) {
                                methodDestino.invoke(destino, valueOrigen);
                            } else if (typeDestino == Date.class) {
                                Date date = convertToDate(valueOrigen);
                                methodDestino.invoke(destino, date);
                            }
                        }

                    } catch (IllegalAccessException e) {
                        log.error(e, e);
                    } catch (IllegalArgumentException e) {
                        log.error(e, e);
                    } catch (InvocationTargetException e) {
                        log.error(e, e);
                    }
                }
            }
        }
        log.info("end");
        return destino;
    }

    /**
     * Busca un metodo dentro de una clase
     *
     * @param clazz Class donde buscar el metodo
     * @param methodName nombre de metodo soliciatdo
     * @return Method metodo solicitado
     */
    public static Method findMethod(Class<?> clazz, String methodName) {
        // log.info("start [clazz:" + clazz + "][methodName:" + methodName + "]");
        Method ret = null;
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                ret = method;
                break;
            }
        }
        // log.info("end [ret:" + ret + "]");
        return ret;
    }

    /**
     * Convierte un objeto a date
     *
     * @param value Objeto a convertir en date
     * @return Date resultado de la conversion null en caso no especificado
     */
    public static Date convertToDate(Object value) {
        // log.info("start [value:" + value + "]");
        Date date = null;
        try {
            if (value instanceof String) {
                date = DateUtils.parseDate((String) value, new String[] { FORMAT_DATE_TIME });
            }
        } catch (ParseException e) {
            log.error("Error formateando Fecha " + value.toString() + " e:" + e, e);
        }
        // log.info("end [date:" + date + "]");
        return date;
    }

    /**
     * Genera una lista con un objeto recibido
     *
     * @param <T> Tipo de clase necesaria para la lista
     * @param value Lista que se desea Recorre
     * @param typeDestino clase nueva lista
     * @return List lista con objetos homologaods
     */
    @SuppressWarnings(value = "unchecked")
    public static <T> List<T> converToList(List<Object> value, Class<T> typeDestino) {
        // log.info("start " + typeDestino);
        List<T> l = createListOfType(typeDestino);
        try {
            for (Object origen : value) {
                Object destino = Class.forName(typeDestino.getName()).newInstance();
                if (destino != null) {
                    destino = convert(origen, destino);
                    l.add((T) destino);
                }
            }
        } catch (ClassNotFoundException e) {
            log.error(e, e);
        } catch (InstantiationException e) {
            log.error(e, e);
        } catch (IllegalAccessException e) {
            log.error(e, e);
        }
        // log.info("end ");
        return l;
    }

    /**
     *
     * @param <T> Class de la cual queremos el <E> de la nueva lista
     * @param type tipo de clase
     * @return ArrayList<E> donde E es dato por el type
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> createListOfType(Class<T> type) {
        return new ArrayList<T>();
    }

    /**
     *
     * @param method Method Set del cual recuperamos los parametros de la lista <E>
     * @return Class <E> para la nueva lista
     */
    public static Class getParameterArgClass(Method method) {
        Class parameterArgClass = null;
        Type[] genericParameterTypes = method.getGenericParameterTypes();

        for (Type genericParameterType : genericParameterTypes) {
            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericParameterType;
                Type[] parameterArgTypes = aType.getActualTypeArguments();
                for (Type parameterArgType : parameterArgTypes) {
                    parameterArgClass = (Class) parameterArgType;
                }
            }
        }
        return parameterArgClass;
    }
}