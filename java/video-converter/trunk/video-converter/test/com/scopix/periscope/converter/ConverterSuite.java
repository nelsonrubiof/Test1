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
 *  ConverterSuite.java
 * 
 *  Created on 16-08-2013, 12:38:12 PM
 * 
 */
package com.scopix.periscope.converter;

import com.scopix.periscope.converter.VideoTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author carlos polo
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.scopix.periscope.converter.ffmpeg.FfmpegSuite.class, com.scopix.periscope.converter.thread.ThreadSuite.class, VideoTest.class, com.scopix.periscope.converter.services.ServicesSuite.class})
public class ConverterSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}