package com.example.quizapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private EditText messageEditText;
    private Button sendButton, createPollButton;
    private ListView chatListView;
    private ArrayList<String> chatMessages;
    private ChatAdapter chatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Initialize views
        messageEditText = view.findViewById(R.id.message);
        sendButton = view.findViewById(R.id.sendButton);
        createPollButton = view.findViewById(R.id.createPollButton);
        chatListView = view.findViewById(R.id.chatListView);

        // Initialize chat messages
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(getContext(), chatMessages);
        chatListView.setAdapter(chatAdapter);

        // Send message logic
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(message)) {
                chatMessages.add("You: " + message);
                chatAdapter.notifyDataSetChanged();
                messageEditText.setText("");
            } else {
                Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        // Create poll logic
        createPollButton.setOnClickListener(v -> showCreatePollDialog());

        return view;
    }

    // Method to show the poll creation dialog
    private void showCreatePollDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_poll, null);
        builder.setView(dialogView);

        EditText questionEditText = dialogView.findViewById(R.id.questionEditText);
        EditText option1EditText = dialogView.findViewById(R.id.option1EditText);
        EditText option2EditText = dialogView.findViewById(R.id.option2EditText);
        EditText option3EditText = dialogView.findViewById(R.id.option3EditText);
        EditText option4EditText = dialogView.findViewById(R.id.option4EditText);
        EditText correctAnswerEditText = dialogView.findViewById(R.id.correctAnswerEditText);
        Button createPollButton = dialogView.findViewById(R.id.createPollButton);

        AlertDialog dialog = builder.create();
        dialog.show();


        createPollButton.setOnClickListener(v -> {
            String question = questionEditText.getText().toString().trim();
            String option1 = option1EditText.getText().toString().trim();
            String option2 = option2EditText.getText().toString().trim();
            String option3 = option3EditText.getText().toString().trim();
            String option4 = option4EditText.getText().toString().trim();
            String correctAnswer = correctAnswerEditText.getText().toString().trim();

            if (!TextUtils.isEmpty(question) && !TextUtils.isEmpty(option1) && !TextUtils.isEmpty(option2) && !TextUtils.isEmpty(correctAnswer)) {
                String pollMessage = "Poll: " + question + "\n1) " + option1 + "\n2) " + option2 + "\n3) " + option3 + "\n4) " + option4 + "\nCorrect: " + correctAnswer;
                chatMessages.add(pollMessage);
                chatAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
