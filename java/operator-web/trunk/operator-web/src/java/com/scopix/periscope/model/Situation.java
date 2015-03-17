package com.scopix.periscope.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase para referenciar datos de las situaciones a evaluar en la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Situation implements Serializable {

    private Integer id;
    private String area;
    private Date length;
    private String client;
    private String storeName;
    private boolean rejected;
    private String productName;
    private String storeDescription;
    private String evidenceDateTime;
    private String productDescription;
    private String rejectedObservation;
    private Integer pendingEvaluationId;
    private List<Metric> metricas = new ArrayList<Metric>();
    private static final long serialVersionUID = 5284580932731922438L;

    // utilizados para el envío asíncrono de evaluaciones
    private Long sessionId;
    private Client cliente;
    private String snapshotTime;
    private String evaluationLogin;

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the length
     */
    public Date getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Date length) {
        this.length = length;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the rejected
     */
    public boolean isRejected() {
        return rejected;
    }

    /**
     * @param rejected the rejected to set
     */
    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the evidenceDateTime
     */
    public String getEvidenceDateTime() {
        return evidenceDateTime;
    }

    /**
     * @param evidenceDateTime the evidenceDateTime to set
     */
    public void setEvidenceDateTime(String evidenceDateTime) {
        this.evidenceDateTime = evidenceDateTime;
    }

    /**
     * @return the pendingEvaluationId
     */
    public Integer getPendingEvaluationId() {
        return pendingEvaluationId;
    }

    /**
     * @param pendingEvaluationId the pendingEvaluationId to set
     */
    public void setPendingEvaluationId(Integer pendingEvaluationId) {
        this.pendingEvaluationId = pendingEvaluationId;
    }

    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return the rejectedObservation
     */
    public String getRejectedObservation() {
        return rejectedObservation;
    }

    /**
     * @param rejectedObservation the rejectedObservation to set
     */
    public void setRejectedObservation(String rejectedObservation) {
        this.rejectedObservation = rejectedObservation;
    }

    /**
     * @return the metricas
     */
    public List<Metric> getMetricas() {
        return metricas;
    }

    /**
     * @param metricas the metricas to set
     */
    public void setMetricas(List<Metric> metricas) {
        this.metricas = metricas;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public String getSnapshotTime() {
        return snapshotTime;
    }

    public void setSnapshotTime(String snapshotTime) {
        this.snapshotTime = snapshotTime;
    }

    public String getEvaluationLogin() {
        return evaluationLogin;
    }

    public void setEvaluationLogin(String evaluationLogin) {
        this.evaluationLogin = evaluationLogin;
    }

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * @return the storeDescription
	 */
	public String getStoreDescription() {
		return storeDescription;
	}

	/**
	 * @param storeDescription the storeDescription to set
	 */
	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}
}