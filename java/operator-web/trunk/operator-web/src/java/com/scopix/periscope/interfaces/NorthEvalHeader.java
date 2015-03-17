package com.scopix.periscope.interfaces;

/**
 * Métodos expuestos para la parte superior de la pantalla de evaluaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public interface NorthEvalHeader {

    /**
     * @return the login
     */
    String getLogin();

    /**
     * @param login the login to set
     */
    void setLogin(String login);

    /**
     * @return the clientName
     */
    String getClientName();

    /**
     * @param clientName the clientName to set
     */
    void setClientName(String clientName);

    /**
     * @return the storeName
     */
    String getStoreName();

    /**
     * @param storeName the storeName to set
     */
    void setStoreName(String storeName);

    /**
     * @return the zoneName
     */
    String getZoneName();

    /**
     * @param zoneName the zoneName to set
     */
    void setZoneName(String zoneName);

    /**
     * @return the evidenceDate
     */
    String getEvidenceDate();

    /**
     * @param evidenceDate the evidenceDate to set
     */
    void setEvidenceDate(String evidenceDate);
}