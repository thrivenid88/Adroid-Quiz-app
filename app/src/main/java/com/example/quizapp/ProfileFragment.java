package com.example.quizapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView ProfileTextView, UpdatePassword, RePassword, ResetPassword;
    private ImageView ProfileImage;
    private EditText Username, MobileNo, EmailId, ReEnter;
    private Button logoutButton, linkAccount, cancelButton, UpdateButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        ProfileImage = view.findViewById(R.id.ivProfileImage);
        Username = view.findViewById(R.id.ed_Username);
        MobileNo = view.findViewById(R.id.ed_Mobile);
        EmailId = view.findViewById(R.id.ed_Email);
        UpdatePassword = view.findViewById(R.id.tv_UpdatePassword);
        RePassword = view.findViewById(R.id.RE_password);
        ReEnter = view.findViewById(R.id.RE_edit_text);
        logoutButton = view.findViewById(R.id.btnLogout);
        linkAccount = view.findViewById(R.id.btnLink);
        cancelButton = view.findViewById(R.id.cancel_button);
        UpdateButton = view.findViewById(R.id.update_button);
        ResetPassword = view.findViewById(R.id.tv_ResetPassword);


        // Update Password Button Logic
        UpdatePassword.setOnClickListener(v -> showUpdatePasswordDialog());

        // Reset Password Button Logic
        ResetPassword.setOnClickListener(v -> {
            // Reset password logic (e.g., send reset link to email)
            Toast.makeText(getContext(), "Reset link sent to your email", Toast.LENGTH_SHORT).show();
        });

        // Logout Button Logic
        logoutButton.setOnClickListener(v -> {
            // Log out logic (e.g., clear session and redirect to login screen)
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    // Method to show the Update Password Dialog
    private void showUpdatePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_update_password, null);
        builder.setView(dialogView);

        EditText oldPasswordEditText = dialogView.findViewById(R.id.old_edit_text);
        EditText newPasswordEditText = dialogView.findViewById(R.id.new_edit_text);
        EditText reEnterPasswordEditText = dialogView.findViewById(R.id.RE_edit_text);
        Button cancelBtn = dialogView.findViewById(R.id.cancel_button);
        Button updateBtn = dialogView.findViewById(R.id.update_button);
        ImageView CloseIcon=dialogView.findViewById(R.id.close_icon);


        AlertDialog alertDialog = builder.create();

        // Update Password Button Logic
        updateBtn.setOnClickListener(v -> {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String reEnterPassword = reEnterPasswordEditText.getText().toString();

            if (oldPassword.isEmpty() || newPassword.isEmpty() || reEnterPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all password fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(reEnterPassword)) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update password logic here (e.g., save to database)
            Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        // Cancel Button Logic to clear the fields
        cancelBtn.setOnClickListener(v -> {
            oldPasswordEditText.setText("");
            newPasswordEditText.setText("");
            reEnterPasswordEditText.setText("");
        });
        CloseIcon.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }
}
