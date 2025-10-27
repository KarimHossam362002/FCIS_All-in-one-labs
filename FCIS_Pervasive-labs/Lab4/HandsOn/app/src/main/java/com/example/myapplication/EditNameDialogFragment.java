package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditNameDialogFragment extends DialogFragment {

    private static final String ARG_NAME = "current_name";
    private static final String ARG_POSITION = "item_position";

    private EditText editTextName;
    private Button btnConfirm;
    private Button btnCancel;

    // Interface for the Activity to implement
    public interface EditNameDialogListener {
        void onFinishEditDialog(int position, String newName);
    }

    // Factory method to create an instance and pass arguments
    public static EditNameDialogFragment newInstance(String name, int position) {
        EditNameDialogFragment fragment = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the XML layout you created previously (fragment_edit_name.xml)
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        editTextName = view.findViewById(R.id.edit_text_name);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnCancel = view.findViewById(R.id.btn_cancel);

        // Pre-fill the EditText with the current name
        String currentName = getArguments().getString(ARG_NAME);
        editTextName.setText(currentName);

        // Set up button listeners
        btnConfirm.setOnClickListener(v -> handleConfirm());
        btnCancel.setOnClickListener(v -> dismiss()); // Close the dialog on cancel

        return view;
    }

    private void handleConfirm() {
        String newName = editTextName.getText().toString().trim();
        int position = getArguments().getInt(ARG_POSITION);

        // 1. Get the listener (the MainActivity)
        EditNameDialogListener listener = (EditNameDialogListener) getActivity();

        // 2. Send the edited data back
        if (listener != null) {
            listener.onFinishEditDialog(position, newName);
        }

        // 3. Close the dialog
        dismiss();
    }
}