/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceFileDAO.java
 * 
 * Created on 18-06-2008, 08:02:15 PM
 */
package com.scopix.periscope.extractionplanmanagement.commands.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.ArecontEvidenceProvider;
import com.scopix.periscope.extractionmanagement.ArecontImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.AxisGenericEvidenceProvider;
import com.scopix.periscope.extractionmanagement.AxisGenericVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.AxisP3301EvidenceProvider;
import com.scopix.periscope.extractionmanagement.AxisP3301ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BrickcomEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BrickcomImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BroadwareHTTPVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.BroadwareVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco3520F_1_2_1EvidenceProvider;
import com.scopix.periscope.extractionmanagement.Cisco3520F_1_2_1ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco7EvidenceProvider;
import com.scopix.periscope.extractionmanagement.Cisco7ImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.Cisco7VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.CiscoPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141ExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter150EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter150ExtractionRequest;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter212EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter212ExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionmanagement.KumGoEvidenceProvider;
import com.scopix.periscope.extractionmanagement.KumGoImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.NextLevel3EvidenceProvider;
import com.scopix.periscope.extractionmanagement.NextLevel3VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.NextLevelEvidenceProvider;
import com.scopix.periscope.extractionmanagement.NextLevelVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.PeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.PeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPXmlExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.VMSGatewayEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VMSGatewayVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.VadaroEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.VadaroPeopleCountingExtractionRequest;
import com.scopix.periscope.extractionmanagement.VadaroXmlExtractionRequest;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.utilities.ScopixUtilities;

/**
 *
 * @author marko.perich
 */
@SpringBean(rootClass = EvidenceExtractionRequestDAO.class)
public class EvidenceExtractionRequestDAO extends DAOHibernate<EvidenceExtractionRequest, Integer> {

    private static Logger log = Logger.getLogger(EvidenceExtractionRequestDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // public void setDataSource(DataSource dataSource) {
    // setJdbcTemplate(new JdbcTemplate(dataSource));
    // }
    //
    // public JdbcTemplate getJdbcTemplate() {
    // return jdbcTemplate;
    // }
    //
    // public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    // this.jdbcTemplate = jdbcTemplate;
    // }
    /**
     * Close conection, close statement and close result set
     *
     * @param rs
     * @param st
     */
    private void closeConnection(Connection con, ResultSet rs, Statement st) {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                log.error("rs error " + ex.getMessage());
            }
        }
        if (st != null) {
            try {
                st.close();
                st = null;
            } catch (SQLException ex) {
                log.error("st error " + ex.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
                log.error("con error " + ex.getMessage());
            }
        }
    }

