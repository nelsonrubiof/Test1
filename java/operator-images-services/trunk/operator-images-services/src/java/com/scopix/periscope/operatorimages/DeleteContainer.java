/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contenedor para borrar proofs contiene ruta de donde encontrar los archivos y Lista de nombres de archivos a borrar
 * 
 * @author nelson
 * @version 1.0.0
 */
@XmlRootElement(name = "deleteContainer")
public class DeleteContainer {

    private String pathOrigen;
    private List<String> list;

    /**
     * @return the pathOrigen
     */
    public String getPathOrigen() {
        return pathOrigen;
    }

    /**
     * @param pathOrigen the pathOrigen to set
     */
    public void setPathOrigen(String pathOrigen) {
        this.pathOrigen = pathOrigen;
    }

    /**
     * @return the list
     */
    public List<String> getList() {
        if (list == null) {
            list = new ArrayList<String>();
        }
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<String> list) {
        this.list = list;
    }
}
