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
public class CntSetXmlDefinition {
    private String name;
    private long starttime;
    private int delta;
    private List<CntGroupXmlDefinition> cntGroups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public List<CntGroupXmlDefinition> getCntGroups() {
        if (cntGroups == null) {
            cntGroups = new ArrayList<CntGroupXmlDefinition>();
        }
        return cntGroups;
    }

    public void setCntGroups(List<CntGroupXmlDefinition> cntGroups) {
        this.cntGroups = cntGroups;
    }

    public void addCntGroup(CntGroupXmlDefinition ctnGroup){
        getCntGroups().add(ctnGroup);
    }
}
