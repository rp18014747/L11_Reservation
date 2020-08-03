package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    CheckBox checkBox;
    Button btReserve;
    Button btReset;
    EditText etDay;
    EditText etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);
        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Listener to set the date
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDay.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };

                // Create and show the Date Picker Dialog
                String[] a = etDay.getText().toString().split("/");
                if (etDay.getText().toString().isEmpty()) {
                    Calendar cal = Calendar.getInstance();
                    DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                    myDateDialog.show();
                } else {
                    DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, Integer.parseInt(a[2]), Integer.parseInt(a[1]) - 1, Integer.parseInt(a[0]));
                    myDateDialog.show();
                }
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                // Create and show the Time Picker Dialog
                String[] a = etTime.getText().toString().split(":");
                if (etTime.getText().toString().isEmpty()) {
                    Calendar cal = Calendar.getInstance();
                    TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                    myTimeDialog.show();
                } else {
                    TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, Integer.parseInt(a[0]), Integer.parseInt(a[1]), true);
                    myTimeDialog.show();
                }
            }
        });

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }

                String str = "New Reservation" +
                        "\r\nName: " + etName.getText().toString()
                        + "\r\nSmoking: " + isSmoke
                        + "\r\nSize: " + etSize.getText().toString()
                        + "\r\nDate: " + etDay.getText().toString()
                        + "\r\nTime: " + etTime.getText().toString();

                // Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                // Set the dialog details
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(str);
                myBuilder.setCancelable(false);

                // Configure 'positive' button
                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "Hi, " + etName.getText().toString() + ", you have booked a "
                                        + etSize.getText().toString() + " person(s) "
                                        + (checkBox.isChecked() ? "smoking" : "non-smoking") + " table on "
                                        + etDay.getText().toString() + " at "
                                        + etTime.getText().toString() + ". Your phone number is "
                                        + etTelephone.getText().toString() + ".",
                                Toast.LENGTH_LONG).show();
                    }
                });
                // Configure 'neutral' button
                myBuilder.setNeutralButton("Cancel", null);

                // Bind and show dialog
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                etDay.setText("");
                etTime.setText("");
                checkBox.setChecked(false);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Instance of SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Instance of SharedPreferences Editor
        SharedPreferences.Editor prefEdit = prefs.edit();
        // Add key-value pair
        prefEdit.putString("name", etName.getText().toString());
        prefEdit.putString("number", etTelephone.getText().toString());
        prefEdit.putString("size", etSize.getText().toString());
        prefEdit.putBoolean("smoking", checkBox.isChecked());
        prefEdit.putString("day", etDay.getText().toString());
        prefEdit.putString("tine", etTime.getText().toString());
        // commit() saves changes into SharePreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Instance of SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieve saved data with key "greetings" from SharedPreference object
        etName.setText(prefs.getString("name", ""));
        etTelephone.setText(prefs.getString("number", ""));
        etSize.setText(prefs.getString("size", ""));
        checkBox.setChecked(prefs.getBoolean("smoking", false));
        etDay.setText(prefs.getString("day", ""));
        etTime.setText(prefs.getString("tine", ""));
    }
}