/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.services.controllers;

import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evaluationmanagement.services.webservices.client.EvaluationWebServicesClient;
import com.scopix.periscope.evidencemanagement.FileManager;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean
public class XMOOVController extends AbstractController {

    Logger log = Logger.getLogger(XMOOVController.class);
// SCRIPT CONFIGURATION
//------------------------------------------------------------------------------------------
// MEDIA PATH
//
// you can configure these settings to point to video files outside the public html folder.
//------------------------------------------------------------------------------------------
// points to server root
    public static final String XMOOV_PATH_ROOT = "";
// points to the folder containing the video files.
    public static final String XMOOV_PATH_FILES = "/ffmpeg/out/";
//------------------------------------------------------------------------------------------
// SCRIPT BEHAVIOR
//------------------------------------------------------------------------------------------
//set to TRUE to use bandwidth limiting.
    public static final Boolean XMOOV_CONF_LIMIT_BANDWIDTH = true;
//set to FALSE to prohibit caching of video files.
    public static final Boolean XMOOV_CONF_ALLOW_FILE_CACHE = false;
//------------------------------------------------------------------------------------------
// BANDWIDTH SETTINGS
//
// these settings are only needed when using bandwidth limiting.
//
// bandwidth is limited my sending a limited amount of video data(XMOOV_BW_PACKET_SIZE),
// in specified time intervals(XMOOV_BW_PACKET_INTERVAL).
// avoid time intervals over 1.5 seconds for best results.
//
// you can also control bandwidth limiting via http command using your video player.
// the function getBandwidthLimit($part) holds three preconfigured presets(low, mid, high),
// which can be changed to meet your needs
//------------------------------------------------------------------------------------------
//set how many kilobytes will be sent per time interval
    public static final Integer XMOOV_BW_PACKET_SIZE = 90;
//set the time interval in which data packets will be sent in seconds.
    public static final Double XMOOV_BW_PACKET_INTERVAL = 1.5;
//set to TRUE to control bandwidth externally via http.
    public static final Boolean XMOOV_CONF_ALLOW_DYNAMIC_BANDWIDTH = true;

//------------------------------------------------------------------------------------------
// DYNAMIC BANDWIDTH CONTROL
//------------------------------------------------------------------------------------------
    private Double getBandwidthIntervalLimit() {
        /*
        switch($_GET[XMOOV_GET_BANDWIDTH])
        {
        case 'low' :
        return 0.5;
        break;
        case 'mid' :
        return 0.5;
        break;
        case 'high' :
        return 0.2;
        break;
        case 'off' :
        return 0;
        break;
        default :
        return XMOOV_BW_PACKET_INTERVAL;
        break;
         */
        return XMOOV_BW_PACKET_INTERVAL;
    }

    private Integer getBandwidthSizeLimit() {
        /*
        switch($_GET[XMOOV_GET_BANDWIDTH])
        {
        case 'low' :
        return 20;
        break;
        case 'mid' :
        return 40;
        break;
        case 'high' :
        return 90;
        break;
        default :
        return XMOOV_BW_PACKET_SIZE;
        break;
        }
         */
        return XMOOV_BW_PACKET_SIZE;
    }
//------------------------------------------------------------------------------------------
// INCOMING GET VARIABLES CONFIGURATION
//
// use these settings to configure how video files, seek position and bandwidth settings are accessed by your player
//------------------------------------------------------------------------------------------
    public static final String XMOOV_GET_FILE = "file";
    public static final String XMOOV_GET_OBSERVED_METRIC_ID = "omId";
    public static final String XMOOV_GET_EVIDENCE_ID = "id";
    public static final String XMOOV_GET_SESSION_ID = "sessionId";
    public static final String XMOOV_GET_POSITION = "start";
    public static final String XMOOV_GET_AUTHENTICATION = "key";
    public static final String XMOOV_GET_BANDWIDTH = "bw";
// END SCRIPT CONFIGURATION - do not change anything beyond this point if you do not know what you are doing
//------------------------------------------------------------------------------------------
// PROCESS FILE REQUEST
//------------------------------------------------------------------------------------------
    public static final byte[] FLV_HEADER = new byte[]{
        (byte) 0x46, (byte) 0x4C, (byte) 0x56,
        (byte) 0x01,
        (byte) 0x01,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x09,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x09
    };

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String fileName = null;
        Integer evidenceId = request.getParameter(XMOOV_GET_EVIDENCE_ID) != null ? new Integer(request.getParameter(
                XMOOV_GET_EVIDENCE_ID)) : null;
        log.debug("[handleRequestInternal] evidence = " + evidenceId);
        Integer observedMetricId = request.getParameter(XMOOV_GET_OBSERVED_METRIC_ID) != null ? new Integer(request.getParameter(
                XMOOV_GET_OBSERVED_METRIC_ID)) : null;
        log.debug("[handleRequestInternal] observedMetric = " + observedMetricId);
        long sessionId = request.getParameter(XMOOV_GET_SESSION_ID) !=
                null ? new Long(request.getParameter(XMOOV_GET_SESSION_ID)).longValue() : 0L;

