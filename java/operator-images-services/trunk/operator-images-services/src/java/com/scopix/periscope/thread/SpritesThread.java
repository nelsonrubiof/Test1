package com.scopix.periscope.thread;

import org.apache.log4j.Logger;

import com.scopix.periscope.ffmpeg.FFmpegImpl;

public class SpritesThread extends Thread {

    private String metodo;
    private String fileName;
    private FFmpegImpl fFmpeg;
    private String spritesScaleX;
    private String spritesScaleY;
    private String spritesBaseDir;
    private String operatorImgUrl;
    private String[] ffmpegSpritesCmd;
    private static Logger log = Logger.getLogger(SpritesThread.class);

    @Override
    public void run() {
        log.info("start, metodo: [" + getMetodo() + "]");
        boolean success = false;
        if ("generateVideoSprites".equalsIgnoreCase(getMetodo())) {
            success = getfFmpeg().generateVideoSprites(getFileName(), getSpritesBaseDir(), getOperatorImgUrl(),
                    getFfmpegSpritesCmd(), getSpritesScaleX(), getSpritesScaleY());
        }
        log.info("end, success: [" + success + "]");
    }

    public String getSpritesScaleX() {
        return spritesScaleX;
    }

    public void setSpritesScaleX(String spritesScaleX) {
        this.spritesScaleX = spritesScaleX;
    }

    public String getSpritesScaleY() {
        return spritesScaleY;
    }

    public void setSpritesScaleY(String spritesScaleY) {
        this.spritesScaleY = spritesScaleY;
    }

    public String getSpritesBaseDir() {
        return spritesBaseDir;
    }

    public void setSpritesBaseDir(String spritesBaseDir) {
        this.spritesBaseDir = spritesBaseDir;
    }

    public String getOperatorImgUrl() {
        return operatorImgUrl;
    }

    public void setOperatorImgUrl(String operatorImgUrl) {
        this.operatorImgUrl = operatorImgUrl;
    }

    public String[] getFfmpegSpritesCmd() {
        return ffmpegSpritesCmd;
    }

    public void setFfmpegSpritesCmd(String[] ffmpegSpritesCmd) {
        this.ffmpegSpritesCmd = ffmpegSpritesCmd;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FFmpegImpl getfFmpeg() {
        return fFmpeg;
    }

    public void setfFmpeg(FFmpegImpl fFmpeg) {
        this.fFmpeg = fFmpeg;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}