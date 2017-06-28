package com.example.heman.projectv02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by yashjain on 6/27/17.
 */

public class SurveyDbOpenHelper extends SQLiteOpenHelper {



    public SurveyDbOpenHelper(Context context) {
        super(context, DbConstant.DB_NAME, null, DbConstant.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstant.CREATE_SURVEY_TABLE);
        db.execSQL(DbConstant.CREATE_QUESTION_TABLE);
        db.execSQL(DbConstant.CREATE_RESPONSE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