        if (observedMetricId != null && evidenceId != null) {
            EvaluationWebServices webService = SpringSupport.getInstance().findBeanByClassName(EvaluationWebServicesClient.class).
                    getWebService();
            FileManager fileManager = SpringSupport.getInstance().findBeanByClassName(FileManager.class);
            String evidencePath = webService.getEvidencesPath(observedMetricId, evidenceId, sessionId);

            log.debug("evidence path = " + evidencePath);
            Map map = fileManager.getFile(evidencePath);
            InputStream is = (InputStream) map.get("is");
            Long size = (Long) map.get("size");

            if (is != null) {
// get seek position
                Long seekPos = request.getParameter(XMOOV_GET_POSITION) != null ? new Long(
                        request.getParameter(XMOOV_GET_POSITION)) : null;
//extract fileName
                fileName = evidencePath.substring(evidencePath.lastIndexOf("/") + 1, evidencePath.length());

// assemble packet interval
                Double packetInterval = (XMOOV_CONF_ALLOW_DYNAMIC_BANDWIDTH && request.getParameter(XMOOV_GET_BANDWIDTH) != null)
                        ? getBandwidthIntervalLimit() : XMOOV_BW_PACKET_INTERVAL;
// assemble packet size
                Integer packetSize = ((XMOOV_CONF_ALLOW_DYNAMIC_BANDWIDTH && request.getParameter(XMOOV_GET_BANDWIDTH) !=
                        null) ? getBandwidthSizeLimit() : XMOOV_BW_PACKET_SIZE) * 1042;

                OutputStream out = response.getOutputStream();
                Long fileSize = size - ((seekPos > 0) ? seekPos + 1 : 0);
// SEND HEADERS
                if (!XMOOV_CONF_ALLOW_FILE_CACHE) {
// prohibit caching (different methods for different clients)
                    response.addHeader("Expires", "Thu, 19 Nov 1981 08:52:00 GMT");
                    response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
                    response.addHeader("Pragma", "no-cache");
                }

// content headers
                response.setContentType("video/x-flv");
                response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.setContentLength(fileSize.intValue());

// FLV file format header
                if (seekPos != 0) {
                    out.write(FLV_HEADER);
                }

                is.skip(seekPos);

// output file
                byte[] buf = new byte[packetSize];
                while (is.read(buf) > -1) {
//// use bandwidth limiting
                    if (XMOOV_CONF_LIMIT_BANDWIDTH && packetInterval > 0) {
//// get start time
                        Long timeStart = System.currentTimeMillis();
//// output packet
                        out.write(buf);
//
//// get end time
                        Long timeStop = System.currentTimeMillis();
//// wait if output is slower than $packet_interval
                        Long timeDifference = timeStop - timeStart;
                        if (timeDifference < packetInterval) {
                            try {
                                Thread.sleep((long) (packetInterval * 1000) - timeDifference * 1000);
                            } catch (Exception e) {
                                log.debug("[handleRequestInternal] error: " + e.getMessage());
                            }
                        }
                    } else {
//// output file without bandwidth limiting
                        out.write(buf);
                    }
                }
            }
        } else {
            PrintWriter out = response.getWriter();
            //CHECKSTYLE:OFF
            out.println("<b>ERROR:</b> xmoov-php could not find (" + fileName + ") please check your settings.");
            System.out.println("<b>ERROR:</b> xmoov-php could not find (" + fileName + ") please check your settings.");
            //CHECKSTYLE:ON
            out.close();
            return null;
        }
        return null;
    }
}