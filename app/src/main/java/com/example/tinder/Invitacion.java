package com.example.tinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class Invitacion extends AppCompatActivity {

    RecyclerView recyclerView = findViewById(R.id.recyclerview);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitacion);
        recyclerView.setLayoutManager(this);
    }
}
