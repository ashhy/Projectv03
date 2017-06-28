package com.example.heman.projectv02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashjain on 6/27/17.
 */


//TODO: SEND THIS OBJECT ON EVERY ACTIVITY CALL STARTING FROM SPLASH SCREEN::: OR MAKE CODE SUCH THAT IT CAN HANDLE ALL THAT
public class SurveyDbHandler implements DbConstant {
    private SurveyDbOpenHelper surveyDbOpenHelper;
    private SQLiteDatabase db;

    private static final String RESPONSE_RID="rId";
    private static final String RESPONSE_SYNCED="synced";


    public SurveyDbHandler(Context context) {
        surveyDbOpenHelper = new SurveyDbOpenHelper(context);
    }


    //CALL THIS IS SPLASHSCREEN
    public void setUpDb() {
        db = surveyDbOpenHelper.getWritableDatabase();
    }


    public long storeSurvey(Survey survey) {
        if (db == null) return -1;
        ContentValues values = new ContentValues();
        values.put(COL_SID, survey.getsId());
        values.put(COL_TITLE, survey.getTitle());
        values.put(COL_DESCRIPTION, survey.getDescription());
        values.put(COL_LANGUAGE, survey.getLanguage());
        values.put(COL_TOTALQ, survey.getTotalQuestions());
        values.put(COL_VERSION, survey.getVersion());
        return db.insertOrThrow(TABLE_SURVEY, null, values);
    }

    public long storeQuestion(Question question) {
        if (db == null) return -1;
        ContentValues values = new ContentValues();
        values.put(COL_SID, question.getsId());
        values.put(COL_QNO, question.getqNo());
        values.put(COL_QTEXT, question.getqText());
        values.put(COL_MULTISELECT, question.isMultiSelect());
        values.put(COL_LANGUAGE, question.getLanguage());
        values.put(COL_OPTIONS, question.getOptions().toString());
        return db.insertOrThrow(TABLE_QUESTION, null, values);
    }


    public long storeResponse(Response response) {
        if (db == null) return -1;
        ContentValues values = new ContentValues();
        values.put(COL_SID, response.getsId());
        values.put(COL_USERID, response.getUserId());
        values.put(COL_LANGUAGE, response.getLanguage());
        values.put(COL_VERSION, response.getVersion());
        values.put(COL_LATITUDE, response.getLatitude());
        values.put(COL_LONGITUDE, response.getLongitude());
        values.put(COL_ALTITUDE, response.getAltitude());
        values.put(COL_SYNCED, response.getSynced());
        values.put(COL_RESULT, response.getResult().toString());
        values.put(COL_FNAME, response.getfName());
        values.put(COL_LNAME, response.getlName());
        values.put(COL_ADDRESS, response.getAddress());
        values.put(COL_AGE, response.getAge());
        long retcode= db.insertOrThrow(TABLE_RESPONSE, null, values);
        Cursor cursor=db.rawQuery("Select MAX("+COL_RID+") FROM "+TABLE_RESPONSE,null);
        response.setrId(cursor.getInt(0));
        return retcode;
    }


