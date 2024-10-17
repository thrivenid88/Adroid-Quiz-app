package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class VocabularyListActivity extends AppCompatActivity {

    TextView vocabularyLevel;
    ListView vocabularyList;
    DatabaseHelper db;
    String level;  // Vocabulary level (e.g., A1, A2, etc.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);

        vocabularyLevel = findViewById(R.id.vocabulary_level);
        vocabularyList = findViewById(R.id.vocabulary_list);

        db = new DatabaseHelper(this);  // Initialize the database helper
        // Get the selected level from the Intent
        level = getIntent().getStringExtra("level");

        // Use getString with a placeholder from the resources
        vocabularyLevel.setText(getString(R.string.vocabulary_level, level));

        // Fetch and display the vocabulary list for the selected level
        ArrayList<String> words = fetchVocabularyForLevel(level);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        vocabularyList.setAdapter(adapter);

        // Replace anonymous inner class with lambda for the OnClickListener
        vocabularyLevel.setOnClickListener(v -> openVocabularyList("A1"));  // Change "A1" to desired logic
    }

    // Fetch vocabulary words for the selected level from the database
    private ArrayList<String> fetchVocabularyForLevel(String level) {
        // Implement your database logic here to fetch words for the given level
        // For now, return a static list as an example
        ArrayList<String> words = new ArrayList<>();
        if (level.equals("A1")) {
            words.add("Apple");
            words.add("Ball");
            words.add("Cat");
        } else if (level.equals("A2")) {
            words.add("Dog");
            words.add("Elephant");
            words.add("Fan");
        }
        // Continue adding words for other levels...
        return words;
    }

    // Method to open the VocabularyListActivity with a selected level
    private void openVocabularyList(String level) {
        Intent intent = new Intent(this, VocabularyListActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }
}
