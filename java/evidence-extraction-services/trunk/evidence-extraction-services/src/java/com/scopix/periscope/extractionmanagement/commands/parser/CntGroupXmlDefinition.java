/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class CntGroupXmlDefinition {

    private List<CntXmlDefinition> cnts;

    /**
     * @return the cnts
     */
    public List<CntXmlDefinition> getCnts() {
        if (cnts == null) {
            cnts = new ArrayList<CntXmlDefinition>();
        }
        return cnts;
    }

    /**
     * @param cnts the cnts to set
     */
    public void setCnts(List<CntXmlDefinition> cnts) {
        this.cnts = cnts;
    }

    public void addCnt(CntXmlDefinition cnt) {
        getCnts().add(cnt);
    }
}