    public List<Survey> getSurveyList(String language) {
        if (db == null) return null;
        String[] projection = {COL_SID, COL_TITLE, COL_DESCRIPTION, COL_LANGUAGE, COL_TOTALQ, COL_VERSION};
        String selection = COL_LANGUAGE + " = ?";
        Cursor cursor = db.query(TABLE_SURVEY, projection, selection, new String[]{language}, null, null, null);
        List<Survey> surveyList = new ArrayList<Survey>();
        while (cursor.moveToNext()) {
            Survey survey = new Survey();
            survey.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            survey.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)));
            survey.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
            survey.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            survey.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTALQ)));
            survey.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VERSION)));
            surveyList.add(survey);
        }
        return surveyList;
    }

    public List<Question> getQuestionsForSurvey(String language, String sId) {
        if (db == null) return null;
        String projection[] = {COL_SID, COL_QNO, COL_QTEXT, COL_MULTISELECT, COL_LANGUAGE, COL_OPTIONS};
        String selection = COL_LANGUAGE + " = ? AND " + COL_SID + " = ? ";
        String sortOrder = COL_QNO + " ASC ";
        Cursor cursor = db.query(TABLE_QUESTION, projection, selection, new String[]{language, sId}, null, null, sortOrder);
        List<Question> questionList = new ArrayList<Question>();
        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            question.setqNo(cursor.getInt(cursor.getColumnIndexOrThrow(COL_QNO)));
            question.setqText(cursor.getString(cursor.getColumnIndexOrThrow(COL_QTEXT)));
            question.setMultiSelect(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MULTISELECT)) == 1);
            question.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            try {
                question.setOptions(new JSONObject(cursor.getString(cursor.getColumnIndexOrThrow(COL_OPTIONS))));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("JSON EXCEPTION", ":PARSING OPTIONS" + e.getMessage());
            }
            questionList.add(question);
        }
        return questionList;
    }


    //TODO: GET RESPONSE RESULT AND SYNC WITH DB
    public int updateResponse(JSONArray responseArray){
        if(responseArray==null)return -1;
        int lenght=responseArray.length();
        for(int i=0;i<lenght;i++){
            try {
                JSONObject jsonObject = responseArray.getJSONObject(i);
                int rId = jsonObject.getInt(RESPONSE_RID);
                String synced = jsonObject.getString(RESPONSE_SYNCED);
                ContentValues values = new ContentValues();
                values.put(COL_SYNCED, SYNCED_TO_SERVER);
                String selection = COL_RID + " = ?";
                String[] selectionArgs = {String.valueOf(rId)};
                db.update(TABLE_RESPONSE, values, selection, selectionArgs);
            }
            catch (JSONException jsonException){
                Log.d("JSON EXCEPTION:","IN UPDATING RESPONSE FROM SERVER"+jsonException.getLocalizedMessage());
            }
        }
        return 1;
    }


    public List<Response> getUnSyncedResponse() {
        if (db == null) return null;
        String[] projection = {COL_RID,COL_SID, COL_USERID, COL_LANGUAGE, COL_VERSION, COL_LATITUDE, COL_LONGITUDE, COL_ALTITUDE,COL_SYNCED,COL_RESULT, COL_FNAME, COL_LNAME, COL_ADDRESS, COL_AGE};
        String selection = COL_SYNCED + " != ?";//TODO: THIS BOOLEAN CAN CAUSE TROUBLE AS ONLY STRING ARRAY IS PASSED IN SELECTION ARGUMENT
        Cursor cursor = db.query(TABLE_RESPONSE, projection, selection, new String[]{SYNCED_TO_SERVER}, null, null, null);
        List<Response> responseList = new ArrayList<Response>();
        while (cursor.moveToNext()) {
            Response response = new Response();
            response.setrId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_RID)));
            response.setsId(cursor.getString(cursor.getColumnIndexOrThrow(COL_SID)));
            response.setSynced(cursor.getString(cursor.getColumnIndexOrThrow(COL_SYNCED)));
            response.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COL_USERID)));
            response.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE)));
            response.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COL_VERSION)));
            response.setLatitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_LATITUDE)));//TODO: ERROR MAY OCCUR HERE
            response.setLongitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_LONGITUDE)));//TODO: ERROR MAY OCCUR HERE
            response.setAltitude(cursor.getFloat(cursor.getColumnIndexOrThrow(COL_ALTITUDE)));//TODO: ERROR MAY OCCUR HERE
            try {
                response.setResult(new JSONArray(cursor.getString(cursor.getColumnIndexOrThrow(COL_RESULT))));
            } catch (JSONException e) {
                Log.d("JSON EXCEPTION", ":PARSING RESPONSE" + e.getMessage());
            }
            response.setfName(cursor.getString(cursor.getColumnIndexOrThrow(COL_FNAME)));
            response.setlName(cursor.getString(cursor.getColumnIndexOrThrow(COL_LNAME)));
            response.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)));
            response.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE)));
            responseList.add(response);
        }
        return responseList;
    }

}