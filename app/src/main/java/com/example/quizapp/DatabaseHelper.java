package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";  // SharedPreferences file name
    private static final String DATABASE_NAME = "QuizApp.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    // New table for sentences
    private static final String TABLE_SENTENCES = "sentences";
    private static final String COLUMN_SENTENCE_ID = "id";
    private static final String COLUMN_SENTENCE_TEXT = "sentence";
    private static final String COLUMN_SENTENCE_DATE = "date";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String COLUMN_QUESTION_ID = "id";
    private static final String COLUMN_QUESTION_TEXT = "question";
    private static final String COLUMN_QUESTION_OPTIONS = "options";
    private static final String COLUMN_QUESTION_ANSWER = "answer";
    private static final String COLUMN_QUESTION_CATEGORY = "category";
    private static final String COLUMN_QUESTION_LEVEL = "level";
    private static final String COLUMN_QUESTION_POINTS = "points";
    private static final String COLUMN_QUESTION_DATE = "date_posted";

    private Context context;

    // Constructor that accepts Context and initializes SharedPreferences
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context = context;  // Assign context
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create users table
        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);

        // Create sentences table
        String createSentenceTable = "CREATE TABLE " + TABLE_SENTENCES + " (" +
                COLUMN_SENTENCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SENTENCE_TEXT + " TEXT, " +
                COLUMN_SENTENCE_DATE + " TEXT)";
        db.execSQL(createSentenceTable);

        // Insert some default sentences (optional)
        insertSentence(db, "Welcome to your daily quiz!", "2024-09-10");
        insertSentence(db, "Learning is a lifelong journey.", "2024-09-11");
        insertSentence(db, "Learning is fun!", "2024-09-12");
        insertSentence(db, "Practice makes perfect.", "2024-09-13");
        insertSentence(db, "Stay curious.", "2024-09-14");


        // Create questions table
        String createQuestionTable = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +  // User who posted the question
                COLUMN_QUESTION_TEXT + " TEXT, " +  // Question text
                COLUMN_QUESTION_OPTIONS + " TEXT, " +  // Options (for MCQ), stored as comma-separated string
                COLUMN_QUESTION_ANSWER + " TEXT, " +  // Correct answer(s)
                COLUMN_QUESTION_CATEGORY + " TEXT, " +  // Category: quiz, query, study_material
                COLUMN_QUESTION_LEVEL + " TEXT, " +  // Level: A1, A2, etc.
                COLUMN_QUESTION_POINTS + " INTEGER, " +  // Points for the question
                COLUMN_QUESTION_DATE + " TEXT)";  // Date when the question was posted
        db.execSQL(createQuestionTable);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENTENCES);
        onCreate(db);
    }

    // Method to insert user into the database
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // If insert is successful, result will not be -1
    }

    // Method to insert a sentence into the database
    public boolean insertSentence(SQLiteDatabase db, String sentence, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SENTENCE_TEXT, sentence);
        contentValues.put(COLUMN_SENTENCE_DATE, date);
        long result = db.insert(TABLE_SENTENCES, null, contentValues);
        return result != -1; // If insert is successful, result will not be -1
    }

    // Method to fetch the sentence of the day based on the date
    public String getSentenceOfTheDay(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String sentence = null;

        try {
            // Query the database
            cursor = db.query(
                    TABLE_SENTENCES,    // Table name
                    new String[]{COLUMN_SENTENCE_TEXT},  // Columns to return
                    COLUMN_SENTENCE_DATE + "=?",    // WHERE clause
                    new String[]{date},    // Arguments for WHERE clause
                    null, null, null);

            // Check if the cursor is not null and has data
            if (cursor != null && cursor.moveToFirst()) {
                sentence = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENTENCE_TEXT));
            } else {
                Log.d("DatabaseHelper", "No sentence found for the date: " + date);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching sentence of the day: " + e.getMessage());
        } finally {
            // Always close the cursor to avoid memory leaks
            if (cursor != null) {
                cursor.close();
            }
        }

        return sentence;
    }


    // Method to check if a username already exists in the database
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to check if a user exists with the given username and password
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        boolean userExists = (cursor.getCount() > 0);

        Log.d("DatabaseHelper", "Username: " + username + ", Password: " + password + ", Exists: " + userExists);

        cursor.close();
        return userExists;
    }

    // Method to check if the user is logged in
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Method to log all users in the database
    public void logAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        // Get column indices for safety
        int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
        int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);

        if (usernameIndex == -1 || passwordIndex == -1) {
            Log.e("DatabaseHelper", "Column names do not match expected values.");
            cursor.close();
            return;
        }

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(usernameIndex);
                String password = cursor.getString(passwordIndex);
                Log.d("DatabaseHelper", "User: " + username + ", Password: " + password);
            } while (cursor.moveToNext());
        } else {
            Log.d("DatabaseHelper", "No users found in the database.");
        }

        cursor.close();
    }

    public boolean insertPostedQuestion(String username, String question, String options, String answer, String category, String level, int points, String datePosted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);  // Who posted the question
        values.put(COLUMN_QUESTION_TEXT, question);  // The question text
        values.put(COLUMN_QUESTION_OPTIONS, options);  // Options for MCQs, stored as a comma-separated string
        values.put(COLUMN_QUESTION_ANSWER, answer);  // Correct answer(s)
        values.put(COLUMN_QUESTION_CATEGORY, category);  // Category of the question (quiz, query, study_material)
        values.put(COLUMN_QUESTION_LEVEL, level);  // Question level (A1, A2, etc.)
        values.put(COLUMN_QUESTION_POINTS, points);  // Points for the question
        values.put(COLUMN_QUESTION_DATE, datePosted);  // Date when the question was posted

        long result = db.insert(TABLE_QUESTIONS, null, values);
        db.close();

        return result != -1;  // Return true if insert is successful
    }
    // Add this method to DatabaseHelper


    // Method to fetch questions based on the level
    // Method to fetch all posted questions
    public Cursor getAllPostedQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch all questions from the questions table
        Cursor cursor = db.query(TABLE_QUESTIONS,
                new String[]{COLUMN_QUESTION_ID, COLUMN_USERNAME, COLUMN_QUESTION_TEXT, COLUMN_QUESTION_OPTIONS, COLUMN_QUESTION_ANSWER, COLUMN_QUESTION_CATEGORY, COLUMN_QUESTION_LEVEL, COLUMN_QUESTION_POINTS, COLUMN_QUESTION_DATE},
                null, null, null, null, COLUMN_QUESTION_DATE + " DESC");  // Sorting by date in descending order

        return cursor;  // Return the cursor containing the results
    }
}
