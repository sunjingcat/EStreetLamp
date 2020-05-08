package com.zz.lamp.bean;

public class MapListBean {
  private String  id;// 10,
    private String          deviceKind;// 1,
    private String         addr;// 12345678,
    private String         code;// null,
    private String       name;// 集中器1,
    private String       markerIconPath;// data;//image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHN2ZyB3aWR0aD0iMTZweCIgaGVpZ2h0PSIxNnB4IiB2aWV3Qm94PSIwIDAgMTYgMTYiIHZlcnNpb249IjEuMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayI+CiAgICA8IS0tIEdlbmVyYXRvcjogU2tldGNoIDU5ICg4NjEyNykgLSBodHRwczovL3NrZXRjaC5jb20gLS0+CiAgICA8dGl0bGU+UmVjdGFuZ2xlIDIyPC90aXRsZT4KICAgIDxkZXNjPkNyZWF0ZWQgd2l0aCBTa2V0Y2guPC9kZXNjPgogICAgPGcgaWQ9IumCgOivt+mTvuaOpSIgc3Ryb2tlPSJub25lIiBzdHJva2Utd2lkdGg9IjEiIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPGcgaWQ9Iuafpeeci+aJgOacieWIh+WbvuWkh+S7vSIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTc0LjAwMDAwMCwgLTgxMC4wMDAwMDApIj4KICAgICAgICAgICAgPGcgaWQ9Imxhbmh1LWljb24tU2xpY2luZyIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoNzQuMDAwMDAwLCA4MTAuMDAwMDAwKSI+CiAgICAgICAgICAgICAgICA8ZyBpZD0i57yW57uEIj4KICAgICAgICAgICAgICAgICAgICA8Zz4KICAgICAgICAgICAgICAgICAgICAgICAgPHJlY3QgaWQ9IlJlY3RhbmdsZS0yMiIgeD0iMCIgeT0iMCIgd2lkdGg9IjE2IiBoZWlnaHQ9IjE2Ij48L3JlY3Q+CiAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik0xMS44MTM4NzIyLDQuMTU2NDM1NiBDMTIuMTI0ODQxOSw0LjQ1NTEwOTU1IDEyLjA0NjE1MTMsNC44NzY0NjU5NiAxMS41Nzc4MDAzLDUuNDIwNTA0ODEgTDkuOTU3LDcuMjkyIEw5Ljk2MjA4NTA3LDcuMjk3ODUxOTUgTDkuMzQzODEwNTYsNy45NzcwNTUzMyBMMTAuNTI3NTY0MSw5LjI5OTU1NDIxIEM5LjYyMTAzNTc3LDEwLjE5NDg3OTcgOC42ODM4MDUxMywxMC45MTEwNjc3IDcuNzE1ODcyMTgsMTEuNDQ4MTE3OSBDNi43NDc5MzkyMiwxMS45ODUxNjgyIDUuNTA5MzE1MTYsMTIuMTMwMjU1NyA0LDExLjg4MzM4MDMgTDguODk1LDYuMzQyIEw4Ljg5MjA1ODUsNi4zMzk0OTgxNCBMMTAuNTU2NjExNyw0LjU0MzQ1Nzk0IEMxMS4wODM4MTU2LDMuOTg2NzY5MSAxMS41MDI5MDI0LDMuODU3NzYxNjUgMTEuODEzODcyMiw0LjE1NjQzNTYgWiIgaWQ9IkNvbWJpbmVkLVNoYXBlIiBmaWxsPSIjRkZGRkZGIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSg4LjAwMDAwMCwgOC4wMDAwMDApIHJvdGF0ZSgyLjAwMDAwMCkgdHJhbnNsYXRlKC04LjAwMDAwMCwgLTguMDAwMDAwKSAiPjwvcGF0aD4KICAgICAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgIDwvZz4KICAgICAgICA8L2c+CiAgICA8L2c+Cjwvc3ZnPg==,
    private String      status;// 0,
    private String      warnStatus;// 0,
    private Double      lng;// null,
    private Double      lat;// null,
    private String       lightOnTime;// null,
    private String      lightOffTime;// null,
    private String       otherContent;// null

    public String getId() {
        return id;
    }

    public String getDeviceKind() {
        return deviceKind;
    }

    public String getAddr() {
        return addr;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getMarkerIconPath() {
        return markerIconPath;
    }

    public String getStatus() {
        return status;
    }

    public String getWarnStatus() {
        return warnStatus;
    }

    public Double getLng() {
        if (lng== null)return 0.0;
        return lng;
    }

    public Double getLat() {
        if (lat== null)return 0.0;
        return lat;
    }

    public String getLightOnTime() {
        return lightOnTime;
    }

    public String getLightOffTime() {
        return lightOffTime;
    }

    public String getOtherContent() {
        return otherContent;
    }
}
