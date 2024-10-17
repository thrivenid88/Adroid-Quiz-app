package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<String> messages;  // List of messages

    // Constructor
    public ChatAdapter(Context context, List<String> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();  // Number of messages
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);  // Get message at a specific position
    }

    @Override
    public long getItemId(int position) {
        return position;  // Position as ID (no specific ID needed here)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If view is not inflated, inflate it
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_message_item, parent, false);
        }

        // Get the message at the current position
        String message = messages.get(position);

        // Find the TextView in the layout and set the message
        TextView messageTextView = convertView.findViewById(R.id.messageTextView);
        messageTextView.setText(message);

        return convertView;
    }
}
