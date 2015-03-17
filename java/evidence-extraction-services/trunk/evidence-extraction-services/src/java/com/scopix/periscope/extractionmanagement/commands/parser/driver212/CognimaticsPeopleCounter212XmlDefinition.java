/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands.parser.driver212;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class CognimaticsPeopleCounter212XmlDefinition {

    private List<CntSetXmlDefinition212> cntset;

    public CognimaticsPeopleCounter212XmlDefinition() {
    }

    public List<CntSetXmlDefinition212> getCntset() {
        if (cntset == null) {
            cntset = new ArrayList<CntSetXmlDefinition212>();
        }
        return cntset;
    }

    public void setCntset(List<CntSetXmlDefinition212> value) {
        this.cntset = value;
    }

    public void addCntset(CntSetXmlDefinition212 cntSet) {
        getCntset().add(cntSet);
    }
}
