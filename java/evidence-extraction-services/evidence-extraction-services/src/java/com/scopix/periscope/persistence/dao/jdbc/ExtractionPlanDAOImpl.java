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
package com.scopix.periscope.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scopix.periscope.extractionmanagement.ExtractionPlan;
import com.scopix.periscope.extractionplanmanagement.EServerDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.persistence.dao.ExtractionPlanDAO;

/**
 * 
 * @author Emo
 */
@Repository
@SpringBean(rootClass = ExtractionPlanDAO.class)
public class ExtractionPlanDAOImpl implements ExtractionPlanDAO {

	// private static final String GET = "select * from dsr.store where id=?";
	private static final String GET_ENABLED_BY_NAME = "select * from extraction_plan where store_name = '?' and expiration_date is null order by id;";
	// private static final String EXIST =
	// "select count(*) from dsr.store where id=?";
	// private static final String GET_ALL = "select * from dsr.store";
	// private static final String DELETE = "delete from dsr.store where id=?";
	private static final String SAVE = "INSERT INTO extraction_plan(expiration_date, store_id, store_name, time_zone_id, extraction_server_id) VALUES (?, ?, ?, ?, ?);";
	// private static final String UPDATE =
	// "update dsr.store set name=?, description=?, timezone=? where id=?";
	private static final String UPDATE_EXPIRATION_DATE = "update extraction_plan set expiration_date = now() where store_name = ? and expiration_date is null";
	// private static final String GET_TIMEZONE_BY_JID = "select timezone " +
	// "from dsr.store s, dsr.user_profile up "
	// + "where s.id = up.store_id " + "and up.jid = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private EServerDAO extractionServerDAO;

	@Override
	public boolean exists(Integer id) {
		// int total = jdbcTemplate.queryForInt(EXIST, new Object[] { id });
		return true; // total > 0;
	}

	@Override
	public ExtractionPlan get(Integer id) {
		// Store store = jdbcTemplate.queryForObject(GET, new Object[] { storeId
		// }, new StoreRowMapper());
		return null;
	}

	@Override
	public ExtractionPlan getEnabledByName(String name) {
		ExtractionPlan extractionPlan = jdbcTemplate.queryForObject(GET_ENABLED_BY_NAME, new Object[] { name },
				new ExtractionPlanRowMapper());

		return extractionPlan;
	}

	@Override
	public void expire(ExtractionPlan extractionPlan) {
		this.jdbcTemplate.update(UPDATE_EXPIRATION_DATE, new Object[] { extractionPlan.getStoreName() });
	}

	@Override
	public List<ExtractionPlan> getAll() {
		return null;
		// jdbcTemplate.query(GET_ALL, new StoreRowMapper());
	}

	@Override
	public void remove(ExtractionPlan value) {
		// jdbcTemplate.update(DELETE, new Object[] { store.getId() });
	}

	@Override
	public void remove(Integer id) {
		// jdbcTemplate.update(DELETE, new Object[] { storeId });
	}

	@Override
	public Integer save(ExtractionPlan value) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		final Timestamp expirationDate = (value.getExpirationDate() == null ? null : new Timestamp(value.getExpirationDate()
				.getTime()));
		final Integer storeId = value.getStoreId();
		final String storeName = value.getStoreName();
		final String timeZoneId = value.getTimeZoneId();
		final Integer extractionServerId = value.getExtractionServer().getId();

		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
				PreparedStatement ps = cnctn.prepareStatement(SAVE, new String[] { "id" });
				ps.setTimestamp(1, expirationDate);
				ps.setObject(2, storeId);
				ps.setString(3, storeName);
				ps.setString(4, timeZoneId);
				ps.setObject(5, extractionServerId);
				return ps;
			}
		}, keyHolder);

		int id = keyHolder.getKey().intValue();
		value.setId(id);

		return id;
	}

	@Override
	public List<Integer> save(List<ExtractionPlan> list) {

		List<Integer> ids = new ArrayList<Integer>();
		for (ExtractionPlan item : list) {
			ids.add(save(item));
		}
		return ids;
	}

	@Override
	public void update(ExtractionPlan value) {
		// this.jdbcTemplate.update(UPDATE,
		// new Object[] { store.getName(), store.getDescription(),
		// store.getTimeZone(), store.getId() });
	}

	@Override
	public void update(List<ExtractionPlan> list) {
		// for (EvidenceExtractionServicesServer store : list) {
		// update(store);
		// }
	}

	private class ExtractionPlanRowMapper implements RowMapper<ExtractionPlan> {

		@Override
		public ExtractionPlan mapRow(ResultSet rs, int i) throws SQLException {

			ExtractionPlan extractionPlan = new ExtractionPlan();
			extractionPlan.setId(rs.getInt("ID"));
			extractionPlan.setStoreName(rs.getString("STORE_NAME"));
			extractionPlan.setExpirationDate(rs.getDate("EXPIRATION_DATE"));
			extractionPlan.setStoreId(rs.getInt("STORE_ID"));
			extractionPlan.setTimeZoneId(rs.getString("TIME_ZONE_ID"));
			extractionPlan.setExtractionServer(getExtractionServerDAO().get(rs.getInt("EXTRACTION_SERVER_ID")));

			return extractionPlan;
		}
	}

	// External DAOs
	public EServerDAO getExtractionServerDAO() {
		if (extractionServerDAO == null) {
			extractionServerDAO = SpringSupport.getInstance().findBeanByClassName(EServerDAO.class);
		}
		return extractionServerDAO;
	}

}
