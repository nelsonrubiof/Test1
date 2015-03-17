/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  SortUtilTest.java
 * 
 *  Created on 10-09-2010, 10:05:54 AM
 * 
 */
package com.scopix.periscope.periscopefoundation.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class SortUtilTest {

    private static ArrayList<Vendor> listaOrdenar;

    public static ArrayList<Vendor> getListaOrdenar() {
        return listaOrdenar;
    }

    public static void setListaOrdenar(ArrayList<Vendor> aListaOrdenar) {
        listaOrdenar = aListaOrdenar;
    }

    public SortUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("SortUtilTest");
        setListaOrdenar(new ArrayList<Vendor>());
        Calendar c = Calendar.getInstance();
        //2010-03-28
        c.set(2010, 2, 28, 0, 0, 0);
        getListaOrdenar().add(new Vendor(1, "Eduardo", "Rubio", c.getTime(), new Vendor(5, "test", "test", c.getTime(), null)));
        //2010-04-05
        c.set(2010, 3, 5, 0, 0, 0);
        getListaOrdenar().add(new Vendor(2, "Nelson", "Perez", c.getTime(), new Vendor(6, "test2", "test", c.getTime(), null)));
        //2010-01-05
        c.set(2010, 0, 5, 0, 0, 0);
        getListaOrdenar().add(new Vendor(3, "Ariel", "Tapia", c.getTime(), new Vendor(7, "test5", "test", c.getTime(), null)));
        //2010-03-28
        c.set(2010, 2, 28, 0, 0, 0);
        getListaOrdenar().add(new Vendor(4, "Arturo", "Gonzalez", c.getTime(), new Vendor(8, "test4", "test", c.getTime(), null)));
        //2010-07-05
        c.set(2010, 6, 5, 0, 0, 0);
        getListaOrdenar().add(new Vendor(5, "Sebastian", "Rubio", c.getTime(), new Vendor(9, "test3", "test", c.getTime(), null)));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSortByColumn() {
        System.out.println("sortByColumn");
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("name", Boolean.TRUE);
        //Orden descendiente por nombre 1 columna
        List<Vendor> lista = new ArrayList<Vendor>();
        lista.addAll(getListaOrdenar());

        SortUtil.sortByColumn(cols, lista);
        assertEquals("Sebastian", lista.get(0).name);
        assertEquals("Nelson", lista.get(1).name);
        assertEquals("Eduardo", lista.get(2).name);
        assertEquals("Arturo", lista.get(3).name);
        assertEquals("Ariel", lista.get(4).name);

    }

    @Test
    public void testSortByColumn2() {
        System.out.println("sortByColumn2");
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("apePat", Boolean.TRUE);
        cols.put("name", Boolean.FALSE);
        //Orden descendiente por Apellido y ascendente por nombre 2 columna
        List<Vendor> lista = new ArrayList<Vendor>();
        lista.addAll(getListaOrdenar());

        SortUtil.sortByColumn(cols, lista);
        assertEquals("Tapia", lista.get(0).apePat);
        assertEquals("Rubio", lista.get(1).apePat);
        assertEquals("Eduardo", lista.get(1).name);
        assertEquals("Rubio", lista.get(2).apePat);
        assertEquals("Sebastian", lista.get(2).name);
        assertEquals("Perez", lista.get(3).apePat);
        assertEquals("Gonzalez", lista.get(4).apePat);
    }

    @Test
    public void testSortByColumn3() {
        System.out.println("sortByColumn3");
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("fecha", Boolean.FALSE);
        cols.put("apePat", Boolean.TRUE);
        cols.put("name", Boolean.FALSE);
        //Orden ascendente fecha, descendiente por Apellido y ascendente por nombre 3 columna
        List<Vendor> lista = new ArrayList<Vendor>();
        lista.addAll(getListaOrdenar());

        SortUtil.sortByColumn(cols, lista);
        assertEquals("Ariel", lista.get(0).name);
        assertEquals("Eduardo", lista.get(1).name);
        assertEquals("Arturo", lista.get(2).name);
        assertEquals("Nelson", lista.get(3).name);
        assertEquals("Sebastian", lista.get(4).name);
    }

    @Test
    public void testSortByColumn4() {
        System.out.println("sortByColumn4");
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("hijo.id", Boolean.FALSE);
        //Orden ascendente fecha, descendiente por Apellido y ascendente por nombre 3 columna
        List<Vendor> lista = new ArrayList<Vendor>();
        lista.addAll(getListaOrdenar());

        SortUtil.sortByColumn(cols, lista);
        assertEquals("Eduardo", lista.get(0).name);
        assertEquals("Nelson", lista.get(1).name);
        assertEquals("Ariel", lista.get(2).name);
        assertEquals("Arturo", lista.get(3).name);
        assertEquals("Sebastian", lista.get(4).name);
    }

    public static final class Vendor {

        private Integer id;
        private String name;
        private String apePat;
        private Date fecha;
        private Vendor hijo;

        public Vendor(Integer i, String nom, String ape, Date date, Vendor v) {
            super();
            setId(i);
            setName(nom);
            setFecha(date);
            setApePat(ape);
            setHijo(v);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        public String getApePat() {
            return apePat;
        }

        public void setApePat(String apePat) {
            this.apePat = apePat;
        }

        public Vendor getHijo() {
            return hijo;
        }

        public void setHijo(Vendor hijo) {
            this.hijo = hijo;
        }
    }
}
