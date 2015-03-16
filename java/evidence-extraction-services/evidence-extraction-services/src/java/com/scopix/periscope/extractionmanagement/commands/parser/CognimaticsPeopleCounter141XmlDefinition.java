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
public class CognimaticsPeopleCounter141XmlDefinition {

    private List<CntSetXmlDefinition> cntset;

    public CognimaticsPeopleCounter141XmlDefinition() {
    }

    public List<CntSetXmlDefinition> getCntset() {
        if (cntset == null) {
            cntset = new ArrayList<CntSetXmlDefinition>();
        }
        return cntset;
    }

    public void setCntset(List<CntSetXmlDefinition> value) {
        this.cntset = value;
    }

    public void addCntset(CntSetXmlDefinition cntSet) {
        getCntset().add(cntSet);
    }
}
