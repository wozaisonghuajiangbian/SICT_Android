package com.dtr.sict.bean;

import java.util.ArrayList;

/**
 * Created by ace on 2017/7/23.
 */
public class Equipment {
    private String operation;
    private String id;
    private String assetNum;
    private String roomNum;
    private String pcType;
    private String pcBrand;
    private String department;
    private String userName;
    private String system;
    private String officeSoft;
    private String antiSoft;

    //设备对象
    public Equipment(String id, String assetNum, String roomNum, String pcType, String pcBrand, String department, String userName, String system, String officeSoft, String antiSoft)
    {
        this.id = id;
        this.assetNum = assetNum;
        this.roomNum = roomNum;
        this.pcType = pcType;
        this.pcBrand = pcBrand;
        this.department = department;
        this.userName = userName;
        this.system = system;
        this.officeSoft = officeSoft;
        this.antiSoft = antiSoft;
    }

    public Equipment(String assetNum, String userName, String roomNum, String department, String pcType) {
        this.assetNum = assetNum;
        this.pcType = pcType;
        this.userName = userName;
        this.roomNum = roomNum;
        this.department = department;
    }
    
    public Equipment(ArrayList<String> attribute)
    {
        this.operation = attribute.get(0);
        this.id = attribute.get(1);
        this.assetNum = attribute.get(2);
        this.roomNum = attribute.get(3);
        this.pcType = attribute.get(4);
        this.pcBrand = attribute.get(5);
        this.department = attribute.get(6);
        this.userName = attribute.get(7);
        this.system = attribute.get(8);
        this.officeSoft = attribute.get(9);
        this.antiSoft = attribute.get(10);
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(String assetNum) {
        this.assetNum = assetNum;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getPcType() {
        return pcType;
    }

    public void setPcType(String pcType) {
        this.pcType = pcType;
    }

    public String getPcBrand() {
        return pcBrand;
    }

    public void setPcBrand(String pcBrand) {
        this.pcBrand = pcBrand;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getOfficeSoft() {
        return officeSoft;
    }

    public void setOfficeSoft(String officeSoft) {
        this.officeSoft = officeSoft;
    }

    public String getAntiSoft() {
        return antiSoft;
    }

    public void setAntiSoft(String antiSoft) {
        this.antiSoft = antiSoft;
    }
}
