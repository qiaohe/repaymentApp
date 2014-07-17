package com.huayuan.web.dto;

import java.io.Serializable;

/**
 * Created by Richard Xue on 14-7-16.
 */
public class CropZoomData implements Serializable {
    private Double viewPortH;
    private Double viewPortW;
    private Integer imageX;
    private Integer imageY;
    private Integer imageW;
    private Integer imageH;
    private Double imageRotate;
    private String imageSource;
    private Double selectorX;
    private Double selectorY;
    private Double selectorW;
    private Double selectorH;

    public Double getViewPortH() {
        return viewPortH;
    }

    public void setViewPortH(Double viewPortH) {
        this.viewPortH = viewPortH;
    }

    public Double getViewPortW() {
        return viewPortW;
    }

    public void setViewPortW(Double viewPortW) {
        this.viewPortW = viewPortW;
    }

    public Integer getImageX() {
        return imageX;
    }

    public void setImageX(Integer imageX) {
        this.imageX = imageX;
    }

    public Integer getImageY() {
        return imageY;
    }

    public void setImageY(Integer imageY) {
        this.imageY = imageY;
    }

    public Double getImageRotate() {
        return imageRotate;
    }

    public void setImageRotate(Double imageRotate) {
        this.imageRotate = imageRotate;
    }

    public Integer getImageW() {
        return imageW;
    }

    public void setImageW(Integer imageW) {
        this.imageW = imageW;
    }

    public Integer getImageH() {
        return imageH;
    }

    public void setImageH(Integer imageH) {
        this.imageH = imageH;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public Double getSelectorX() {
        return selectorX;
    }

    public void setSelectorX(Double selectorX) {
        this.selectorX = selectorX;
    }

    public Double getSelectorY() {
        return selectorY;
    }

    public void setSelectorY(Double selectorY) {
        this.selectorY = selectorY;
    }

    public Double getSelectorW() {
        return selectorW;
    }

    public void setSelectorW(Double selectorW) {
        this.selectorW = selectorW;
    }

    public Double getSelectorH() {
        return selectorH;
    }

    public void setSelectorH(Double selectorH) {
        this.selectorH = selectorH;
    }
}
