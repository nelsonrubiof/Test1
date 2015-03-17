package com.scopix.periscope.corporate;

/**
 * Clase para referenciar datos de los clientes (corporates) de la aplicación
 * 
 * @author  carlos polo
 * @version 1.0.0
 * @since   6.0
 */
public class Corporate  {
    
    private Integer id;
    private String name;
    private String description;

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}