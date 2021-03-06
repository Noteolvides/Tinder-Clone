package com.example.tinder.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinder.Connection.TinderManager;
import com.example.tinder.Interfaces.GenericCallback;
import com.example.tinder.Model.CardOfPeople;
import com.example.tinder.Model.Gender;
import com.example.tinder.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements GenericCallback, AdapterView.OnItemSelectedListener {
    private static final String TAG = "SettingsActivity";

    private ImageView profileIV;
    private EditText nameET;
    private EditText descriptionET;
    private TextView birthdayTV;
    private EditText weight;
    private EditText height;
    private Gender[] genders;
    private Button saveButton;
    private Button logoutButton;
    private Spinner genderSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setTitle("Edit profile - Salle Tinder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileIV = findViewById(R.id.photo_iv);
        nameET = findViewById(R.id.name_et);
        descriptionET = findViewById(R.id.description_et);
        birthdayTV = findViewById(R.id.birthday_tv);
        weight = findViewById(R.id.height_et);
        height = findViewById(R.id.weight_et);
        saveButton = findViewById(R.id.save_button);
        profileIV.setImageResource(R.drawable.iscle);
        logoutButton = findViewById(R.id.logout);
        genderSpinner = findViewById(R.id.gender_spinner);

        CardOfPeople user = TinderManager.getInstance().getActualUser();
        if (user.getDisplayName() != null)
            nameET.setText(user.getDisplayName());
        if (user.getAboutMe() != null)
            descriptionET.setText(user.getAboutMe());
        if (user.getBirthDate() != null) {
            String[] bd = user.getBirthDate().split("-");
            birthdayTV.setText(bd[2] + "/" + bd[1] + "/" + bd[0]);
        } else
            birthdayTV.setText("xx/xx/xxxx");

        height.setText(String.valueOf(user.getHeight()));
        weight.setText(String.valueOf(user.getWeight()));

        logoutButton.setOnClickListener(v -> logout());
        saveButton.setOnClickListener(v -> sendData());
        birthdayTV.setOnClickListener(v -> setBirth());

        TinderManager.getInstance().getGenders(this);
    }

    private void logout() {
        runOnUiThread(() -> {
            SharedPreferences.Editor sharedPrefEditor = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();

            sharedPrefEditor.putBoolean("rememberMe", false);

            sharedPrefEditor.apply();


            // Create the LoginActivity intent
            Intent loginActivityIntent = new Intent(this, LoginActivity.class);
            // Start MainActivity via the intent
            startActivity(loginActivityIntent);
            // Finish the LoginActivity
            finishAffinity();
        });
    }

    private void setBirth() {
        Calendar c = Calendar.getInstance();
        if (true) { // No hi ha cap data de naixement
            c.add(Calendar.YEAR, -18);
        }
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> birthdayTV.setText(String.format("%02d/%02d/%02d", dayOfMonth, month + 1, year)), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void sendData() {
        CardOfPeople newProfile = TinderManager.getInstance().getActualUser();
        newProfile.setDisplayName(nameET.getText().toString());
        newProfile.setAboutMe(descriptionET.getText().toString());
        newProfile.setWeight(Float.parseFloat(weight.getText().toString()));
        newProfile.setHeight(Float.parseFloat(height.getText().toString()));
        String[] birthday = birthdayTV.getText().toString().split("/");
        newProfile.setBirthDate(birthday[2] + "-" + birthday[1] + "-" + birthday[0]);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        /*

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        newProfile.setPicture(Base64.encodeToString(imageBytes, Base64.DEFAULT));

        */

        TinderManager.getInstance().profilePut(this, newProfile);
    }

    /*
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
        }
    }
    */

    @Override
    public void onSuccess(Object data) {
        genders = (Gender[]) data;
        ArrayList<String> gendersList = new ArrayList<>();
        for (Gender gender : genders) {
            gendersList.add(gender.getType());
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gendersList);
        genderSpinner.setAdapter(adapter);
    }

    @Override
    public void onFailure(Object data) {
        runOnUiThread(() -> {
            // Create a Snackbar showing the error to the user
            String message = ((String) data);
            Toast.makeText(SettingsActivity.this, "Login failed: " + message + " \uD83D\uDE05", Toast.LENGTH_LONG).show();
            //Snackbar.make(mainLayout, "Login failed: " + message + " \uD83D\uDE05", Snackbar.LENGTH_LONG).show();
            finishAffinity();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
