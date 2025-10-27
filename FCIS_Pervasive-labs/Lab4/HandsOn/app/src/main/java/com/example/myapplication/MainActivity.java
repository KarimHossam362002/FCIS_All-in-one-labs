package com.example.myapplication;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast; // Added for feedback

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1. Implement the communication interface from the DialogFragment
public class MainActivity extends AppCompatActivity implements EditNameDialogFragment.EditNameDialogListener {

    private List<String> contacts;
    private ArrayAdapter<String> adapter;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myList = findViewById(R.id.list1);

    
        String[] contactsArray = getResources().getStringArray(R.array.T_view);
        contacts = new ArrayList<>(Arrays.asList(contactsArray));

        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, contacts);
        myList.setAdapter(adapter);
        registerForContextMenu(myList);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.edit) {

            editItem(info.position);
            return true;
        } else if (item.getItemId() == R.id.delete) {
            deleteItem(info.position);
            return true;
        }
        return super.onContextItemSelected(item);
    }


    private void editItem(int position) {
        String currentName = contacts.get(position);


        EditNameDialogFragment dialog = EditNameDialogFragment.newInstance(currentName, position);


        dialog.show(getSupportFragmentManager(), "EditNameDialog");
    }

    private void deleteItem(int position) {

        contacts.remove(position);

        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
    }


    /**
     * This method is called by the EditNameDialogFragment when the user presses 'SAVE'.
     * @param position The list index of the item that was edited.
     * @param newName The new name typed by the user.
     */
    @Override
    public void onFinishEditDialog(int position, String newName) {

        contacts.set(position, newName);


        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Name updated to: " + newName, Toast.LENGTH_SHORT).show();
    }
}