package com.khafizov.collectionapp;

import static com.khafizov.collectionapp.AppDatabase.MIGRATION_1_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;



public class ProfileActivity extends AppCompatActivity {
    TextInputEditText inputName, inputLastname, inputSurname, inputAddress, inputPhone, spinnerCable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        inputName = findViewById(R.id.input_name);
        inputLastname = findViewById(R.id.input_lastname);
        inputSurname = findViewById(R.id.input_surname);
        inputAddress = findViewById(R.id.input_address);
        inputPhone = findViewById(R.id.input_phone);
        spinnerCable = findViewById(R.id.input_spinner);
        loadUserProfile();}
    private void loadUserProfile() {
        AsyncTask<Void, Void, User> loadUserTask = new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "UfanetDatabase")
                        .addMigrations(MIGRATION_1_2)
                        .build();
                UserDao userDao = appDatabase.userDao();
                // Получаем идентификатор текущего пользователя из Firebase Authentication

                return userDao.getLastUser();
            }
            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    // Устанавливаем значения в TextView
                    inputName.setText(user.getName());
                    inputLastname.setText(user.getLastname());
                    inputSurname.setText(user.getSurname());
                    inputAddress.setText(user.getAddress());
                    inputPhone.setText(user.getPhone());
                    spinnerCable.setText(user.getCableStatus());

                }
            }
        };
        loadUserTask.execute();
    }


}