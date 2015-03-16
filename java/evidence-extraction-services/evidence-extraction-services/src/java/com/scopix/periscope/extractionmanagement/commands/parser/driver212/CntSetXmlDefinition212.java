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
public class CntSetXmlDefinition212 {
    private String name;
    private long starttime;
    private int delta;
    private List<CntGroupXmlDefinition212> cntGroups;

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

    public List<CntGroupXmlDefinition212> getCntGroups() {
        if (cntGroups == null) {
            cntGroups = new ArrayList<CntGroupXmlDefinition212>();
        }
        return cntGroups;
    }

    public void setCntGroups(List<CntGroupXmlDefinition212> cntGroups) {
        this.cntGroups = cntGroups;
    }

    public void addCntGroup(CntGroupXmlDefinition212 ctnGroup){
        getCntGroups().add(ctnGroup);
    }
}
