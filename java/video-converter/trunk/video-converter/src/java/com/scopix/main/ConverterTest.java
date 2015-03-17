/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * ConverterTest.java
 * 
 * Created on 12/08/2014
 */
package com.scopix.main;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.scopix.periscope.converter.VideoConverterDTO;

public class ConverterTest {

    public static void main(String[] args) {
        WebClient serviceClient = WebClient.create("http://173.204.188.250:8080/video-converter/services/REST/");
        serviceClient.path("/convertVideo").accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);

        VideoConverterDTO videoConverterDTO = new VideoConverterDTO();
        videoConverterDTO.setFileName("20140811_168623.asf");
        videoConverterDTO.setUrlNotificacion("http://prueba");

        Response response = serviceClient.post(videoConverterDTO, Response.class);
        System.out.println("response: " + response.getStatusInfo().getStatusCode());
    }
}