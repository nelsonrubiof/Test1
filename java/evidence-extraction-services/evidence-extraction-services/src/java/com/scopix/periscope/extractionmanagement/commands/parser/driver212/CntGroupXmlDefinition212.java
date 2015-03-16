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
public class CntGroupXmlDefinition212 {
    private long endtime;
    private List<CntXmlDefinition212> cnts;

    /**
     * @return the cnts
     */
    public List<CntXmlDefinition212> getCnts() {
        if (cnts == null) {
            cnts = new ArrayList<CntXmlDefinition212>();
        }
        return cnts;
    }

    /**
     * @param cnts the cnts to set
     */
    public void setCnts(List<CntXmlDefinition212> cnts) {
        this.cnts = cnts;
    }

    public void addCnt(CntXmlDefinition212 cnt) {
        getCnts().add(cnt);
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
}
