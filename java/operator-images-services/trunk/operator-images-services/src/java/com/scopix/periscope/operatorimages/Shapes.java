/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@XmlRootElement(name = "shapes")
public class Shapes {

    private Integer positionX;
    private Integer positionY;
    private Integer width;
    private Integer height;
    private String color;

    /**
     * @return the positionX
     */
    public Integer getPositionX() {
        return positionX;
    }

    /**
     * @param positionX the positionX to set
     */
    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    /**
     * @return the positionY
     */
    public Integer getPositionY() {
        return positionY;
    }

    /**
     * @param positionY the positionY to set
     */
    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    /**
     * @return the width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
}
