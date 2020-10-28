package com.zz.lamp.bean;

public class ImageBack {
    String id;
    String base64 ;
    String modelId;
    String type;
    String path;

    public String getPath() {
        return path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getBase64() {
        return base64;
    }

    public String getModelId() {
        return modelId;
    }

    public String getType() {
        return type;
    }
}
