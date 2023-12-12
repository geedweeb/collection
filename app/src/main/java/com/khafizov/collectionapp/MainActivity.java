package com.khafizov.collectionapp;

import static com.khafizov.collectionapp.AppDatabase.MIGRATION_1_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {
    TextInputEditText editTextName, editTextLastname, editTextSurname, editTextAddress, editTextPhone, editTextEmail;
    Button saveBtn;
    Spinner spinnerCable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.input_name);
        editTextLastname = findViewById(R.id.input_lastname);
        editTextSurname = findViewById(R.id.input_surname);
        editTextAddress = findViewById(R.id.input_address);
        editTextPhone = findViewById(R.id.input_phone);
        saveBtn = findViewById(R.id.save_btn);
        spinnerCable = findViewById(R.id.spinner_cable);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choose, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCable.setAdapter(adapter);
        saveBtn.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String lastname = editTextLastname.getText().toString();
            String surname = editTextSurname.getText().toString();
            String address = editTextAddress.getText().toString();
            String phone = editTextPhone.getText().toString();
            String cableStatus = spinnerCable.getSelectedItem().toString();
            registerUser(name, lastname, surname, address, phone, cableStatus);
        });
    }
    private void registerUser(String name, String lastname, String surname, String address, String phone, String cableStatus) {
        if (name.isEmpty() || surname.isEmpty() || lastname.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(MainActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            return;
        }
        saveUserToDatabase(name, lastname, surname, address, phone, cableStatus);
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        finish();
    }
    private void saveUserToDatabase(String name, String lastname, String surname, String address, String phone, String cableStatus) {
        AsyncTask<Void, Void, Void> saveUserTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                // Инициализация базы данных и получение соответствующего DAO
                AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "UfanetDatabase")
                        .addMigrations(MIGRATION_1_2)
                        .build();
                UserDao userDao = appDatabase.userDao();
                User user = new User();
                user.setName(name);
                user.setLastname(lastname);
                user.setSurname(surname);
                user.setAddress(address);
                user.setPhone(phone);
                user.setCableStatus(cableStatus);
                userDao.insert(user);
                User lastUser = userDao.getLastUser();
                return null;
            }
        };
        saveUserTask.execute();
    }
    @Override
    public void onBackPressed() {
        // здесь можно выполнить нужные действия, например, закрыть текущее окно или перейти на другой экран
        super.onBackPressed();
    }
}

