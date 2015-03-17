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
 *  VideoConverterServiceImplTest.java
 * 
 *  Created on 16-08-2013, 12:38:11 PM
 * 
 */
package com.scopix.periscope.converter.services;

import com.scopix.periscope.converter.VideoConverterDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author carlos polo
 */
public class VideoConverterServiceImplTest {
    
    public VideoConverterServiceImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of convertVideo method, of class VideoConverterServiceImpl.
     */
    @Test
    public void testConvertVideo() throws ScopixException {
        //Crea instancia de la clase a probar
        VideoConverterServiceImpl videoConverterImpl = new VideoConverterServiceImpl();
        //Invoca método a probar
        VideoConverterDTO videoConverterDTO = new VideoConverterDTO();
        videoConverterDTO.setFileName("video.avi");
        videoConverterDTO.setUrlNotificacion("");
        
        videoConverterImpl.convertVideo(videoConverterDTO);
    }
}