package com.example.heman.projectv02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SurveyListActivity extends AppCompatActivity implements SurveyAdapter.SurveyElementOnClickListener {

    ArrayList<Survey> surveyList;
    ListView listView;
    private static SurveyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        listView = (ListView) findViewById(R.id.slSurveyList);
        surveyList = new ArrayList<Survey>();
        for (int i = 0; i < 10; i++) {
            Survey survey = new Survey();
            survey.setsId(String.valueOf(i));
            survey.setTitle("Title " + String.valueOf(i));
            survey.setDescription("Desctiption " + String.valueOf(i));
            survey.setTotalQuestions(i);
            survey.setLanguage(String.valueOf(i));
            survey.setVersion(i);
            surveyList.add(survey);
        }
        adapter = new SurveyAdapter(this, surveyList, this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.FinalPage:
                startActivity(new Intent(this, FinalPage.class));
                break;
        }
        return true;
    }

    @Override
    public void onClick(Survey survey) {
        Log.d("SURVEY CLICKED", survey.getTitle());
        Toast.makeText(this, survey.getTitle(), Toast.LENGTH_LONG).show();
    }
}
