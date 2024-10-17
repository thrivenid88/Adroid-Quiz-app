package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostQuestionActivity extends AppCompatActivity {
    private EditText editTextQuestion, editTextOptions, editTextAnswer;
    private Spinner spinnerLevel, spinnerType;
    private Button buttonPost, buttonDiscard;
    private ListView listViewPostedQuestions;
    private List<Question> postedQuestionsList;
    private QuestionAdapter questionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);

        // Initialize the UI elements
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextOptions = findViewById(R.id.editTextOptions);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        spinnerLevel = findViewById(R.id.level_array);
        spinnerType = findViewById(R.id.question_type_array);
        buttonPost = findViewById(R.id.buttonPost);
        buttonDiscard = findViewById(R.id.buttonDiscard);

        // Initialize list to store posted questions
        postedQuestionsList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, postedQuestionsList);
        listViewPostedQuestions = findViewById(R.id.listViewPostedQuestions);
        listViewPostedQuestions.setAdapter(questionAdapter);

        // Populate Spinner for Level
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this,
                R.array.level_array, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLevel.setAdapter(levelAdapter);

        // Populate Spinner for Question Type
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.question_type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerType.setAdapter(typeAdapter);

        // Handle the Post button click
        // Handle the Post button click
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user inputs
                String question = editTextQuestion.getText().toString().trim();
                String options = editTextOptions.getText().toString().trim();
                String answer = editTextAnswer.getText().toString().trim(); // Multiple answers separated by commas
                String level = spinnerLevel.getSelectedItem().toString().trim();
                String category = spinnerType.getSelectedItem().toString().trim();
                String questionType = spinnerType.getSelectedItem().toString().trim();
                int points = 1;  // Example of setting default points
                String datePosted = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Basic validation
                if (question.isEmpty() || options.isEmpty() || answer.isEmpty() || level.isEmpty()) {
                    Toast.makeText(PostQuestionActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Split options by new lines
                String[] optionsArray = options.split("\n");  // Assuming options are entered on new lines
                if (optionsArray.length != 4) {
                    Toast.makeText(PostQuestionActivity.this, "Please provide exactly 4 options", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Format the options
                String formattedOptions = "a) " + optionsArray[0].trim() + "\n" +
                        "b) " + optionsArray[1].trim() + "\n" +
                        "c) " + optionsArray[2].trim() + "\n" +
                        "d) " + optionsArray[3].trim();

                // Split the answers by commas
                String[] answersArray = answer.split(",");  // Multiple correct answers separated by commas
                List<String> formattedAnswers = new ArrayList<>();

                // Check which options match the answers
                for (String ans : answersArray) {
                    ans = ans.trim();  // Trim each answer to avoid white spaces

                    if (ans.equalsIgnoreCase(optionsArray[0].trim())) {
                        formattedAnswers.add("a) " + optionsArray[0].trim());
                    } else if (ans.equalsIgnoreCase(optionsArray[1].trim())) {
                        formattedAnswers.add("b) " + optionsArray[1].trim());
                    } else if (ans.equalsIgnoreCase(optionsArray[2].trim())) {
                        formattedAnswers.add("c) " + optionsArray[2].trim());
                    } else if (ans.equalsIgnoreCase(optionsArray[3].trim())) {
                        formattedAnswers.add("d) " + optionsArray[3].trim());
                    } else {
                        Toast.makeText(PostQuestionActivity.this, "Each answer must match one of the options", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Join the formatted answers into a single string
                String formattedAnswer = String.join(", ", formattedAnswers);

                // Insert the question into the database with the formatted answer
                DatabaseHelper db = new DatabaseHelper(PostQuestionActivity.this);
                boolean isSaved = db.insertPostedQuestion("<username>", question, formattedOptions, formattedAnswer, category, level, points, datePosted);

//                if (isSaved) {
//                    Toast.makeText(PostQuestionActivity.this, "Question posted and saved successfully!", Toast.LENGTH_SHORT).show();
//                    // Clear the input fields after posting
//                    clearInputFields();
//                } else {
//                    Toast.makeText(PostQuestionActivity.this, "Failed to save question.", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                // Create a new Question object with the formatted answers
                Question newQuestion = new Question(question, formattedOptions, formattedAnswer, level, questionType, new Date());
                if (isSaved) {
                    Toast.makeText(PostQuestionActivity.this, "Question posted and saved successfully!", Toast.LENGTH_SHORT).show();
                    // Clear the input fields after posting
                    clearInputFields();

                    // Prepare the data to send back to PostQuestionFragment
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("questionText", question);
                    resultIntent.putExtra("options", formattedOptions);
                    resultIntent.putExtra("answer", formattedAnswer);  // Send the formatted answers back
                    resultIntent.putExtra("level", level);
                    resultIntent.putExtra("type", questionType);
                    resultIntent.putExtra("date", datePosted);

                    // Set result and finish activity
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(PostQuestionActivity.this, "Failed to save question.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        // Handle the Discard button click
        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the input fields if discard is clicked
                clearInputFields();
            }
        });
    }

    // Method to clear input fields
    private void clearInputFields() {
        editTextQuestion.setText("");
        editTextOptions.setText("");
        editTextAnswer.setText("");
        spinnerLevel.setSelection(0);
        spinnerType.setSelection(0);
    }
}

