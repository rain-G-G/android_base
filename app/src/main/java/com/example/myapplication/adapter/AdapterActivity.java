package com.example.myapplication.adapter;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        /*String[] strings = {"11111111111", "222222222222", "3333333333", "555", "666"};
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_multiple_choice, strings);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);*/

        String[] citys = {"1", "2", "3", "4", "5"};
        String[] level = {"a", "b", "c", "d", "e"};
        int[] imgIds = {R.drawable.wx, R.drawable.wx, R.drawable.wx, R.drawable.wx, R.drawable.wx};

        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < citys.length; i++) {
            Map<String, Object> map = new ArrayMap<>();
            map.put("image_tou", imgIds[i]);
            map.put("city", citys[i]);
            map.put("level", level[i]);
            listItems.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getBaseContext(),
                listItems,
                R.layout.list_item_layout,
                new String[]{"image_tou", "city", "level"}, new int[]{R.id.image_tou, R.id.city, R.id.level});

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(simpleAdapter);

    }
}