    public void saveEvidenceProviders(List<EvidenceProvider> eps) throws ScopixException {
        log.debug("init");
        StringBuilder sqlExecutor = new StringBuilder();

        Statement st = null;
        Statement stId = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();

            Integer id = 0;
            StringBuilder sqlId = new StringBuilder("select nextval('evidence_provider_seq') as SEC");
            stId = conn.createStatement();

            StringBuilder sqlBase = new StringBuilder("INSERT INTO public.evidence_provider ( ");
            sqlBase.append(" dtype, id, name, description, device_id, extraction_server_id, unique_device_id ");

            StringBuilder insertBroadware = new StringBuilder();
            insertBroadware.append(sqlBase.toString()).append(", ip_address, loop_name ) VALUES ( ");

            StringBuilder insertOther1 = new StringBuilder();
            insertOther1.append(sqlBase.toString()).append(", ip_address,  user_name, \"password\" ) VALUES ( ");

            // StringBuilder insertOther2 = new StringBuilder();
            // insertOther2.append(sqlBase.toString()).append(", ip_address, user_name, \"password\", port, protocol ) VALUES ( ");
            StringBuilder insertAxisGeneric = new StringBuilder();
            insertAxisGeneric.append(sqlBase.toString()).append(", ip_address,  user_name, \"password\", port, protocol, ");
            insertAxisGeneric.append(" resolution, framerate ) VALUES ( ");

            StringBuilder insertNextLevel = new StringBuilder();
            insertNextLevel.append(sqlBase.toString()).append(", uuid, ip_address,  user_name, \"password\", port, protocol) ");
            insertNextLevel.append(" VALUES ( ");

            StringBuilder insertBasic = new StringBuilder();
            insertBasic.append(sqlBase.toString()).append(", user_name, \"password\", ip_address, port, protocol)");
            insertBasic.append(" VALUES ( ");

            StringBuilder insertAxisP330 = new StringBuilder();
            insertAxisP330.append(sqlBase.toString()).append(", user_name, \"password\", ip_address, port, protocol,"
                    + " resolution)");
            insertAxisP330.append(" VALUES ( ");

            StringBuilder insertVMSGateway = new StringBuilder();
            insertVMSGateway.append(sqlBase.toString()).append(", ip_address, provider, provider_type, ");
            insertVMSGateway.append(" extraction_plan_to_past, port, protocol) VALUES ( ");

            StringBuilder insertReadUrl = new StringBuilder();
            insertReadUrl.append(sqlBase.toString()).append(", user_name, \"password\", ip_address, port, protocol, query) ");
            insertReadUrl.append(" VALUES ( ");

            StringBuilder insertCisco7 = new StringBuilder();
            insertCisco7.append(sqlBase.toString()).append(", vsom_ip_address, vsom_user, vsom_pass, vsom_domain, ");
            insertCisco7.append(" camera_name, vsom_protocol, vsom_port, media_server_ip, media_server_protocol, ");
            insertCisco7.append(" media_server_port, uuid) VALUES (");

            StringBuilder insertVadaro = new StringBuilder();
            insertVadaro.append(sqlBase.toString()).append(", ip_address, port, protocol, loop_name)");
            insertVadaro.append(" VALUES ( ");

            // StringBuilder insertCiscoPeopleCounting = new StringBuilder();
            // insertCiscoPeopleCounting.append(sqlBase.toString()).append(", port, user_name, password, protocol, ip_address) VALUES (");
            //
            // StringBuilder insertVadaroPeopleCounting = new StringBuilder();
            // insertVadaroPeopleCounting.append(sqlBase.toString()).append(", port, user_name, password, protocol, ip_address) VALUES (");
            int counter = 1;
            for (EvidenceProvider ep : eps) {
                rs = stId.executeQuery(sqlId.toString());
                rs.next();
                id = rs.getInt("SEC");
                ep.setId(id);
                String paramBase = generateParameterBase(ep);
                log.debug("Id = " + ep.getId());
                if (ep instanceof BroadwareEvidenceProvider) {
                    BroadwareEvidenceProvider bep = (BroadwareEvidenceProvider) ep;
                    sqlExecutor.append(insertBroadware.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(bep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(bep.getLoopName()).append("');");
                } else if (ep instanceof PeopleCountingEvidenceProvider) {
                    PeopleCountingEvidenceProvider pcep = (PeopleCountingEvidenceProvider) ep;
                    sqlExecutor.append(insertOther1.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(pcep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(pcep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(pcep.getPassword()).append("');");
                } else if (ep instanceof CognimaticsPeopleCounter141EvidenceProvider) {
                    CognimaticsPeopleCounter141EvidenceProvider cpcep = (CognimaticsPeopleCounter141EvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(cpcep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getProtocol()).append("');");
                } else if (ep instanceof KumGoEvidenceProvider) {
                    KumGoEvidenceProvider kgep = (KumGoEvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(kgep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(kgep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(kgep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(kgep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(kgep.getProtocol()).append("');");
                } else if (ep instanceof AxisP3301EvidenceProvider) {
                    AxisP3301EvidenceProvider apep = (AxisP3301EvidenceProvider) ep;
                    sqlExecutor.append(insertAxisP330.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(apep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(apep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(apep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(apep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(apep.getProtocol()).append("',");
                    if (apep.getResolution() == null) {
                        sqlExecutor.append(" ").append(apep.getResolution()).append(" );");
                    } else {
                        sqlExecutor.append(" '").append(apep.getResolution()).append("' );");
                    }
                } else if (ep instanceof CognimaticsPeopleCounter212EvidenceProvider) {
                    CognimaticsPeopleCounter212EvidenceProvider cpcep = (CognimaticsPeopleCounter212EvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(cpcep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getProtocol()).append("' );");
                } else if (ep instanceof CognimaticsPeopleCounter150EvidenceProvider) {
                    CognimaticsPeopleCounter150EvidenceProvider cpcep = (CognimaticsPeopleCounter150EvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(cpcep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(cpcep.getProtocol()).append("' );");
                } else if (ep instanceof AxisGenericEvidenceProvider) {
                    AxisGenericEvidenceProvider agep = (AxisGenericEvidenceProvider) ep;
                    sqlExecutor.append(insertAxisGeneric.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(agep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(agep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(agep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(agep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(agep.getProtocol()).append("', ");
                    sqlExecutor.append(" '").append(agep.getResolution()).append("', ");
                    sqlExecutor.append(" '").append(agep.getFramerate()).append("');");
                } else if (ep instanceof NextLevelEvidenceProvider) {
                    NextLevelEvidenceProvider nlep = (NextLevelEvidenceProvider) ep;
                    sqlExecutor.append(insertNextLevel.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append("'").append(nlep.getUuid()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getProtocol()).append("');");
                } else if (ep instanceof NextLevel3EvidenceProvider) {
                    NextLevel3EvidenceProvider nlep = (NextLevel3EvidenceProvider) ep;
                    sqlExecutor.append(insertNextLevel.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append("'").append(nlep.getUuid()).append("',");
                    sqlExecutor.append(" '").append(nlep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(nlep.getProtocol()).append("');");
                } else if (ep instanceof BrickcomEvidenceProvider) {
                    BrickcomEvidenceProvider bep = (BrickcomEvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(bep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(bep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(bep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(bep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(bep.getProtocol()).append("');");
                } else if (ep instanceof ArecontEvidenceProvider) {
                    ArecontEvidenceProvider aep = (ArecontEvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(aep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(aep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(aep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(aep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(aep.getProtocol()).append("');");
                } else if (ep instanceof VMSGatewayEvidenceProvider) {
                    VMSGatewayEvidenceProvider vmsEP = (VMSGatewayEvidenceProvider) ep;
                    sqlExecutor.append(insertVMSGateway.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(vmsEP.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(vmsEP.getProvider()).append("', ");
                    sqlExecutor.append(" '").append(vmsEP.getProviderType()).append("', ");
                    sqlExecutor.append(" ").append(vmsEP.getExtractionPlanToPast()).append(", ");
                    sqlExecutor.append(" '").append(vmsEP.getPort()).append("', ");
                    sqlExecutor.append(" '").append(vmsEP.getProtocol()).append("');");
                } else if (ep instanceof BroadwareHTTPEvidenceProvider) {
                    BroadwareHTTPEvidenceProvider bep = (BroadwareHTTPEvidenceProvider) ep;
                    sqlExecutor.append(insertBroadware.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(bep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(bep.getLoopName()).append("');");
                } else if (ep instanceof Cisco3520F_1_2_1EvidenceProvider) {
                    Cisco3520F_1_2_1EvidenceProvider cisco3520Ep = (Cisco3520F_1_2_1EvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(cisco3520Ep.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(cisco3520Ep.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(cisco3520Ep.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(cisco3520Ep.getPort()).append("', ");
                    sqlExecutor.append(" '").append(cisco3520Ep.getProtocol()).append("');");
                } else if (ep instanceof ReadUrlPHPEvidenceProvider) {
                    ReadUrlPHPEvidenceProvider provider = (ReadUrlPHPEvidenceProvider) ep;
                    sqlExecutor.append(insertReadUrl.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(provider.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(provider.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPort()).append("', ");
                    sqlExecutor.append(" '").append(provider.getProtocol()).append("', ");
                    sqlExecutor.append(" '").append(provider.getQuery()).append("');");
                } else if (ep instanceof Cisco7EvidenceProvider) {
                    Cisco7EvidenceProvider provider = (Cisco7EvidenceProvider) ep;
                    sqlExecutor.append(insertCisco7.toString());
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(provider.getVsomIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(provider.getVsomUser()).append("', ");
                    sqlExecutor.append(" '").append(provider.getVsomPass()).append("', ");
                    sqlExecutor.append(" '").append(provider.getVsomDomain()).append("', ");
                    sqlExecutor.append(" '").append(provider.getCameraName()).append("', ");
                    sqlExecutor.append(" '").append(provider.getVsomProtocol()).append("', ");
                    sqlExecutor.append(" '").append(provider.getVsomPort()).append("', ");
                    sqlExecutor.append(" '").append(provider.getMediaServerIp()).append("', ");
                    sqlExecutor.append(" '").append(provider.getMediaServerProtocol()).append("', ");
                    sqlExecutor.append(" '").append(provider.getMediaServerPort()).append("', ");
                    if (provider.getUuid() != null) {
                        sqlExecutor.append(" '").append(provider.getUuid()).append("');");
                    } else {
                        sqlExecutor.append(" ").append(provider.getUuid()).append(");");
                    }
                } else if (ep instanceof CiscoPeopleCountingEvidenceProvider) {
                    CiscoPeopleCountingEvidenceProvider provider = (CiscoPeopleCountingEvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    // user_name, \"password\", ip_address, port, protocol
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(provider.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(provider.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPort()).append("', ");
                    sqlExecutor.append(" '").append(provider.getProtocol()).append("');");
                } else if (ep instanceof VadaroPeopleCountingEvidenceProvider) {
                    VadaroPeopleCountingEvidenceProvider provider = (VadaroPeopleCountingEvidenceProvider) ep;
                    sqlExecutor.append(insertBasic.toString());
                    // user_name, \"password\", ip_address, port, protocol
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(provider.getUserName()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPassword()).append("', ");
                    sqlExecutor.append(" '").append(provider.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPort()).append("', ");
                    sqlExecutor.append(" '").append(provider.getProtocol()).append("');");
                } else if (ep instanceof VadaroEvidenceProvider) {
                    VadaroEvidenceProvider provider = (VadaroEvidenceProvider) ep;
                    sqlExecutor.append(insertVadaro.toString());
                    // ip_address, port, protocol, loop_name
                    sqlExecutor.append(paramBase).append(", ");
                    sqlExecutor.append(" '").append(provider.getIpAddress()).append("', ");
                    sqlExecutor.append(" '").append(provider.getPort()).append("', ");
                    sqlExecutor.append(" '").append(provider.getProtocol()).append("',");
                    sqlExecutor.append(" '").append(provider.getLoopName()).append("');");
                }

                log.debug("Count = " + counter++);
            }
            jdbcTemplate.execute(sqlExecutor.toString());

            // session.createSQLQuery(sqlExecutor.toString()).executeUpdate();
        } catch (SQLException e) {
            log.error("sql " + sqlExecutor.toString(), e);
            throw new ScopixException(e);
        } finally {
            closeConnection(conn, null, null);
            // closeConnection(conn, rs, st);
            // closeConnection(null, null, stId);
            // this.releaseSession(session);

        }
        log.debug("end");
    }

    private String generateParameterBase(EvidenceProvider ep) {
        StringBuilder parser = new StringBuilder();
        parser.append(" '").append(ep.getClass().getSimpleName()).append("',");
        parser.append(" ").append(ep.getId()).append(",");
        parser.append(" '").append(ep.getName()).append("',");
        parser.append(" '").append(ep.getDescription()).append("',");
        parser.append(" ").append(ep.getDeviceId()).append(",");
        parser.append(" ").append(ep.getExtractionServer().getId()).append(",");
        parser.append(" '").append(ep.getUniqueDeviceId()).append("' ");
        return parser.toString();
    }

    /**
     * Este metodo es utilizado cuando se envia un extraction plan.
     *
     * @param eers
     * @throws ScopixException
     */
    public void saveEERS(List<EvidenceExtractionRequest> eers) throws ScopixException {
        log.debug("init");
        StringBuilder sqlExecutor = new StringBuilder();
        Connection conn = null;
        ResultSet rs;
        Statement stId = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            stId = conn.createStatement();
            int id = 0;
            String sqlId = "select nextval('evidence_extraction_request_seq') as SEC";

            int counter = 1;
            for (EvidenceExtractionRequest eer : eers) {
                sqlExecutor.setLength(0);
                rs = stId.executeQuery(sqlId);
                rs.next();
                id = rs.getInt("SEC");
                closeConnection(null, rs, null);
                eer.setId(id);
                log.debug("Id = " + eer.getId());
                sqlExecutor.append(generateSqlInsertEER(eer, id));
                jdbcTemplate.execute(sqlExecutor.toString());
                log.debug("Count = " + counter++);
            }

            // session.createSQLQuery(sqlExecutor.toString()).executeUpdate();
        } catch (Exception e) {
            log.error("Error ejecutando " + sqlExecutor.toString(), e);
            throw new ScopixException(e);
        } finally {
             closeConnection(conn, null, null);
            // closeConnection(null, null, stId);
            // this.releaseSession(session);

        }
        log.debug("end");
    }

    private String generateSqlInsertEER(EvidenceExtractionRequest eer, int id) {
        StringBuilder sqlExecutor = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfCreation = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        sqlExecutor.append("INSERT INTO evidence_extraction_request ");
        sqlExecutor.append("( dtype, id, requested_time, remote_request_id, day_of_week, extraction_plan_id, ");
        sqlExecutor.append("  evidence_provider_id, type, creation_timestamp, length_in_secs, process_id, live, ");
        sqlExecutor.append(" priorization, allows_extraction_plan_to_past, evidence_date) VALUES ( ");
        String clazz = eer.getClass().getSimpleName();
        Date time = eer.getRequestedTime();
        Integer remoteId = eer.getRemoteRequestId();
        Integer dayOfWeek = eer.getDayOfWeek();
        Integer extractionPlanId = eer.getExtractionPlan().getId();
        Integer epId = eer.getEvidenceProvider().getId();
        Integer priorization = eer.getPriorization();
        Integer lengthInSecs = null;
        Integer processId = eer.getProcessId();
        Boolean allowsExtractionPlanToPast = null;
        if (eer instanceof BroadwareVideoExtractionRequest) {
            lengthInSecs = ((BroadwareVideoExtractionRequest) eer).getLengthInSecs();
        } else if (eer instanceof AxisGenericVideoExtractionRequest) {
            lengthInSecs = ((AxisGenericVideoExtractionRequest) eer).getLengthInSecs();
        } else if (eer instanceof NextLevelVideoExtractionRequest) {
            lengthInSecs = ((NextLevelVideoExtractionRequest) eer).getLengthInSecs();
        } else if (eer instanceof NextLevel3VideoExtractionRequest) {
            lengthInSecs = ((NextLevel3VideoExtractionRequest) eer).getLengthInSecs();
        } else if (eer instanceof VMSGatewayVideoExtractionRequest) {
            lengthInSecs = ((VMSGatewayVideoExtractionRequest) eer).getLengthInSecs();
            allowsExtractionPlanToPast = ((VMSGatewayVideoExtractionRequest) eer).getAllowsExtractionPlanToPast();
        } else if (eer instanceof BroadwareHTTPVideoExtractionRequest) {
            lengthInSecs = ((BroadwareHTTPVideoExtractionRequest) eer).getLengthInSecs();
        } else if (eer instanceof Cisco7VideoExtractionRequest) {
            lengthInSecs = ((Cisco7VideoExtractionRequest) eer).getLengthInSecs();
        }
        String type = eer.getType().name();
        Date creation = eer.getCreationTimestamp();
        Date evidenceDate = eer.getEvidenceDate();

        sqlExecutor.append(" '").append(clazz).append("', ");
        sqlExecutor.append(id).append(", ");
        sqlExecutor.append(" to_timestamp('").append(sdf.format(time)).append("', 'HH24:MI'), ");
        sqlExecutor.append(remoteId).append(", ");
        sqlExecutor.append(dayOfWeek).append(", ");
        sqlExecutor.append(extractionPlanId).append(", ");
        sqlExecutor.append(epId).append(", ");
        sqlExecutor.append("'").append(type).append("', ");
        sqlExecutor.append(" to_timestamp('").append(DateFormatUtils.format(creation, "yyyy-MM-dd HH:mm:ss.S"))
                .append("', 'yyyy-MM-dd HH24:MI:ss.MS'), ");
        sqlExecutor.append(lengthInSecs).append(", ");
        sqlExecutor.append(processId).append(",");
        sqlExecutor.append(eer.getLive()).append(",");
        sqlExecutor.append(priorization).append(",");
        sqlExecutor.append(allowsExtractionPlanToPast).append(",");
        if (evidenceDate == null) {
            sqlExecutor.append("null);");
        } else {
            sqlExecutor.append("to_timestamp('").append(sdfCreation.format(evidenceDate)).append("','yyyy-MM-dd HH24:MI') );");
        }
        return sqlExecutor.toString();
    }

    /**
     * Este metodo es utilizado para el envio automatico de evidencia.
     *
     * @param eer
     * @return
     * @throws PeriscopeException
     */
    public Integer saveEERS(EvidenceExtractionRequest eer) throws ScopixException {
        log.debug("init");
        Session session = this.getSession();
        StringBuilder sqlExecutor = new StringBuilder();
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;
        int id = 0;
        try {
            conn = session.connection();
            st = conn.createStatement();
            StringBuilder sqlId = new StringBuilder("select nextval('evidence_extraction_request_seq') as SEC");
            rs = st.executeQuery(sqlId.toString());
            rs.next();
            id = rs.getInt("SEC");
            eer.setId(id);
            log.debug("Id = " + eer.getId());
            sqlExecutor.append(generateSqlInsertEER(eer, id));
            st.executeUpdate(sqlExecutor.toString());
            // session.createSQLQuery(sqlExecutor.toString()).executeUpdate();
        } catch (Exception e) {
            log.error("Error ejecutando " + sqlExecutor.toString(), e);
            throw new ScopixException(e);
        } finally {
            closeConnection(conn, rs, st);
            this.releaseSession(session);

        }
        log.debug("end, id = " + id);
        return id;
    }

    public List<EvidenceExtractionRequest> getEvidenceExtractionRequestBySituation(SituationRequest situationRequest,
            Date fechaSolicitud) throws ScopixException {
        List<EvidenceExtractionRequest> list = new ArrayList<EvidenceExtractionRequest>();
        Date previousDate = DateUtils.addDays(fechaSolicitud, -1);
        Date nextDate = DateUtils.addDays(fechaSolicitud, +1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(previousDate);
        Integer dayOfWeekPrevious = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(nextDate);
        Integer dayOfWeekNext = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(fechaSolicitud);
        Integer dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        StringBuilder sql = new StringBuilder();
        //agregar nombre del provider para poder hacer una discriminacion por provider tambien
        sql.append("select eer.id, eer.creation_timestamp, eer.dtype, provider.name, eer.evidence_date ");
        sql.append("from     ");
        sql.append("  evidence_extraction_request eer, ");
        sql.append("  extraction_plan ep, ");
        sql.append("  metric_request mr, ");
        sql.append("  evidence_provider_request epr, ");
        sql.append("  evidence_provider provider ");
        sql.append("where ");
        sql.append(" eer.extraction_plan_id  = ep.id ");
        sql.append(" and provider.id = eer.evidence_provider_id ");
        sql.append(" and (eer.day_of_week = ? or eer.day_of_week = ? or eer.day_of_week =? ) ");
        sql.append(" and ep.expiration_date is null ");
        sql.append(" and mr.situation_request_id  = ? ");
        sql.append(" and epr.metric_request_id = mr.id ");
        sql.append(" and eer.evidence_provider_id = epr.evidence_provider_id ");
        sql.append(" and eer.creation_timestamp >=? ");
        sql.append(" and eer.creation_timestamp <? ");
        sql.append(" order by eer.creation_timestamp");
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString(), new Object[]{dayOfWeekPrevious,
            dayOfWeekNext, dayOfWeek, situationRequest.getId(), previousDate, nextDate});
        for (Map<String, Object> row : result) {
            String dtype = (String) row.get("dtype");
            EvidenceExtractionRequest eer = getEvidenceExtractionRequestByDtype(dtype); // new
            // EvidenceExtractionRequest()
            eer.setId((Integer) row.get("id"));
            eer.setCreationTimestamp((Date) row.get("creation_timestamp"));
            eer.setEvidenceDate((Date) row.get("requested_time"));

            EvidenceProvider ep = new EvidenceProvider();
            ep.setName((String) row.get("name"));
            eer.setEvidenceProvider(ep);

            list.add(eer);
        }
        return list;
    }

    private EvidenceExtractionRequest getEvidenceExtractionRequestByDtype(String dtype) throws ScopixException {
        EvidenceExtractionRequest eer = null;
        if (dtype.equals(BrickcomImageExtractionRequest.class.getSimpleName())) {
            eer = new BrickcomImageExtractionRequest();
        } else if (dtype.equals(AxisP3301ImageExtractionRequest.class.getSimpleName())) {
            eer = new AxisP3301ImageExtractionRequest();
        } else if (dtype.equals(BroadwareImageExtractionRequest.class.getSimpleName())) {
            eer = new BroadwareImageExtractionRequest();
        } else if (dtype.equals(CognimaticsPeopleCounter141ExtractionRequest.class.getSimpleName())) {
            eer = new CognimaticsPeopleCounter141ExtractionRequest();
        } else if (dtype.equals(NextLevelVideoExtractionRequest.class.getSimpleName())) {
            eer = new NextLevelVideoExtractionRequest();
        } else if (dtype.equals(NextLevel3VideoExtractionRequest.class.getSimpleName())) {
            eer = new NextLevel3VideoExtractionRequest();
        } else if (dtype.equals(PeopleCountingExtractionRequest.class.getSimpleName())) {
            eer = new PeopleCountingExtractionRequest();
        } else if (dtype.equals(VMSGatewayVideoExtractionRequest.class.getSimpleName())) {
            eer = new VMSGatewayVideoExtractionRequest();
        } else if (dtype.equals(ArecontImageExtractionRequest.class.getSimpleName())) {
            eer = new ArecontImageExtractionRequest();
        } else if (dtype.equals(CognimaticsPeopleCounter212ExtractionRequest.class.getSimpleName())) {
            eer = new CognimaticsPeopleCounter212ExtractionRequest();
        } else if (dtype.equals(AxisGenericVideoExtractionRequest.class.getSimpleName())) {
            eer = new AxisGenericVideoExtractionRequest();
        } else if (dtype.equals(KumGoImageExtractionRequest.class.getSimpleName())) {
            eer = new KumGoImageExtractionRequest();
        } else if (dtype.equals(BroadwareVideoExtractionRequest.class.getSimpleName())) {
            eer = new BroadwareVideoExtractionRequest();
        } else if (dtype.equals(CognimaticsPeopleCounter150ExtractionRequest.class.getSimpleName())) {
            eer = new CognimaticsPeopleCounter150ExtractionRequest();
        } else if (dtype.equals(Cisco3520F_1_2_1ImageExtractionRequest.class.getSimpleName())) {
            eer = new Cisco3520F_1_2_1ImageExtractionRequest();
        } else if (dtype.equals(ReadUrlPHPImageExtractionRequest.class.getSimpleName())) {
            eer = new ReadUrlPHPImageExtractionRequest();
        } else if (dtype.equals(ReadUrlPHPXmlExtractionRequest.class.getSimpleName())) {
            eer = new ReadUrlPHPXmlExtractionRequest();
        } else if (dtype.equals(Cisco7VideoExtractionRequest.class.getSimpleName())) {
            eer = new Cisco7VideoExtractionRequest();
        } else if (dtype.equals(Cisco7ImageExtractionRequest.class.getSimpleName())) {
            eer = new Cisco7ImageExtractionRequest();
        } else if (dtype.equals(CiscoPeopleCountingExtractionRequest.class.getSimpleName())) {
            eer = new CiscoPeopleCountingExtractionRequest();
        } else if (dtype.equals(VadaroPeopleCountingExtractionRequest.class.getSimpleName())) {
            eer = new VadaroPeopleCountingExtractionRequest();
        } else if (dtype.equals(BroadwareHTTPVideoExtractionRequest.class.getSimpleName())) {
            eer = new BroadwareHTTPVideoExtractionRequest();
        } else if (dtype.equals(VadaroXmlExtractionRequest.class.getSimpleName())) {
            eer = new VadaroXmlExtractionRequest();
        } else {
            throw new ScopixException("dtype " + dtype + " no definido");
        }
        return eer;
    }

    public EvidenceExtractionRequest getEvidenceExtractionRequestById(Integer evidenceExtractionRequestId) throws ScopixException {
        StringBuilder sql = new StringBuilder("select eer.dtype as eer_dtype, eer.id as eer_id, eer.creation_timestamp,");
        sql.append(" eer.day_of_week, eer.evidence_date, eer.priorization, eer.process_id, eer.remote_request_id,");
        sql.append(" eer.requested_time, eer.type, eer.length_in_secs, eer.allows_extraction_plan_to_past,");
        sql.append(" eer.evidence_provider_id, eer.extraction_plan_id, eer.live, provider.dtype as provider_dtype,");
        sql.append(" provider.id as provider_id, provider.description, provider.device_id, provider.name,");
        sql.append(" provider.unique_device_id, provider.ip_address, provider.password, provider.user_name,");
        sql.append(" provider.port, provider.protocol, provider.query, provider.uuid, provider.gallery_dir,");
        sql.append(" provider.extraction_plan_to_past, provider.provider, provider.provider_type,");
        sql.append(" provider.loop_name, provider.camera_name, provider.media_server_ip, provider.media_server_port,");
        sql.append(" provider.media_server_protocol, provider.vsom_domain, provider.vsom_ip_address,");
        sql.append(" provider.vsom_pass, provider.vsom_port, provider.vsom_protocol, provider.vsom_user, provider.uuid,");
        sql.append(" provider.framerate, provider.resolution, provider.extraction_server_id, ep.id as ep_id,");
        sql.append(" ep.expiration_date, ep.store_id, ep.store_name, ep.time_zone_id, ep.extraction_server_id ");
        sql.append(" from evidence_extraction_request eer, evidence_provider provider, extraction_plan ep ");
        sql.append(" where eer.id = ? ");
        sql.append(" and provider.id = eer.evidence_provider_id");
        sql.append(" and ep.id = eer.extraction_plan_id");

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString(), new Object[]{evidenceExtractionRequestId});

        EvidenceExtractionRequest eer = null;

        for (Map<String, Object> row : result) {
            eer = getEvidenceExtractionRequestByDtype((String) row.get("eer_dtype"));

            ScopixUtilities.setterInObject(eer, row.get("eer_id"), "id");
            ScopixUtilities.setterParam(eer, row, "creation_timestamp");
            ScopixUtilities.setterParam(eer, row, "day_of_week");
            ScopixUtilities.setterParam(eer, row, "evidence_date");
            ScopixUtilities.setterParam(eer, row, "priorization");
            ScopixUtilities.setterParam(eer, row, "process_id");
            ScopixUtilities.setterParam(eer, row, "remote_request_id");
            ScopixUtilities.setterParam(eer, row, "requested_time");
            eer.setType(EvidenceRequestType.valueOf((String) row.get("type")));
            // ScopixUtilities.setterParam(eer, row, "type");
            ScopixUtilities.setterParam(eer, row, "length_in_secs");
            ScopixUtilities.setterParam(eer, row, "allows_extraction_plan_to_past");
            ScopixUtilities.setterParam(eer, row, "evidence_provider_id");
            ScopixUtilities.setterParam(eer, row, "live");

            ExtractionPlan ep = new ExtractionPlan();
            ep.setId((Integer) row.get("ep_id"));
            ep.setExpirationDate((Date) row.get("expiration_date"));
            ep.setStoreId((Integer) row.get("store_id"));
            ep.setStoreName((String) row.get("store_name"));
            ep.setTimeZoneId((String) row.get("time_zone_id"));

            eer.setExtractionPlan(ep);

            EvidenceProvider provider = ScopixUtilities.findEvidenceProviderByClassName((String) row.get("provider_dtype"));
            provider.setId((Integer) row.get("provider_id"));
            ScopixUtilities.setterParam(provider, row, "description");
            ScopixUtilities.setterParam(provider, row, "device_id");
            ScopixUtilities.setterParam(provider, row, "name");
            ScopixUtilities.setterParam(provider, row, "unique_device_id");
            ScopixUtilities.setterParam(provider, row, "ip_address");
            ScopixUtilities.setterParam(provider, row, "password");
            ScopixUtilities.setterParam(provider, row, "user_name");
            ScopixUtilities.setterParam(provider, row, "port");
            ScopixUtilities.setterParam(provider, row, "protocol");
            ScopixUtilities.setterParam(provider, row, "query");
            ScopixUtilities.setterParam(provider, row, "uuid");
            ScopixUtilities.setterParam(provider, row, "gallery_dir");
            ScopixUtilities.setterParam(provider, row, "extraction_plan_to_past");
            ScopixUtilities.setterParam(provider, row, "provider");

            ScopixUtilities.setterParam(provider, row, "provider_type");
            ScopixUtilities.setterParam(provider, row, "loop_name");
            ScopixUtilities.setterParam(provider, row, "camera_name");
            ScopixUtilities.setterParam(provider, row, "media_server_ip");
            ScopixUtilities.setterParam(provider, row, "media_server_port");
            ScopixUtilities.setterParam(provider, row, "media_server_protocol");
            ScopixUtilities.setterParam(provider, row, "vsom_domain");
            ScopixUtilities.setterParam(provider, row, "vsom_ip_address");
            ScopixUtilities.setterParam(provider, row, "vsom_pass");
            ScopixUtilities.setterParam(provider, row, "vsom_port");
            ScopixUtilities.setterParam(provider, row, "vsom_protocol");
            ScopixUtilities.setterParam(provider, row, "vsom_user");
            ScopixUtilities.setterParam(provider, row, "uuid");
            ScopixUtilities.setterParam(provider, row, "framerate");
            ScopixUtilities.setterParam(provider, row, "resolution");
            ScopixUtilities.setterParam(provider, row, "extraction_server_id");

            eer.setEvidenceProvider(provider);
        }
        return eer;
    }
}
