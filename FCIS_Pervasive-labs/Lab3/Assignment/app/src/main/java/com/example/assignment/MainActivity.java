package com.example.assignment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.widget.ListView;
import androidx.core.view.WindowInsetsCompat;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    int[] images = {R.drawable.twitter, R.drawable.youtube, R.drawable.facebook, R.drawable.pinterest, R.drawable.instagram};
    String[] titles = {"Title 1", "Title 2", "Title 3", "Title 4", "Title 5"};
    String[] subtitles = {"Sub Title 1", "Sub Title 2", "Sub Title 3", "Sub Title 4", "Sub Title 5"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", images[i]);
            item.put("title", titles[i]);
            item.put("subtitle", subtitles[i]);
            data.add(item);
        }

        String[] from = {"image", "title", "subtitle"};
        int[] to = {R.id.icon, R.id.title, R.id.subtitle};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_item, from, to);

        listView.setAdapter(adapter);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}