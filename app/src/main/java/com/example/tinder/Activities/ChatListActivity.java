package com.example.tinder.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tinder.Adapters.ChatListAdapter;
import com.example.tinder.Connection.TinderManager;
import com.example.tinder.Interfaces.GenericCallback;
import com.example.tinder.Model.CardOfPeople;
import com.example.tinder.Model.ChatRow;
import com.example.tinder.Model.Message;
import com.example.tinder.R;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    private static final String TAG = "ChatListActivity";

    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<CardOfPeople> friends = new ArrayList<>();
        TinderManager.getInstance().getFriends(new GenericCallback() {
            @Override
            public void onSuccess(Object data) {
                CardOfPeople[] people = (CardOfPeople[]) data;

                ArrayList<ChatRow> rows = new ArrayList<>();
                boolean inside = false;
                for (CardOfPeople p : people) {
                    for (CardOfPeople p2 : friends) {
                        if (p.getId() == p2.getId()) {
                            inside = true;
                        }
                    }
                    if (!inside) {
                        TinderManager.getInstance().getMessages(p.getId(), 1, new GenericCallback() {
                            @Override
                            public void onSuccess(Object data) {
                                Message[] messages = (Message[]) data;

                                if (messages.length > 0)
                                    rows.add(new ChatRow(p.getId(), p.getDisplayName(), messages[0].getMessage(), p.getPicture()));
                                else {
                                    rows.add(new ChatRow(p.getId(), p.getDisplayName(), "No messages yet!", p.getPicture()));
                                }
                                chatListAdapter.setDataset(rows);
                            }

                            @Override
                            public void onFailure(Object data) {
                                Log.d(TAG, "Adding " + p.getId());
                                rows.add(new ChatRow(p.getId(), p.getDisplayName(), "No messages yet!", p.getPicture()));
                                chatListAdapter.setDataset(rows);
                            }
                        });
                        friends.add(p);
                        inside = false;
                    }

                }
            }

            @Override
            public void onFailure(Object data) {
                Toast.makeText(ChatListActivity.this, "Error while getting friends!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        try {
            if (TinderManager.getInstance().getActualUser().getDisplayName().equalsIgnoreCase("fernando")) {
                MediaPlayer mp = MediaPlayer.create(this, R.raw.welcome_sound);
                mp.start();
            }
        } catch (NullPointerException e) {
            // Something failed...
        }

        ArrayList<CardOfPeople> friends = new ArrayList<>();
        // Esto es para la barra de arriba
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatListAdapter = new ChatListAdapter(this, null);
        recyclerView.setAdapter(chatListAdapter);

        TinderManager.getInstance().getFriends(new GenericCallback() {
            @Override
            public void onSuccess(Object data) {
                CardOfPeople[] people = (CardOfPeople[]) data;

                ArrayList<ChatRow> rows = new ArrayList<>();
                boolean inside = false;
                for (CardOfPeople p : people) {
                    for (CardOfPeople p2 : friends) {
                        if (p.getId() == p2.getId()) {
                            inside = true;
                        }
                    }
                    if (!inside) {
                        TinderManager.getInstance().getMessages(p.getId(), 1, new GenericCallback() {
                            @Override
                            public void onSuccess(Object data) {
                                Message[] messages = (Message[]) data;

                                if (messages.length > 0)
                                    rows.add(new ChatRow(p.getId(), p.getDisplayName(), messages[0].getMessage(), p.getPicture()));
                                else {
                                    rows.add(new ChatRow(p.getId(), p.getDisplayName(), "No messages yet!", p.getPicture()));
                                }
                                chatListAdapter.setDataset(rows);
                            }

                            @Override
                            public void onFailure(Object data) {
                                Log.d(TAG, "Adding " + p.getId());
                                rows.add(new ChatRow(p.getId(), p.getDisplayName(), "No messages yet!", p.getPicture()));
                                chatListAdapter.setDataset(rows);
                            }
                        });
                        friends.add(p);
                        inside = false;
                    }

                }
            }

            @Override
            public void onFailure(Object data) {
                Toast.makeText(ChatListActivity.this, "Error while getting friends!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
