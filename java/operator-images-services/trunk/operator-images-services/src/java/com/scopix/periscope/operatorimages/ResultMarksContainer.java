/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@XmlRootElement(name = "resultMarksContainer")
public class ResultMarksContainer {

    private List<ResultMarks> results;

    /**
     * @return the results
     */
    public List<ResultMarks> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<ResultMarks> results) {
        this.results = results;
    }
}
