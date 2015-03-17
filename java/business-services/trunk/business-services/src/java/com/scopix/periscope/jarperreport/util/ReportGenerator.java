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
 *  ReportGenerator.java
 * 
 *  Created on 16-04-2012, 05:26:12 PM
 * 
 */
package com.scopix.periscope.jarperreport.util;

//import com.scopix.periscope.periscopefoundation.exception.ScopixException;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Collection;
//import java.util.HashMap;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperRunManager;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.export.JRCsvExporter;
//import net.sf.jasperreports.engine.export.JRHtmlExporter;
//import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
//import net.sf.jasperreports.engine.export.JRRtfExporter;
//import net.sf.jasperreports.engine.export.JRXlsExporter;
//import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author nelson
 * @deprecated solo reports se generan por tableau
 */
public class ReportGenerator {

//    public byte[] generateReport(String jasperFile, Collection data, ReportType type, HashMap parameters)
//            throws ScopixException {
//        byte[] report;
//        try {
//            InputStream is = new FileInputStream(new File(jasperFile));
//            report = getReport(type, is, parameters, data, null);
//        } catch (FileNotFoundException e) {
//            throw new ScopixException(e);
//        }
//        return report;
//    }
//
//    public byte[] generateReportFromClasspath(String jasperFile, Collection data, ReportType type, HashMap parameters)
//            throws ScopixException {
//        byte[] report = null;
//        try {
//            ClassPathResource rs = new ClassPathResource(jasperFile);
//            InputStream is = rs.getInputStream();
//            report = getReport(type, is, parameters, data, null);
//        } catch (IOException e) {
//            throw new ScopixException(e);
//
//        }
//        return report;
//    }
//
//    public byte[] compileAndGenerateReport(String xmlPath, Collection data, ReportType type, HashMap parameters)
//            throws ScopixException {
//        byte[] report = null;
//        try {
//            InputStream is = new FileInputStream(new File(xmlPath));
//            JasperDesign design = JRXmlLoader.load(is);
//            JasperReport jasperReport = JasperCompileManager.compileReport(design);
//
//            report = getReport(type, is, parameters, data, jasperReport);
//        } catch (FileNotFoundException e) {
//            throw new ScopixException(e);
//        } catch (JRException e) {
//            throw new ScopixException(e);
//        }
//        return report;
//    }
//
//    public byte[] compileAndGenerateReportFromClasspath(String xmlPath, Collection data, ReportType type, HashMap parameters)
//            throws ScopixException {
//        byte[] report = null;
//        try {
//            ClassPathResource rs = new ClassPathResource(xmlPath);
//            InputStream is = rs.getInputStream();
//            JasperDesign design = JRXmlLoader.load(is);
//            JasperReport jasperReport = JasperCompileManager.compileReport(design);
//            report = getReport(type, is, parameters, data, jasperReport);
//        } catch (IOException e) {
//            throw new ScopixException(e);
//        } catch (JRException e) {
//            throw new ScopixException(e);
//        }
//        return report;
//    }
//
//    private byte[] getReport(ReportType type, InputStream is, HashMap parameters, Collection data,
//            JasperReport jasperReport) throws ScopixException {
//        byte[] report = null;
//        try {
//            report = null;
//            JasperPrint jasperPrint = null;
//            JRDataSource datos = new JRBeanCollectionDataSource(data);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            if (jasperReport != null) {
//                jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datos);
//            } else {
//                jasperPrint = JasperFillManager.fillReport(is, parameters, datos);
//            }
//            switch (type) {
//                case CSV:
//                    JRCsvExporter csvExporter = new JRCsvExporter();
//                    csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//                    csvExporter.exportReport();
//                    report = baos.toByteArray();
//                    break;
//                case EXCEL:
//                    JRXlsExporter xlsExporter = new JRXlsExporter();
//                    xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//                    xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//                    xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
//                    xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
//                    xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//                    xlsExporter.exportReport();
//                    report = baos.toByteArray();
//                    break;
//                case HTML:
//                    File tmpFile = File.createTempFile("tempFile", "", new File("/tmp"));
//                    tmpFile.delete();
//                    tmpFile.mkdir();
//                    String path = tmpFile.getCanonicalPath();
//                    JRHtmlExporter htmlExporter = new JRHtmlExporter();
//                    htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//                    htmlExporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
//                    htmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
//                    htmlExporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
//                    htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, path);
//                    htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, (new StringBuilder()).append(path).
//                            append(File.separator).toString());
//                    htmlExporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//                    htmlExporter.exportReport();
//                    report = baos.toByteArray();
//                    break;
//                case RTF:
//                    JRRtfExporter rtfExporter = new JRRtfExporter();
//                    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//                    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//                    rtfExporter.exportReport();
//                    report = baos.toByteArray();
//                    break;
//                default:
//                    if (jasperReport != null) {
//                        report = JasperRunManager.runReportToPdf(jasperReport, parameters, datos);
//                    } else {
//                        report = JasperRunManager.runReportToPdf(is, parameters, datos);
//                    }
//                    break;
//            }
//        } catch (JRException e) {
//            throw new ScopixException(e);
//        } catch (IOException e) {
//            throw new ScopixException(e);
//        }
//        return report;
//    }
}
