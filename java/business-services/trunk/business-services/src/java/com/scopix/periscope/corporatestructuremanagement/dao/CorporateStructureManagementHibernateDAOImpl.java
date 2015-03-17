/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * CorporateStructureManagementHibernateDAOImpl.java
 * 
 * Created on 16-06-2008, 01:53:46 PM
 */
package com.scopix.periscope.corporatestructuremanagement.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessResourceFailureException;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Country;
import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderType;
import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Location;
import com.scopix.periscope.corporatestructuremanagement.Place;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.corporatestructuremanagement.RelationEvidenceProviderLocation;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SensorAndEvidenceExtractionServicesServerDTO;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.RequestedImage;
import com.scopix.periscope.extractionplanmanagement.RequestedVideo;
import com.scopix.periscope.extractionplanmanagement.RequestedXml;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.templatemanagement.EvidenceType;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.vadaro.VadaroEvent;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = CorporateStructureManagementHibernateDAOImpl.class)
public class CorporateStructureManagementHibernateDAOImpl extends DAOHibernate<Place, Integer> implements
    CorporateStructureManagementHibernateDAO {

    private Logger log = Logger.getLogger(CorporateStructureManagementHibernateDAOImpl.class);

    /**
     * Obtain a list of AreaType object using filters.
     *
     * @param areaType Filter object
     * @return List<AreaType> List of AreaType objects
     */
    @Override
    public List<AreaType> getAreaTypeList(AreaType areaType) {
        List<AreaType> areaTypes = null;
        Criteria criteria = this.getSession().createCriteria(AreaType.class);
        criteria.addOrder(Order.asc("id"));
        if (areaType != null) {
            if (areaType.getName() != null && areaType.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", areaType.getName(), MatchMode.ANYWHERE));
            }
            if (areaType.getDescription() != null && areaType.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", areaType.getDescription(), MatchMode.ANYWHERE));
            }
        }
        areaTypes = criteria.list();
        return areaTypes;
    }

    /**
     * Obtain a list of Area object using filters.
     *
     * @param area Filter object
     * @return List<Area> List of Area objects
     */
    @Override
    public List<Area> getAreaList(Area area) {
        List<Area> areas = null;
        Criteria criteria = this.getSession().createCriteria(Area.class);
        criteria.addOrder(Order.asc("id"));
        if (area != null) {
            if (area.getName() != null && area.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", area.getName(), MatchMode.ANYWHERE));
            }
            if (area.getDescription() != null && area.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", area.getDescription(), MatchMode.ANYWHERE));
            }
            if (area.getAreaType() != null && area.getAreaType().getId() != null && area.getAreaType().getId() > 0) {
                criteria.add(Restrictions.eq("areaType.id", area.getAreaType().getId()));
            }
            if (area.getStore() != null && area.getStore().getId() != null && area.getStore().getId() > 0) {
                criteria.add(Restrictions.eq("store.id", area.getStore().getId()));
            }
        }
        areas = criteria.list();
        return areas;
    }

    /**
     * Obtain a list of Store object using filters.
     *
     * @param store Filter object
     * @return List<Store> List of Store objects
     */
    @Override
    public List<Store> getStoreList(Store store) {
        List<Store> stores = null;
        Criteria criteria = this.getSession().createCriteria(Store.class);
        criteria.addOrder(Order.asc("description"));
        if (store != null) {
            if (store.getName() != null && store.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", store.getName(), MatchMode.ANYWHERE));
            }
            if (store.getDescription() != null && store.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", store.getDescription(), MatchMode.ANYWHERE));
            }
            if (store.getCorporate() != null && store.getCorporate().getId() != null && store.getCorporate().getId() > 0) {
                criteria.add(Restrictions.eq("corporate.id", store.getCorporate().getId()));
            }
            if (store.getAddress() != null && store.getAddress().length() > 0) {
                criteria.add(Restrictions.ilike("address", store.getAddress(), MatchMode.ANYWHERE));
            }
            if (store.getEvidenceExtractionServicesServer() != null
                && store.getEvidenceExtractionServicesServer().getId() != null
                && store.getEvidenceExtractionServicesServer().getId() > 0) {
                criteria.add(Restrictions.eq("evidenceExtractionServicesServer.id", store.getEvidenceExtractionServicesServer()
                    .getId()));
            }
            if (store.getEvidenceServicesServer() != null && store.getEvidenceServicesServer().getId() != null
                && store.getEvidenceServicesServer().getId() > 0) {
                criteria.add(Restrictions.eq("evidenceServicesServer.id", store.getEvidenceServicesServer().getId()));
            }
        }
        stores = criteria.list();
        return stores;
    }

    /**
     * Obtain a list of EvidenceServicesServer object using filters.
     *
     * @param ess Filter object
     * @return List<EvidenceServicesServer> List of EvidenceServicesServer objects
     */
    @Override
    public List<EvidenceServicesServer> getEvidenceServicesServersList(EvidenceServicesServer ess) {
        List<EvidenceServicesServer> list = null;
        Criteria criteria = this.getSession().createCriteria(EvidenceServicesServer.class);
        criteria.addOrder(Order.asc("id"));
        if (ess != null) {
            if (ess.getUrl() != null && ess.getUrl().length() > 0) {
                criteria.add(Restrictions.ilike("url", ess.getUrl(), MatchMode.ANYWHERE));
            }
            if (ess.getEvidencePath() != null && ess.getEvidencePath().length() > 0) {
                criteria.add(Restrictions.ilike("evidencePath", ess.getEvidencePath(), MatchMode.ANYWHERE));
            }
        }
        list = criteria.list();
        return list;
    }

    @Override
    public List<EvidenceExtractionServicesServerDTO> getEvidenceExtractionServicesServerListForStores(List<String> stores) {
        log.info("start");
        List<EvidenceExtractionServicesServerDTO> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select eess.id as idAtBusinessServices, eess.name as name, eess.ssh_address as sshAddress, ");
        sql.append(" eess.ssh_local_tunnel_port as sshLocalTunnelPort, eess.ssh_password as sshPassword,");
        sql.append(" eess.ssh_port as sshPort, eess.ssh_remote_tunnel_port as sshRemoteTunnelPort, ");
        sql.append(" eess.ssh_user as sshUser, eess.url as url, eess.evidence_services_server_id as serverId,");
        sql.append(" eess.use_tunnel as useTunnel");
        sql.append(" from evidence_extraction_services_server eess, place p");
        sql.append(" where p.evidence_extraction_services_server_id = eess.id and p.name in ('");
        sql.append(StringUtils.join(stores, "','")).append("')");

        Session session = this.getSession();
        list = session.createSQLQuery(sql.toString()).addScalar("idAtBusinessServices").addScalar("name").addScalar("sshAddress")
            .addScalar("sshLocalTunnelPort").addScalar("sshPassword").addScalar("sshPort").addScalar("sshRemoteTunnelPort")
            .addScalar("sshUser").addScalar("url").addScalar("serverId").addScalar("useTunnel")
            .setResultTransformer(Transformers.aliasToBean(EvidenceExtractionServicesServerDTO.class)).list();

        log.info("end");
        return list;
    }

    /**
     * Obtain a list of EvidenceExtractionServicesServer object using filters.
     *
     * @param eess Filter object
     * @return List<EvidenceExtractionServicesServer> List of EvidenceExtractionServicesServer objects
     */
    @Override
    public List<EvidenceExtractionServicesServer> getEvidenceExtractionServicesServersList(EvidenceExtractionServicesServer eess) {
        List<EvidenceExtractionServicesServer> list = null;
        Criteria criteria = this.getSession().createCriteria(EvidenceExtractionServicesServer.class);
        criteria.addOrder(Order.asc("id"));
        if (eess != null) {
            if (eess.getEvidenceServicesServer() != null && eess.getEvidenceServicesServer().getId() != null
                && eess.getEvidenceServicesServer().getId() > 0) {
                criteria.add(Restrictions.eq("evidenceServicesServer.id", eess.getEvidenceServicesServer().getId()));
            }
            if (eess.getUrl() != null && eess.getUrl().length() > 0) {
                criteria.add(Restrictions.ilike("url", eess.getUrl(), MatchMode.ANYWHERE));
            }
            if (eess.getStores() != null && eess.getStores().size() > 0) {
                List<String> names = new LinkedList<String>();
                for (Store store : eess.getStores()) {
                    names.add(store.getName());
                }
                criteria.setFetchMode("Place", FetchMode.JOIN).add(Restrictions.in("name", names));
            }
        }
        list = criteria.list();
        return list;
    }

    /**
     * Obtain a list of EvidenceExtractionServicesServer object using filters.
     *
     * @param eess Filter object
     * @return List<EvidenceExtractionServicesServer> List of EvidenceExtractionServicesServer objects
     */
    @Override
    public List<EvidenceExtractionServicesServer> getFreeEvidenceExtractionServicesServersList(
        EvidenceExtractionServicesServer eess) {
        List<EvidenceExtractionServicesServer> list = null;
        Criteria criteria = this.getSession().createCriteria(EvidenceExtractionServicesServer.class);
        criteria.addOrder(Order.asc("id"));
        if (eess != null) {
            if (eess.getEvidenceServicesServer() != null && eess.getEvidenceServicesServer().getId() != null
                && eess.getEvidenceServicesServer().getId() > 0) {
                criteria.add(Restrictions.eq("evidenceServicesServer.id", eess.getEvidenceServicesServer().getId()));
            }
            if (eess.getUrl() != null && eess.getUrl().length() > 0) {
                criteria.add(Restrictions.ilike("url", eess.getUrl(), MatchMode.ANYWHERE));
            }
        }
        // se elimina ya que in ees puede tener mucho stores asociados
        // if (eess != null && eess.getStore() != null && eess.getStore().getId() != null && eess.getStore().getId() > 0) {
        // criteria.add(Restrictions.or(Restrictions.eq("store.id", eess.getStore().getId()), Restrictions.isNull("store")));
        // } else {
        // criteria.add(Restrictions.isNull("store.id"));
        // }
        list = criteria.list();
        return list;
    }

    /**
     * Obtain a list of EvidenceProvider object using filters.
     *
     * @param evidenceProvider Filter object
     * @return List<EvidenceProvider> List of EvidenceProvider objects
     */
    @Override
    public List<EvidenceProvider> getEvidenceProvidersList(EvidenceProvider evidenceProvider) {
        log.info("start");
        List<EvidenceProvider> list = null;
        Criteria criteria = this.getSession().createCriteria(EvidenceProvider.class);
        criteria.addOrder(Order.asc("id"));
        if (evidenceProvider != null) {
            if (evidenceProvider.getName() != null && evidenceProvider.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", evidenceProvider.getName(), MatchMode.ANYWHERE));
            }
            if (evidenceProvider.getDescription() != null && evidenceProvider.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", evidenceProvider.getDescription(), MatchMode.ANYWHERE));
            }
            if (evidenceProvider.getEvidenceProviderType() != null && evidenceProvider.getEvidenceProviderType().getId() != null
                && evidenceProvider.getEvidenceProviderType().getId() > 0) {
                criteria.add(Restrictions.eq("evidenceProviderType.id", evidenceProvider.getEvidenceProviderType().getId()));
            }
            if (evidenceProvider.getStore() != null && evidenceProvider.getStore().getId() != null
                && evidenceProvider.getStore().getId() > 0) {
                criteria.add(Restrictions.eq("store.id", evidenceProvider.getStore().getId()));
            }
            if (!evidenceProvider.getAreas().isEmpty()) {
                Set<Integer> ids = new HashSet<Integer>();
                for (Area a : evidenceProvider.getAreas()) {
                    if (a.getId() != -1) {
                        ids.add(a.getId());
                    }
                }
                if (ids.size() > 0) {
                    criteria.createCriteria("areas").add(Restrictions.in("id", ids.toArray(new Integer[0])));
                    // debemos cargar tambien las relaciones
                    // criteria.createCriteria("relationEvidenceProviderLocationsFrom").add(Restrictions.in("area.id",
                    // ids.toArray(new Integer[0])));
                }

            }
            // if (evidenceProvider.getArea() != null && evidenceProvider.getArea().getAreaType() != null
            // && evidenceProvider.getArea().getAreaType().getId() != null && evidenceProvider.getArea().
            // getAreaType().getId() > 0) {
            // criteria.createCriteria("area").add(Restrictions.eq("areaType.id",
            // evidenceProvider.getArea().getAreaType().getId()));
            // }
        }

        list = criteria.list();
        log.info("end " + list.size());
        return list;
    }

    @Override
    public List<EvidenceProvider> getEvidenceProvidersByType(String type, Integer storeId) {
        List<EvidenceProvider> evidenceProviders = new ArrayList<EvidenceProvider>();

        StringBuilder sqlEvidenceProviders = new StringBuilder();
        sqlEvidenceProviders.append("select ep.* ");
        sqlEvidenceProviders.append(" from evidence_provider ep, evidence_provider_type ept ");
        sqlEvidenceProviders.append(" where ep.evidence_provider_type_id = ept.id ");
        sqlEvidenceProviders.append(" and ep.store_id = ").append(storeId).append(" ");
        sqlEvidenceProviders.append(" and ept.description = '").append(type).append("'");

        Session session = this.getSession();
        Query query = session.createSQLQuery(sqlEvidenceProviders.toString());
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> list = query.list();

        for (Map<String, Object> row : list) {
            EvidenceProvider ep = new EvidenceProvider();
            ep.setId((Integer) row.get("id"));
            ep.setDefinitionData((String) row.get("definition_data"));
            ep.setDescription((String) row.get("description"));
            ep.setName((String) row.get("name"));

            evidenceProviders.add(ep);
        }

        return evidenceProviders;
    }

    @Override
    public List<VadaroEvent> getLastVadaroEvents(Integer minutes, Integer storeId, Date timeEvidence) {

        List<VadaroEvent> ret = new ArrayList<VadaroEvent>();
        String date = DateFormatUtils.format(timeEvidence, "yyyy-MM-dd HH:mm:ss");
        StringBuilder sql = new StringBuilder();
//        sql.append("select * from vadaro_event ");
//        sql.append("where store_id = ").append(storeId);
//        sql.append(" and server_time >= (now() - interval '").append(minutes).append(" minutes') ");
//        sql.append(" order by server_time desc");
        sql.append("select * from vadaro_event ");
        sql.append("where store_id = ").append(storeId);
        sql.append(" and time >= (to_timestamp('").append(date).append("', 'YYYY-MM-DD HH24:MI:SS') - interval '").append(minutes).append(" minutes') ");
        sql.append(" and time <= '").append(date).append("'");
        sql.append(" order by time desc");
        Session session = this.getSession();

        try {
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            for (Map<String, Object> row : list) {
                VadaroEvent event = new VadaroEvent();

                event.setId((Integer) row.get("id"));
                event.setAbandoned((Integer) row.get("abandoned"));
                event.setCameraName((String) row.get("camera_name"));
                event.setEntered((Integer) row.get("entered"));
                event.setExited((Integer) row.get("exited"));
                event.setLength((Integer) row.get("length"));
                event.setService((String) row.get("service"));
                event.setServiceTime((Double) row.get("service_time"));
                event.setTime((Date) row.get("time"));
                event.setWaitTime((Double) row.get("wait_time"));
                event.setServerTime((Date) row.get("server_time"));

                Store store = new Store();
                store.setId((Integer) row.get("store_id"));
                event.setStore(store);

                ret.add(event);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }

        return ret;
    }

    @Override
    public List<EvidenceProvider> getEvidenceProvidersListByArea(EvidenceProvider evidenceProvider) {
        List<EvidenceProvider> evidenceProviders = new ArrayList<EvidenceProvider>();
        Set<Integer> areas = new HashSet<Integer>();
        for (Area a : evidenceProvider.getAreas()) {
            areas.add(a.getId());
        }
        StringBuilder sqlEvidenceProviders = new StringBuilder();
        sqlEvidenceProviders.append("select ep.id, ep.definition_data, ep.description, ep.name, ");
        sqlEvidenceProviders.append(" ep.evidence_provider_type_id, ep.store_id ");
        sqlEvidenceProviders.append(" from evidence_provider ep, rel_evidence_provider_area repa ");
        sqlEvidenceProviders.append(" where ep.store_id  = ").append(evidenceProvider.getStore().getId());
        if (!areas.isEmpty()) {
            sqlEvidenceProviders.append(" and repa.area_id in (").append(StringUtils.join(areas, ",")).append(")");
        }
        sqlEvidenceProviders.append(" and repa.evidence_provider_id = ep.id order by ep.description");

        Session session = this.getSession();
        try {
            Query query = session.createSQLQuery(sqlEvidenceProviders.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            for (Map<String, Object> row : list) {
                EvidenceProvider ep = new EvidenceProvider();
                ep.setId((Integer) row.get("id"));
                ep.setDefinitionData((String) row.get("definition_data"));
                ep.setDescription((String) row.get("description"));
                ep.setName((String) row.get("name"));
                EvidenceProviderType evidenceProviderType = new EvidenceProviderType();
                evidenceProviderType.setId((Integer) row.get("evidence_provider_type_id"));
                ep.setEvidenceProviderType(evidenceProviderType);
                Store store = new Store();
                store.setId((Integer) row.get("store_id"));
                ep.setStore(store);
                // se deben recuperar los RelationEvidenceProviderLocation asociados
                StringBuilder sqlRelationEvidenceProviderLocation = new StringBuilder();
                sqlRelationEvidenceProviderLocation.append("select id, ");
                sqlRelationEvidenceProviderLocation.append(" (case when default_evidence_provider is null then false ");
                sqlRelationEvidenceProviderLocation.append(" else default_evidence_provider end) as default_evidence_provider, ");
                sqlRelationEvidenceProviderLocation.append(" location, view_order, ");
                sqlRelationEvidenceProviderLocation.append(" evidence_provider_from_id, evidence_provider_to_id, area_id ");
                sqlRelationEvidenceProviderLocation.append(" from relation_evidence_provider_location ");
                sqlRelationEvidenceProviderLocation.append(" where evidence_provider_from_id  = ").append(ep.getId());
                sqlRelationEvidenceProviderLocation.append(" and area_id in(").append(StringUtils.join(areas, ",")).append(")");
                Query query2 = session.createSQLQuery(sqlRelationEvidenceProviderLocation.toString());
                query2.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                List<Map<String, Object>> list2 = query2.list();
                // ISSU con el NULL del default_evidence_provider
                for (Map<String, Object> row2 : list2) {
                    RelationEvidenceProviderLocation repl = new RelationEvidenceProviderLocation();
                    repl.setId((Integer) row2.get("id"));
                    repl.setDefaultEvidenceProvider((Boolean) row2.get("default_evidence_provider"));
                    repl.setLocation(Location.valueOf((String) row2.get("location")));
                    repl.setViewOrder((Integer) row2.get("view_order"));

                    Area a = new Area();
                    a.setId((Integer) row2.get("area_id"));
                    repl.setArea(a);
                    repl.setEvidenceProviderFrom(ep);
                    EvidenceProvider epTo = new EvidenceProvider();
                    epTo.setId((Integer) row2.get("evidence_provider_to_id"));
                    repl.setEvidenceProviderTo(epTo);
                    ep.getRelationEvidenceProviderLocationsFrom().add(repl);
                }
                evidenceProviders.add(ep);
            }
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }
        return evidenceProviders;
    }

    /**
     * Obtain a list of Sensor object using filters.
     *
     * @param sensor Filter object
     * @return List<EvidenceProvider> List of EvidenceProvider objects
     */
    @Override
    public List<Sensor> getSensorList(Sensor sensor) {
        List<Sensor> list = null;
        Criteria criteria = this.getSession().createCriteria(Sensor.class);
        criteria.addOrder(Order.asc("id"));
        if (sensor != null) {
            if (sensor.getName() != null && sensor.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", sensor.getName(), MatchMode.ANYWHERE));
            }
            if (sensor.getDescription() != null && sensor.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", sensor.getDescription(), MatchMode.ANYWHERE));
            }
            if (sensor.getStore() != null && sensor.getStore().getId() != null && sensor.getStore().getId() > 0) {
                criteria.add(Restrictions.eq("store.id", sensor.getStore().getId()));
            }
            if (sensor.getArea() != null && sensor.getArea().getId() != null && sensor.getArea().getId() > 0) {
                criteria.add(Restrictions.eq("area.id", sensor.getArea().getId()));
            }
            if (sensor.getArea() != null && sensor.getArea().getAreaType() != null
                && sensor.getArea().getAreaType().getId() != null && sensor.getArea().getAreaType().getId() > 0) {
                criteria.createCriteria("area").add(Restrictions.eq("areaType.id", sensor.getArea().getAreaType().getId()));
            }
        }
        list = criteria.list();
        return list;
    }

    @Override
    public Sensor getSensor(String name) {
        Criteria criteria = this.getSession().createCriteria(Sensor.class);
        criteria.setMaxResults(1);
        criteria.add(Restrictions.eq("name", name));
        Sensor sensor = (Sensor) criteria.uniqueResult();
        return sensor;
    }

    /**
     * Obtain a list of Country object using filters.
     *
     * @param country Filter object
     * @return List<Country> List of Country objects
     */
    @Override
    public List<Country> getCountryList(Country country) {
        List<Country> list = null;
        Criteria criteria = this.getSession().createCriteria(Country.class);
        criteria.addOrder(Order.asc("id"));
        if (country != null) {
            if (country.getName() != null && country.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", country.getName(), MatchMode.ANYWHERE));
            }
            if (country.getDescription() != null && country.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", country.getDescription(), MatchMode.ANYWHERE));
            }
            if (country.getCode() != null && country.getCode().length() > 0) {
                criteria.add(Restrictions.ilike("code", country.getCode(), MatchMode.ANYWHERE));
            }
        }
        list = criteria.list();
        return list;
    }

    /**
     * Obtain a list of Region object using filters.
     *
     * @param region Filter object
     * @return List<Region> List of Region objects
     */
    @Override
    public List<Region> getRegionList(Region region) {
        List<Region> list = null;
        Criteria criteria = this.getSession().createCriteria(Region.class);
        criteria.addOrder(Order.asc("id"));
        if (region != null) {
            if (region.getName() != null && region.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", region.getName(), MatchMode.ANYWHERE));
            }
            if (region.getDescription() != null && region.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", region.getDescription(), MatchMode.ANYWHERE));
            }
            if (region.getCountry() != null && region.getCountry().getId() != null && region.getCountry().getId() > 0) {
                Criteria criteriaCountry = criteria.createCriteria("country");
                criteriaCountry.add(Restrictions.eq("id", region.getCountry().getId()));
            }
        }
        list = criteria.list();
        return list;
    }

    /**
     * Obtain all actives evidence request for a particular store
     *
     * @param storeId
     * @return
     */
    @Override
    public List<? extends EvidenceRequest> getActiveEvidenceRequests(Integer storeId) {
        List<? extends EvidenceRequest> evidenceRequests = null;
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("select ");
        sbHql.append("   er.id as id, ");
        sbHql.append("   er.evidenceTime as evidenceTime, ");
        sbHql.append("   er.duration as duration, ");
        sbHql.append("   er.type as type, ");
        sbHql.append("   er.evidenceProvider as evidenceProvider, ");
        sbHql.append("   er.day as day, ");
        sbHql.append("   er.evidenceRequestType as evidenceRequestType, ");
        sbHql.append("   er.priorization ");
        sbHql.append(" from ");
        sbHql.append("   EvidenceRequest er, ");
        sbHql.append("   ExtractionPlanCustomizing epc, ");
        sbHql.append("   ExtractionPlanRange epr, ");
        sbHql.append("   ExtractionPlanRangeDetail eprd, ");
        sbHql.append("   SituationTemplate st ");
        sbHql.append("where ");
        sbHql.append("   epc.store.id = ").append(storeId).append(" and ");
        sbHql.append("   epc.active = true and ");
        sbHql.append("   epr.extractionPlanCustomizing.id = epc.id and ");
        sbHql.append("   eprd.extractionPlanRange.id = epr.id and ");
        sbHql.append("   er.extractionPlanRangeDetail.id = eprd.id and ");
        sbHql.append("   epc.situationTemplate.id = st.id and  ");
        sbHql.append("   st.active = true");
        evidenceRequests = this.getSession().createQuery(sbHql.toString())
            .setResultTransformer(Transformers.aliasToBean(EvidenceRequest.class)).list();

        return evidenceRequests;
    }

    @Override
    public List<EvidenceRequestDTO> getActiveEvidenceRequestDTOs(Integer storeId) throws ScopixException {
        List<EvidenceRequestDTO> evidenceRequestDTOs = new ArrayList<EvidenceRequestDTO>();
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("select ");
        sbHql.append("   er.id as businessServicesRequestId, ");
        sbHql.append("   er.evidenceProvider.id as deviceId, ");
        sbHql.append("   er.evidenceTime as requestedTime, ");
        sbHql.append("   er.day as dayOfWeek, ");
        sbHql.append("   '{v}' as requestType, ");
        sbHql.append("   er.duration as duration,");
        sbHql.append("   er.priorization as priorization,");
        sbHql.append("   st.live as live");
        sbHql.append(" from ");
        sbHql.append("   EvidenceRequest er,");
        sbHql.append("   ExtractionPlanCustomizing epc, ");
        sbHql.append("   ExtractionPlanRange epr, ");
        sbHql.append("   ExtractionPlanRangeDetail eprd , ");
        sbHql.append("   SituationTemplate st ");
        sbHql.append("where ");
        sbHql.append("   epc.store.id = ").append(storeId).append(" and ");
        sbHql.append("   epc.active = true and ");
        sbHql.append("   epr.extractionPlanCustomizing.id = epc.id and ");
        sbHql.append("   eprd.extractionPlanRange.id = epr.id and ");
        sbHql.append("   er.extractionPlanRangeDetail.id = eprd.id and ");
        sbHql.append("   epc.situationTemplate.id = st.id and  ");
        sbHql.append("   st.active = true and ");
        sbHql.append("   er.type = :type1 ");

        for (EvidenceType type : EvidenceType.values()) {
            String classTextConvert = getClassConvert(type);
            String s = StringUtils.replace(sbHql.toString(), "{v}", classTextConvert);
            Query q = this.getSession().createQuery(s);
            q.setParameter("type1", type);
            evidenceRequestDTOs.addAll(q.setResultTransformer(Transformers.aliasToBean(EvidenceRequestDTO.class)).list());
        }

        // evidenceRequestDTOs = this.getSession().createQuery(sbHql.toString()).
        // setResultTransformer(Transformers.aliasToBean(EvidenceRequestDTO.class)).list();
        return evidenceRequestDTOs;
    }

    private String getClassConvert(EvidenceType evidenceType) throws ScopixException {
        String classRequest = null;
        switch (evidenceType) {
            case VIDEO:
                classRequest = RequestedVideo.class.getSimpleName();
                break;
            case IMAGE:
                classRequest = RequestedImage.class.getSimpleName();
                break;
            case XML:
                classRequest = RequestedXml.class.getSimpleName();
                break;
            default:
                log.info("no se recibe evidenceType valido" + evidenceType.getName());
                throw new ScopixException("Tipo de evidencia no soportado " + evidenceType.getName());

        }
        return classRequest;
    }

    /**
     *
     * @param evidenceRequestIds
     * @return
     */
    @Override
    public List<EvidenceRequest> getEvidenceRequestsList(List<Integer> evidenceRequestIds) throws ScopixException {
        List<EvidenceRequest> evidenceRequests = null;
        try {
            evidenceRequests = new ArrayList<EvidenceRequest>();
            StringBuilder sql = new StringBuilder();
            sql.append("select er.id, er.day, er.duration, er.evidence_provider_id,  ");
            sql.append("er.evidence_time, er.metric_id, er.type,  ");
            sql.append("m.description AS metric_description, ep.description as ep_description ");
            sql.append("from evidence_request er, metric m, evidence_provider ep ");
            sql.append("where  ");
            sql.append("m.id = er.metric_id ");
            sql.append("and ep.id = er.evidence_provider_id and er.id in (");

            StringBuilder ids = new StringBuilder();
            for (Integer id : evidenceRequestIds) {
                ids.append(id);
                ids.append(", ");
            }
            sql.append(ids.substring(0, ids.length() - 2));
            sql.append(")");

            Session session = this.getSession();
            try {
                Query query = session.createSQLQuery(sql.toString());
                query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                List<Map<String, Object>> list = query.list();
                for (Map m : list) {
                    EvidenceRequest er = null;
                    if (m.get("duration") != null) {
                        er = new RequestedVideo();
                        ((RequestedVideo) er).setDuration((Integer) m.get("duration"));
                    } else {
                        er = new RequestedImage();
                    }
                    er.setId((Integer) m.get("id"));
                    er.setDay((Integer) m.get("day"));
                    EvidenceProvider ep = new EvidenceProvider();
                    ep.setId((Integer) m.get("evidence_provider_id"));
                    ep.setDescription((String) m.get("ep_description"));
                    er.setEvidenceProvider(ep);
                    er.setEvidenceTime((Date) m.get("evidence_time"));
                    EvidenceType et = EvidenceType.valueOf((String) m.get("type"));
                    er.setType(et);
                    Metric mt = new Metric();
                    mt.setId((Integer) m.get("metric_id"));
                    mt.setDescription((String) m.get("metric_description"));
                    er.setMetric(mt);
                    evidenceRequests.add(er);
                }

            } finally {
                this.releaseSession(session);
            }
        } catch (DataAccessResourceFailureException e) {
            throw new ScopixException(e);
        } catch (IllegalStateException e) {
            throw new ScopixException(e);
        } catch (HibernateException e) {
            throw new ScopixException(e);
        }
        return evidenceRequests;
    }

    @Override
    public List<SituationTemplate> getSituationTemplateListByArea(Set<Integer> areaIds) {

        List<SituationTemplate> situationTemplates = new ArrayList<SituationTemplate>();
        if (areaIds != null && !areaIds.isEmpty()) {
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct ");
            sql.append(" st.id, st.active, st.evidence_spring_bean_evaluator_name, st.name, st.area_type_id , st.product_id ");
            sql.append(" from ");
            sql.append(" situation_template  st, ");
            sql.append(" situation s, ");
            sql.append(" metric m ");
            sql.append(" where m.area_id in(").append(StringUtils.join(areaIds, ",")).append(")");
            sql.append(" and s.id = m.situation_id ");
            sql.append(" and st.id = s.situation_template_id ");
            sql.append(" and active = true ");
            sql.append(" order by st.name");
            Session session = this.getSession();
            try {
                Query query = session.createSQLQuery(sql.toString());
                query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
                List<Map<String, Object>> list = query.list();
                for (Map<String, Object> row : list) {
                    SituationTemplate st = new SituationTemplate();
                    st.setId((Integer) row.get("id"));
                    st.setActive((Boolean) row.get("active"));
                    st.setEvidenceSpringBeanEvaluatorName((String) row.get("evidence_spring_bean_evaluator_name"));
                    st.setName((String) row.get("name"));

                    AreaType at = new AreaType();
                    at.setId((Integer) row.get("area_type_id"));
                    st.setAreaType(at);

                    Product p = new Product();
                    p.setId((Integer) row.get("product_id"));
                    st.setProduct(p);
                    situationTemplates.add(st);
                }
            } catch (DataAccessResourceFailureException e) {
                log.error(e, e);
            } catch (IllegalStateException e) {
                log.error(e, e);
            } catch (HibernateException e) {
                log.error(e, e);
            } finally {
                this.releaseSession(session);
            }
        }
        return situationTemplates;
    }

    @Override
    public List<SensorAndEvidenceExtractionServicesServerDTO> getSensorAndEvidenceExtractionServicesServerList(
        List<String> sensors) throws ScopixException {
        log.info("start");
        List<SensorAndEvidenceExtractionServicesServerDTO> listResp = new LinkedList<SensorAndEvidenceExtractionServicesServerDTO>();

        StringBuilder sql = new StringBuilder();
        sql.append("select s.name as sensor_name, eess.id, eess.name, eess.ssh_address, eess.ssh_local_tunnel_port,");
        sql.append(" eess.ssh_password, eess.ssh_port, eess.ssh_remote_tunnel_port, eess.ssh_user, eess.url,");
        sql.append(" p.name as store_name");
        sql.append(" from sensor s,place p, evidence_extraction_services_server eess");
        sql.append(" where");
        sql.append(" s.store_id = p.id");
        sql.append(" and p.evidence_extraction_services_server_id = eess.id");
        sql.append(" and s.name in (");
        sql.append(StringUtils.join(sensors, ","));
        sql.append(" )");
        Session session = this.getSession();
        try {
            SensorAndEvidenceExtractionServicesServerDTO dto = null;
            EvidenceExtractionServicesServerDTO eessDTO = null;
            Query query = session.createSQLQuery(sql.toString());
            query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String, Object>> list = query.list();
            for (Map<String, Object> row : list) {
                dto = new SensorAndEvidenceExtractionServicesServerDTO();
                eessDTO = new EvidenceExtractionServicesServerDTO();

                dto.setSensorName((String) row.get("sensor_name"));
                dto.setStoreName((String) row.get("store_name"));

                eessDTO.setIdAtBusinessServices((Integer) row.get("id"));
                eessDTO.setName((String) row.get("name"));
                eessDTO.setName((String) row.get("ssh_address"));
                eessDTO.setName((String) row.get("ssh_local_tunnel_port"));
                eessDTO.setName((String) row.get("ssh_password"));
                eessDTO.setName((String) row.get("ssh_port"));
                eessDTO.setName((String) row.get("ssh_remote_tunnel_port"));
                eessDTO.setName((String) row.get("ssh_user"));
                eessDTO.setName((String) row.get("url"));

                dto.setEvidenceExtractionServicesServerDTO(eessDTO);

                listResp.add(dto);
            }
        } catch (DataAccessResourceFailureException e) {
            log.error(e, e);
        } catch (IllegalStateException e) {
            log.error(e, e);
        } catch (HibernateException e) {
            log.error(e, e);
        } finally {
            this.releaseSession(session);
        }

        log.info("end");
        return listResp;
    }
}
