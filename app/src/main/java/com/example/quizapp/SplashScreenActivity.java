package com.example.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Duration of the splash screen in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Use a handler to delay the start of the next activity
        new Handler().postDelayed(() -> {
            // Check if user is logged in
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

            Intent intent;
            if (isLoggedIn) {
                // Redirect to MainActivity if logged in
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            } else {
                // Redirect to LoginActivity if not logged in
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish(); // Close SplashScreenActivity
        }, SPLASH_DISPLAY_LENGTH);
    }
}
