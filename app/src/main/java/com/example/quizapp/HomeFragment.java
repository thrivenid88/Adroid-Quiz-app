package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView welcomeTextView;
    private TextView scoreTextView;
    private TextView currentStreakTextView;
    private TextView highestStreakTextView;
    private TextView avgTimeSpentTextView;
    private Button openDictionaryButton;
    private Button addWordButton;

    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize TextViews
        welcomeTextView = view.findViewById(R.id.welcome_text);
        scoreTextView = view.findViewById(R.id.scores_text);
        currentStreakTextView = view.findViewById(R.id.current_streak);
        highestStreakTextView = view.findViewById(R.id.highest_streak);
        avgTimeSpentTextView = view.findViewById(R.id.avg_time_spent);

        // Initialize Buttons
        openDictionaryButton = view.findViewById(R.id.open_dictionary_button);
        addWordButton = view.findViewById(R.id.add_word_button);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(getActivity());

        // Set welcome note
        setWelcomeNote();

        // Fetch and set score and statistics
        fetchScoresAndStats();

        // Set up vocabulary levels
        setVocabularyLevelActions(view);

        // Set up dictionary actions
        setupDictionaryActions();

        return view;
    }

    private void setWelcomeNote() {
        Context context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "User");
            welcomeTextView.setText(getString(R.string.welcome_message, username));
        } else {
            welcomeTextView.setText(getString(R.string.welcome_message, "User"));
        }
    }

    private void fetchScoresAndStats() {
        // Fetch score and statistics from the database
        int score = 150; // Example score from the database
        int currentStreak = 5; // Example current streak from the database
        int highestStreak = 10; // Example highest streak from the database
        String avgTimeSpent = "10 min"; // Example avg time spent from the database

        scoreTextView.setText(getString(R.string.scores_text, score));
        currentStreakTextView.setText(getString(R.string.current_streak, currentStreak));
        highestStreakTextView.setText(getString(R.string.highest_streak, highestStreak));
        avgTimeSpentTextView.setText(getString(R.string.avg_time_spent, avgTimeSpent));
    }

    private void setVocabularyLevelActions(View view) {
        // Set up buttons for each vocabulary level
        view.findViewById(R.id.button_a1).setOnClickListener(v -> openVocabularyList("A1"));
        view.findViewById(R.id.button_a2).setOnClickListener(v -> openVocabularyList("A2"));
        view.findViewById(R.id.button_b1).setOnClickListener(v -> openVocabularyList("B1"));
        view.findViewById(R.id.button_b2).setOnClickListener(v -> openVocabularyList("B2"));
        view.findViewById(R.id.button_c1).setOnClickListener(v -> openVocabularyList("C1"));
        view.findViewById(R.id.button_c2).setOnClickListener(v -> openVocabularyList("C2"));
    }

    private void openVocabularyList(String level) {
        Intent intent = new Intent(getActivity(), VocabularyListActivity.class);
        intent.putExtra("level", level);  // Pass the selected vocabulary level to the activity
        startActivity(intent);
    }

    private void setupDictionaryActions() {
        // Open dictionary button action
        openDictionaryButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DictionaryActivity.class);
            startActivity(intent);
        });

        // Add word button action
        addWordButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddWordActivity.class);
            startActivity(intent);
        });
    }
}
