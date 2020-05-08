package com.zz.lamp.bean;

import java.util.List;

public class TestPost {
    String alarmStatus;
    String  handleDescription;
    String  id;
    List <String> handleFile;

    public TestPost(String alarmStatus, String handleDescription, String id, List<String> handleFile) {
        this.alarmStatus = alarmStatus;
        this.handleDescription = handleDescription;
        this.id = id;
        this.handleFile = handleFile;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public List<String> getHandleFile() {
        return handleFile;
    }
}
