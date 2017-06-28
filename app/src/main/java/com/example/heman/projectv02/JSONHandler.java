package com.example.heman.projectv02;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by yashjain on 6/27/17.
 */

public class JSONHandler {

    public static Survey parseSurvey(JSONObject jSurvey){
        Gson gson=new Gson();
        return  gson.fromJson(jSurvey.toString(),Survey.class);
    }

    public static Question parseQuestion(JSONObject jQuestion){
        Gson gson=new Gson();
        return gson.fromJson(jQuestion.toString(),Question.class);
    }

    public static String getResponseJSONObject(Response response){
        Gson gson=new Gson();
        return gson.toJson(response);
    }
}
