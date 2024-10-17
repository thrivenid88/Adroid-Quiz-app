package com.example.quizapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class QuestionDetailsFragment extends Fragment {

    private EditText editTextQuestion, editTextOptions, editTextAnswer, editTexttype,editTextLevel;
    private Button buttonPost, buttonDiscard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmet_question_details, container, false);

        // Initialize UI elements
        editTextQuestion = view.findViewById(R.id.editTextQuestion);
        editTextOptions = view.findViewById(R.id.editTextOptions);
        editTextAnswer = view.findViewById(R.id.editTextAnswer);
        editTextLevel = view.findViewById(R.id.editTextLevel);
        editTexttype = view.findViewById(R.id.editTexttype);
        buttonPost = view.findViewById(R.id.buttonPost);
        buttonDiscard = view.findViewById(R.id.buttonDiscard);

        // Set button listeners
        buttonPost.setOnClickListener(v -> postQuestion());
        buttonDiscard.setOnClickListener(v -> clearInputFields());

        return view;
    }

    private void postQuestion() {
        String question = editTextQuestion.getText().toString().trim();
        String options = editTextOptions.getText().toString().trim();
        String answer = editTextAnswer.getText().toString().trim();
        String level = editTextLevel.getText().toString().trim();
        String type = editTextLevel.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(question) || TextUtils.isEmpty(options) || TextUtils.isEmpty(answer) || TextUtils.isEmpty(level)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new question object (same logic as before)
        Date datePosted = new Date();
        Question newQuestion = new Question(question, options, answer, level, type, datePosted);

        // Optionally pass data back or handle posting logic here
        Toast.makeText(getContext(), "Question posted successfully", Toast.LENGTH_SHORT).show();

        // Clear input fields after posting
        clearInputFields();

        // Navigate back to the previous fragment
        getParentFragmentManager().popBackStack();
    }

    private void clearInputFields() {
        editTextQuestion.setText("");
        editTextOptions.setText("");
        editTextAnswer.setText("");
        editTextLevel.setText("");
        editTextLevel.setText("");
    }
}
