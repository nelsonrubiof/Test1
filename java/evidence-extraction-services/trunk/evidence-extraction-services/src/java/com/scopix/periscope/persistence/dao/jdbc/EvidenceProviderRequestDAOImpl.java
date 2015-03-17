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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scopix.periscope.extractionmanagement.EvidenceProviderRequest;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.persistence.dao.EvidenceProviderRequestDAO;

/**
 * 
 * @author Emo
 */
@Repository
@SpringBean(rootClass = EvidenceProviderRequestDAO.class)
public class EvidenceProviderRequestDAOImpl implements EvidenceProviderRequestDAO {

	// private static final String GET = "select * from dsr.store where id=?";
	// private static final String EXIST =
	// "select count(*) from dsr.store where id=?";
	// private static final String GET_ALL = "select * from dsr.store";
	// private static final String DELETE = "delete from dsr.store where id=?";
	private static final String SAVE = "INSERT INTO evidence_provider_request(device_id, unique_device_id, evidence_provider_id, metric_request_id, situation_request_id) "
			+ "VALUES (?, ?, ?, ?, ?);";
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
	public EvidenceProviderRequest get(Integer id) {
		// Store store = jdbcTemplate.queryForObject(GET, new Object[] { storeId
		// }, new StoreRowMapper());
		return null;
	}

	@Override
	public List<EvidenceProviderRequest> getAll() {
		return null;
		// jdbcTemplate.query(GET_ALL, new StoreRowMapper());
	}

	@Override
	public void remove(EvidenceProviderRequest ep) {
		// jdbcTemplate.update(DELETE, new Object[] { store.getId() });
	}

	@Override
	public void remove(Integer id) {
		// jdbcTemplate.update(DELETE, new Object[] { storeId });
	}

	@Override
	public Integer save(EvidenceProviderRequest epr) {
		// this.jdbcTemplate.update(
		// SAVE,
		// new Object[] { eess.getIdAtBusinessServices(), eess.getName(),
		// eess.getServerId(), eess.getSshAddress(),
		// eess.getSshLocalTunnelPort(), eess.getSshPassword(),
		// eess.getSshPort(), eess.getSshRemoteTunnelPort(),
		// eess.getSshUser(), eess.getUrl() });

		// definition_data, description, device_id, provider_type,
		// evidence_extraction_services_server_id

		KeyHolder keyHolder = new GeneratedKeyHolder();
		final Integer deviceId = epr.getDeviceId();
		final String uniqueDeviceId = epr.getUniqueDeviceId();
		final Integer evidenceProviderId = epr.getEvidenceProvider().getId();
		final Integer metricRequestId = epr.getMetricRequest().getId();
		final Integer situationRequestId = epr.getSituationRequest().getId();

		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection cnctn) throws SQLException {
				PreparedStatement ps = cnctn.prepareStatement(SAVE, new String[] { "id" });
				ps.setObject(1, deviceId);
				ps.setString(2, uniqueDeviceId);
				ps.setObject(3, evidenceProviderId);
				ps.setObject(4, metricRequestId);
				ps.setObject(5, situationRequestId);
				return ps;
			}
		}, keyHolder);

		int id = keyHolder.getKey().intValue();
		epr.setId(id);

		return id;
	}

	@Override
	public List<Integer> save(List<EvidenceProviderRequest> list) {

		List<Integer> ids = new ArrayList<Integer>();
		for (EvidenceProviderRequest item : list) {
			ids.add(save(item));
		}
		return ids;
	}

	@Override
	public void update(EvidenceProviderRequest ep) {
		// this.jdbcTemplate.update(UPDATE,
		// new Object[] { store.getName(), store.getDescription(),
		// store.getTimeZone(), store.getId() });
	}

	@Override
	public void update(List<EvidenceProviderRequest> list) {
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
