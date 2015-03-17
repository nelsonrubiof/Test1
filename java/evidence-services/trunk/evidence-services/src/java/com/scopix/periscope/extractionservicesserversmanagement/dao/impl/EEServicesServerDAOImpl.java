/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  StoreDAOImpl.java
 * 
 *  Created on Dec 22, 2013, 14:56:48 PM
 * 
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EEServicesServerDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * 
 * @author Emo
 */
@Repository
@SpringBean(rootClass = EEServicesServerDAO.class)
public class EEServicesServerDAOImpl implements EEServicesServerDAO {

	// private static final String GET = "select * from dsr.store where id=?";
	// private static final String EXIST =
	// "select count(*) from dsr.store where id=?";
	// private static final String GET_ALL = "select * from dsr.store";
	// private static final String DELETE = "delete from dsr.store where id=?";
	private static final String SAVE = "INSERT INTO evidence_extraction_services_server(id_at_business_services, name, server_id, ssh_address, "
			+ "ssh_local_tunnel_port, ssh_password, ssh_port, ssh_remote_tunnel_port, ssh_user, url) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?);";
	// private static final String UPDATE =
	// "update dsr.store set name=?, description=?, timezone=? where id=?";
	// private static final String GET_TIMEZONE_BY_JID = "select timezone " +
	// "from dsr.store s, dsr.user_profile up "
	// + "where s.id = up.store_id " + "and up.jid = ?";
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean exists(Integer id) {
		// int total = jdbcTemplate.queryForInt(EXIST, new Object[] { id });
		return true; // total > 0;
	}

	@Override
	public EvidenceExtractionServicesServer get(Integer id) {
		// Store store = jdbcTemplate.queryForObject(GET, new Object[] { storeId
		// }, new StoreRowMapper());
		return null;
	}

	@Override
	public List<EvidenceExtractionServicesServer> getAll() {
		return null;
		// jdbcTemplate.query(GET_ALL, new StoreRowMapper());
	}

	@Override
	public void remove(EvidenceExtractionServicesServer id) {
		// jdbcTemplate.update(DELETE, new Object[] { store.getId() });
	}

	@Override
	public void remove(Integer id) {
		// jdbcTemplate.update(DELETE, new Object[] { storeId });
	}

	@Override
	public Integer save(EvidenceExtractionServicesServer eess) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		final Integer idAtBS = eess.getIdAtBusinessServices();
		final String name = eess.getName();
		final Integer serverId = eess.getServerId();
		final String sshAddress = eess.getSshAddress();
		final String sshLocalTunnelPort = eess.getSshLocalTunnelPort();
		final String sshPassword = eess.getSshPassword();
		final String sshPort = eess.getSshPort();
		final String sshRemoteTunnelPort = eess.getSshRemoteTunnelPort();
		final String sshUser = eess.getSshUser();
		final String url = eess.getUrl();

		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
				PreparedStatement ps = cnctn.prepareStatement(SAVE, new String[] { "id" });
				ps.setObject(1, idAtBS);
				ps.setString(2, name);
				ps.setObject(3, serverId);
				ps.setString(4, sshAddress);
				ps.setString(5, sshLocalTunnelPort);
				ps.setString(6, sshPassword);
				ps.setString(7, sshPort);
				ps.setString(8, sshRemoteTunnelPort);
				ps.setString(9, sshUser);
				ps.setString(10, url);
				return ps;
			}
		}, keyHolder);

		int id = keyHolder.getKey().intValue();
		eess.setId(id);

		return id;
	}

	@Override
	public List<Integer> save(List<EvidenceExtractionServicesServer> list) {

		List<Integer> ids = new ArrayList<Integer>();
		for (EvidenceExtractionServicesServer item : list) {
			ids.add(save(item));
		}
		return ids;
	}

	@Override
	public void update(EvidenceExtractionServicesServer eess) {
		// this.jdbcTemplate.update(UPDATE,
		// new Object[] { store.getName(), store.getDescription(),
		// store.getTimeZone(), store.getId() });
	}

	@Override
	public void update(List<EvidenceExtractionServicesServer> list) {
		// for (EvidenceExtractionServicesServer store : list) {
		// update(store);
		// }
	}

	// private class StoreRowMapper implements
	// RowMapper<EvidenceExtractionServicesServer> {
	//
	// @Override
	// public EvidenceExtractionServicesServer mapRow(ResultSet rs, int i)
	// throws SQLException {
	// EvidenceExtractionServicesServer store = new
	// EvidenceExtractionServicesServer();
	// store.setId(rs.getInt("ID"));
	// store.setName(rs.getString("NAME"));
	// store.setDescription(rs.getString("DESCRIPTION"));
	// store.setTimeZone(rs.getString("TIMEZONE"));
	// return store;
	// }
	// }
}
