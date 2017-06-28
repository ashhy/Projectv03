package com.example.heman.projectv02;

/**
 * Created by yashjain on 6/27/17.
 */

interface DbConstant{
    //DB CONFIG DETAILS
    String DB_NAME="surveydb";
    int DB_VERSION=1;

    //TABLE NAMES
    String TABLE_SURVEY="survey";
    String TABLE_QUESTION="question";
    String TABLE_RESPONSE="response";

    //COLUMN NAMES
        /*SURVEY TABLE*/
    String COL_SID="sid";
    String COL_TITLE="title";
    String COL_DESCRIPTION="description";
    String COL_LANGUAGE="language";
    String COL_TOTALQ="totalq";
    String COL_VERSION="version";
    /*QUESTION TABLE*/
    String COL_QNO="qno";
    String COL_QTEXT="qtext";
    String COL_MULTISELECT="multiselect";
    String COL_OPTIONS="otext";
    /*RESPONSE TABLE*/
    String COL_USERID="userid";
    String COL_LATITUDE="latitude";
    String COL_LONGITUDE="longitude";
    String COL_ALTITUDE="altitude";
    String COL_SYNCED="synced";
    String COL_RESULT="result";
    String COL_FNAME="fname";
    String COL_LNAME="lname";
    String COL_ADDRESS="address";
    String COL_AGE="age";
    String COL_RID="rid";


    //DATA TYPES
    String TEXT=" TEXT ";
    String INTEGER=" INTEGER ";
    String BOOLEAN=" BOOLEAN ";
    String REAL=" REAL ";
    String INTEGER_PRIMARY_AUTO= " INTEGER PRIMARY KEY AUTOINCREMENT ";

    //VALUES FOR SYNCED
    String SYNCED_TO_SERVER="syncedtoserver";
    String EXPORTED="exported";
    String OFFLINE="offline";

    //TABLE CREATE STATEMENTS
    String CREATE_SURVEY_TABLE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_SURVEY+" ("+
                    COL_SID+TEXT+","+
                    COL_TITLE+TEXT+","+
                    COL_DESCRIPTION+TEXT+","+
                    COL_LANGUAGE+TEXT+","+
                    COL_TOTALQ+INTEGER+","+
                    COL_VERSION+INTEGER+")";
    String CREATE_QUESTION_TABLE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_QUESTION+" ("+
                    COL_SID+TEXT+","+
                    COL_QNO+INTEGER+","+
                    COL_QTEXT+TEXT+","+
                    COL_MULTISELECT+BOOLEAN+","+ //BOOLEANS ARE STORED AS 0 AND 1
                    COL_LANGUAGE+TEXT+","+
                    COL_OPTIONS+TEXT+")";
    String CREATE_RESPONSE_TABLE=
            " CREATE TABLE IF NOT EXISTS "+TABLE_RESPONSE+" ("+
                    COL_RID+INTEGER_PRIMARY_AUTO+","+
                    COL_SID+TEXT+","+
                    COL_USERID+TEXT+","+
                    COL_LANGUAGE+TEXT+","+
                    COL_VERSION+INTEGER+","+
                    COL_LATITUDE+REAL+","+
                    COL_LONGITUDE+REAL+","+
                    COL_ALTITUDE+REAL+","+
                    COL_SYNCED+TEXT+","+
                    COL_RESULT+TEXT+","+
                    COL_FNAME+TEXT+","+
                    COL_LNAME+TEXT+","+
                    COL_ADDRESS+TEXT+","+
                    COL_AGE+INTEGER+")";
}

