package com.example.quizapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostQuestionFragment extends Fragment {

    private EditText editTextStartDate, editTextEndDate;
    private Button buttonPost, buttonDiscard, buttonFilterDateRange;
    private ListView listViewPostedQuestions;
    private List<Question> postedQuestionsList;
    private QuestionAdapter questionAdapter;
    private Date startDate, endDate;

    public static final int POST_QUESTION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_post_question, container, false);

        // Initialize UI elements
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);
        buttonPost = view.findViewById(R.id.buttonPost);
        buttonFilterDateRange = view.findViewById(R.id.buttonFilterDateRange);
        listViewPostedQuestions = view.findViewById(R.id.listViewPostedQuestions);

        // Initialize list to store posted questions
        postedQuestionsList = new ArrayList<>();

        // Set up the adapter to display posted questions
        questionAdapter = new QuestionAdapter(getContext(), postedQuestionsList);
        listViewPostedQuestions.setAdapter(questionAdapter);

        // Set button listeners
        buttonPost.setOnClickListener(v -> openPostQuestionActivity());
        editTextStartDate.setOnClickListener(v -> showDatePickerDialog(true));
        editTextEndDate.setOnClickListener(v -> showDatePickerDialog(false));

        return view;
    }


    private void openPostQuestionActivity() {
        // Navigate to the new activity when the button is clicked
        Intent intent = new Intent(getActivity(), PostQuestionActivity.class);
        startActivityForResult(intent, POST_QUESTION_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_QUESTION_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                // Retrieve the new question data from the intent
                String questionText = data.getStringExtra("questionText");
                String options = data.getStringExtra("options");
                String answer = data.getStringExtra("answer");
                String level = data.getStringExtra("level");
                String type = data.getStringExtra("type");
//                int category = data.getIntExtra("someIntParameter", 0); // Add this line
                String dateString = data.getStringExtra("date");
                Date date = null;
                if (level != null && type != null && dateString != null) {
                    // Update your fragment with the data
                    // For example, update TextViews, Spinners, or store the data in your ViewModel
//                    Toast.makeText(getActivity(), "Level: " + level + "\nType: " + type + "\nDate: " + dateString, Toast.LENGTH_SHORT).show();
                }
//                int points = data.getIntExtra("points", 1);
                if(dateString!=null && !dateString.isEmpty()) {
                    // Parse dateString to Date object
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    date=new Date();
                    Log.e("PostQuestionFragment","Date string is empty or null");

                }
                Log.d("PostQuestionFragment", "Level received: " + level);
                // Create a new Question object
                Question newQuestion = new Question(questionText, options, answer, level, type, date);


                // Insert the new question into the list
                insertPostedQuestion(newQuestion);
            }
        }
    }

    public void insertPostedQuestion(Question newQuestion) {
        // Add the new question to the list
        postedQuestionsList.add(newQuestion);
        // Notify the adapter that the data set has changed
        questionAdapter.notifyDataSetChanged();
    }

    private void showDatePickerDialog(final boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    Date selectedDate = calendar.getTime();
                    if (isStartDate) {
                        startDate = selectedDate;
                        editTextStartDate.setText(dateFormat.format(selectedDate));
                    } else {
                        endDate = selectedDate;
                        editTextEndDate.setText(dateFormat.format(selectedDate));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
