package com.scopix.periscope.model;

import java.io.Serializable;

/**
 * Clase para referenciar datos de las cámaras de una evidencia
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Camara implements Serializable, Comparable<Camara> {

    private Integer id;
    private String name;
    private String evidencePath;
    private String evidenceType;
    private String templatePath;
    private String operatorImagesIp;
    private static final long serialVersionUID = 6990396170562636843L;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the evidencePath
     */
    public String getEvidencePath() {
        return evidencePath;
    }

    /**
     * @param evidencePath the evidencePath to set
     */
    public void setEvidencePath(String evidencePath) {
        this.evidencePath = evidencePath;
    }

    /**
     * @return the evidenceType
     */
    public String getEvidenceType() {
        return evidenceType;
    }

    /**
     * @param evidenceType the evidenceType to set
     */
    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    /**
     * @return the templatePath
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * @param templatePath the templatePath to set
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
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

    @Override
    public int compareTo(Camara camara) {
        int result = 0;
        if (this.getName() != null) {
            String name2 = camara.getName();
            if (name2 != null) {
                result = this.getName().compareToIgnoreCase(name2);
            }
        }
        return result;
    }

    /**
     * @return the operatorImagesIp
     */
    public String getOperatorImagesIp() {
        return operatorImagesIp;
    }

    /**
     * @param operatorImagesIp the operatorImagesIp to set
     */
    public void setOperatorImagesIp(String operatorImagesIp) {
        this.operatorImagesIp = operatorImagesIp;
    }
}