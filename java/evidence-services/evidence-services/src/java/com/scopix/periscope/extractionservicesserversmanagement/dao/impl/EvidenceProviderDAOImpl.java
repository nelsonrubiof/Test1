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
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scopix.periscope.extractionservicesserversmanagement.EvidenceProvider;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceProviderDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * 
 * @author Emo
 */
@Repository
@SpringBean(rootClass = EvidenceProviderDAO.class)
public class EvidenceProviderDAOImpl implements EvidenceProviderDAO {

	// private static final String GET = "select * from dsr.store where id=?";
	// private static final String EXIST =
	// "select count(*) from dsr.store where id=?";
	// private static final String GET_ALL = "select * from dsr.store";
	// private static final String DELETE = "delete from dsr.store where id=?";
	private static final String SAVE = "INSERT INTO evidence_provider(definition_data, description, device_id, provider_type, evidence_extraction_services_server_id) "
			+ "VALUES (?, ?, ?, ?, ?); ";
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
	public EvidenceProvider get(Integer id) {
		// Store store = jdbcTemplate.queryForObject(GET, new Object[] { storeId
		// }, new StoreRowMapper());
		return null;
	}

	@Override
	public List<EvidenceProvider> getAll() {
		return null;
		// jdbcTemplate.query(GET_ALL, new StoreRowMapper());
	}

	@Override
	public void remove(EvidenceProvider ep) {
		// jdbcTemplate.update(DELETE, new Object[] { store.getId() });
	}

	@Override
	public void remove(Integer id) {
		// jdbcTemplate.update(DELETE, new Object[] { storeId });
	}

	@Override
	public Integer save(EvidenceProvider ep) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		final HashMap<String, String> definitionData = ep.getDefinitionData();
		final String description = ep.getDescription();
		final Integer deviceId = ep.getDeviceId();
		final String providerType = ep.getProviderType();
		final Integer evidenceExtractionServicesServerId = ep.getEvidenceExtractionServicesServer().getId();

		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
				PreparedStatement ps = cnctn.prepareStatement(SAVE, new String[] { "id" });
				// ps.setBinary(1, definitionData);
				ps.setString(2, description);
				ps.setObject(3, deviceId);
				ps.setString(4, providerType);
				ps.setObject(5, evidenceExtractionServicesServerId);
				return ps;
			}
		}, keyHolder);

		int id = keyHolder.getKey().intValue();
		ep.setId(id);

		return id;
	}

	@Override
	public List<Integer> save(List<EvidenceProvider> list) {

		List<Integer> ids = new ArrayList<Integer>();
		for (EvidenceProvider item : list) {
			ids.add(save(item));
		}
		return ids;
	}

	@Override
	public void update(EvidenceProvider ep) {
		// this.jdbcTemplate.update(UPDATE,
		// new Object[] { store.getName(), store.getDescription(),
		// store.getTimeZone(), store.getId() });
	}

	@Override
	public void update(List<EvidenceProvider> list) {
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
