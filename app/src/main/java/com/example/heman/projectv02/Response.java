package com.example.heman.projectv02;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yashjain on 6/27/17.
 */

public class Response {

    public static final String QNO="qno";
    public static final String ONO="ono";
    public static final String OPTION="otext";

    private int rId;
    private String sId;
    private String userId;
    private String language;
    private int version;
    private float longitude;
    private float latitude;
    private float altitude;
    private String synced;
    private JSONArray result;
    private String fName;
    private String lName;
    private String address;
    private int age;

    public void addResult(String qNo,String oNo,String option){
        if(result==null)result=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(QNO, qNo);
            jsonObject.put(ONO, oNo);
            jsonObject.put(OPTION, option);
        }
        catch (org.json.JSONException jsonException){
            Log.d("JSON EXCEPTION: ","INSIDE RESPONSE CLASS "+jsonException.getMessage()+"\n"+jsonException.getLocalizedMessage());
        }
        result.put(jsonObject);
    }


    public Response(){
    }


    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public JSONArray getResult() {
        return result;
    }

    public void setResult(JSONArray result) {
        this.result = result;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }
}
