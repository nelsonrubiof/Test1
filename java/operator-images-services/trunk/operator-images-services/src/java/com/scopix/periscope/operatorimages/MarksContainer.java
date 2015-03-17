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
@XmlRootElement(name = "marksContainer")
public class MarksContainer {

    private List<Marks> marks;

    /**
     * @return the marks
     */
    public List<Marks> getMarks() {
        return marks;
    }

    /**
     * @param marks the marks to set
     */
    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }
}
