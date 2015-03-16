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

import com.scopix.periscope.extractionservicesserversmanagement.SituationSensor;
import com.scopix.periscope.extractionservicesserversmanagement.dao.SituationSensorDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * 
 * @author Emo
 */
@Repository
@SpringBean(rootClass = SituationSensorDAO.class)
public class SituationSensorDAOImpl implements SituationSensorDAO {

	// private static final String GET = "select * from dsr.store where id=?";
	// private static final String EXIST =
	// "select count(*) from dsr.store where id=?";
	// private static final String GET_ALL = "select * from dsr.store";
	// private static final String DELETE = "delete from dsr.store where id=?";
	private static final String SAVE = "INSERT INTO situation_sensor(description, name, sensor_id, situation_request_id) VALUES (?, ?, ?, ?);";
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
	public SituationSensor get(Integer id) {
		// Store store = jdbcTemplate.queryForObject(GET, new Object[] { storeId
		// }, new StoreRowMapper());
		return null;
	}

	@Override
	public List<SituationSensor> getAll() {
		return null;
		// jdbcTemplate.query(GET_ALL, new StoreRowMapper());
	}

	@Override
	public void remove(SituationSensor ss) {
		// jdbcTemplate.update(DELETE, new Object[] { store.getId() });
	}

	@Override
	public void remove(Integer id) {
		// jdbcTemplate.update(DELETE, new Object[] { storeId });
	}

	@Override
	public Integer save(SituationSensor ss) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String description = ss.getDescription();
		final String name = ss.getName();
		final Integer sensorId = ss.getSensorId();
		final Integer situationRequestId = ss.getSituationRequest().getId();

		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
				PreparedStatement ps = cnctn.prepareStatement(SAVE, new String[] { "id" });
				ps.setString(1, description);
				ps.setString(2, name);
				ps.setObject(3, sensorId);
				ps.setObject(4, situationRequestId);
				return ps;
			}
		}, keyHolder);

		int id = keyHolder.getKey().intValue();
		ss.setId(id);

		return id;

	}

	@Override
	public List<Integer> save(List<SituationSensor> list) {

		List<Integer> ids = new ArrayList<Integer>();
		for (SituationSensor item : list) {
			ids.add(save(item));
		}
		return ids;
	}

	@Override
	public void update(SituationSensor ss) {
		// this.jdbcTemplate.update(UPDATE,
		// new Object[] { store.getName(), store.getDescription(),
		// store.getTimeZone(), store.getId() });
	}

	@Override
	public void update(List<SituationSensor> list) {
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
