package com.example.heman.projectv02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yashjain on 6/27/17.
 */

public class SurveyAdapter extends ArrayAdapter<Survey> {

    private ArrayList<Survey> surveyList;
    private Context context;
    private SurveyElementOnClickListener surveyElementOnClickListener;

    public interface SurveyElementOnClickListener {
        void onClick(Survey survey);
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView totalQ;
        TextView language;
        TextView version;
    }

    public SurveyAdapter(Context context, ArrayList<Survey> surveyList, SurveyAdapter.SurveyElementOnClickListener surveyElementOnClickListener) {
        super(context, R.layout.survey_list_element, surveyList);
        this.context = context;
        this.surveyList = surveyList;
        this.surveyElementOnClickListener = surveyElementOnClickListener;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Survey survey = (Survey) getItem(position);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.survey_list_element, parent, false);
        viewHolder.title = (TextView) convertView.findViewById(R.id.seTitle);
        viewHolder.description = (TextView) convertView.findViewById(R.id.seDescription);
        viewHolder.totalQ = (TextView) convertView.findViewById(R.id.seTotalQ);
        viewHolder.language = (TextView) convertView.findViewById(R.id.seLanguage);
        viewHolder.version = (TextView) convertView.findViewById(R.id.seVersion);
        viewHolder.title.setText(survey.getTitle());
        viewHolder.description.setText(survey.getDescription());
        viewHolder.totalQ.setText(getContext().getString(R.string.seTotalQ) + String.valueOf(survey.getTotalQuestions()));
        viewHolder.language.setText(getContext().getString(R.string.seLanguage) + survey.getLanguage());
        viewHolder.version.setText(getContext().getString(R.string.seVersion) + String.valueOf(survey.getVersion()));
        final View finalConvertView = convertView;
        View.OnClickListener vOnClick = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                surveyElementOnClickListener.onClick(getItem(position));
                //REMOVE THIS
                Snackbar.make(finalConvertView, "POSITION" + String.valueOf(position) + getItem(position).getTitle(), Snackbar.LENGTH_LONG).show();
            }
        };
        viewHolder.title.setOnClickListener(vOnClick);
        viewHolder.description.setOnClickListener(vOnClick);
        viewHolder.totalQ.setOnClickListener(vOnClick);
        viewHolder.language.setOnClickListener(vOnClick);
        viewHolder.version.setOnClickListener(vOnClick);
        return convertView;
    }
}
