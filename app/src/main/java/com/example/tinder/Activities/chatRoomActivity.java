package com.example.tinder.Activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tinder.Connection.TinderManager;
import com.example.tinder.GenericCallback;
import com.example.tinder.Model.Message;
import com.example.tinder.R;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class chatRoomActivity extends AppCompatActivity implements GenericCallback {
    private RecyclerView recycle;
    private ImageView perfil;
    private TextView name;
    private Button button;
    private TextInputEditText tersto;
    private ArrayList<com.example.tinder.Model.Message> messages;
    private float id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);
        id = (float) getIntent().getExtras().getSerializable("USER_ID");
        messages = new ArrayList<>();
        TinderManager.getInstance().getMessages((long)id,20,this);

        recycle = findViewById(R.id.recyclerView);
        perfil = findViewById(R.id.personalImage);
        name = findViewById(R.id.nameChat);
        tersto = findViewById(R.id.introducion);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> send());

    }

    private void send(){
        if (tersto.getText() != null) {
            String mensaje = tersto.getText().toString();
            Date date= new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            //Message m = new Message();

            //TinderManager.getInstance().postMessages();
        }
    }


    @Override
    public void onSuccess(Object data) {
        com.example.tinder.Model.Message[] men = (com.example.tinder.Model.Message[])data;
        messages.addAll(Arrays.asList(men));
        for (Message fer : messages){
            Log.d("Pepe",fer.getMessage());
        }
    }

    @Override
    public void onFailure(Object data) {

    }
}
