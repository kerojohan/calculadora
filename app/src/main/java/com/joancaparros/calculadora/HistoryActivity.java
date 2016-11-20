package com.joancaparros.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ArrayList<String> operationsList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView operationsHistory=(ListView) findViewById(R.id.operationHistory);
        Intent intent = getIntent();
        operationsList= intent.getStringArrayListExtra("listOperations");
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,operationsList);
        operationsHistory.setAdapter(arrayAdapter);


    }
}
