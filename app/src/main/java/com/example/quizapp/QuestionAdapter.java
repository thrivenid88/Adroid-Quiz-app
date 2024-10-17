package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class QuestionAdapter extends ArrayAdapter<Question> {

    private Context context;
    private List<Question> questions;

    public QuestionAdapter(Context context, List<Question> questions) {
        super(context, 0, questions);
        this.context = context;
        this.questions = questions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the question object for this position
        Question question = getItem(position);

        // Check if an existing view is being reused, otherwise inflate a new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_question, parent, false);
        }

        // Lookup views to populate data
        TextView textViewQuestion = convertView.findViewById(R.id.textViewQuestion);
        TextView textViewOptions = convertView.findViewById(R.id.textViewOptions);
        TextView textViewAnswer = convertView.findViewById(R.id.textViewanswer);
        TextView textViewLevel = convertView.findViewById(R.id.textViewlevel);
        TextView textViewType = convertView.findViewById(R.id.textViewtype);
        TextView textViewDate = convertView.findViewById(R.id.textViewdate);

        // Populate the data into the template view
        textViewQuestion.setText(question.getQuestionText());
        textViewOptions.setText("Options: " + question.getOptions());
        textViewAnswer.setText("Answer: " + question.getAnswer());
        textViewLevel.setText("Level: " + question.getLevel());
        textViewType.setText("Type: " + question.getType());

        // Format the date to a readable format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        textViewDate.setText("Date: " + dateFormat.format(question.getDate()));

        // Return the completed view to render on screen
        return convertView;
    }


//
//    // Helper method to get the index of the level in the array
//    private int getLevelIndex(String level) {
//        String[] levels = context.getResources().getStringArray(R.array.level_array);
//        for (int i = 0; i < levels.length; i++) {
//            if (levels[i].equalsIgnoreCase(level)) {
//                return i;
//            }
//        }
//        return -1; // If the level is not found
//    }
//
//    // Helper method to get the index of the question type in the array
//    private int getTypeIndex(String type) {
//        String[] types = context.getResources().getStringArray(R.array.question_type_array);
//        for (int i = 0; i < types.length; i++) {
//            if (types[i].equalsIgnoreCase(type)) {
//                return i;
//            }
//        }
//        return -1; // If the type is not found
//    }
//
//    // ViewHolder class to improve performance by reducing findViewById calls
//    static class ViewHolder {
//        TextView textViewQuestion;
//        TextView textOptions;
//        TextView textAnswer;
//        Spinner spinnerLevel;
//        Spinner spinnerType;
//    }
}
