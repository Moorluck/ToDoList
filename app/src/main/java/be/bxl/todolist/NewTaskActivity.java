package be.bxl.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.bxl.todolist.utils.Date;

public class NewTaskActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    public static final String NAME_CODE = "name";
    public static final String PRIORITY_CODE = "priority";
    public static final String DATE_CODE = "date";

    EditText etName;
    Spinner spPriority;
    Button btnValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        etName = findViewById(R.id.et_newtask_name);

        spPriority = findViewById(R.id.spinner_newtask_priority);

        List<String> spinnerString = new ArrayList<>();
        spinnerString.add(getString(R.string.priority_low_string));
        spinnerString.add(getString(R.string.priority_medium_string));
        spinnerString.add(getString(R.string.priority_high_string));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
            getApplicationContext(),
            R.layout.spinner_item,
            android.R.id.text1,
            spinnerString
        );

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPriority.setAdapter(spinnerAdapter);

        btnValidate = findViewById(R.id.btn_newtask_validate);

        btnValidate.setOnClickListener(v -> {

            if (etName.length() > 0) {
                Bundle bundle = new Bundle();

                String name = etName.getText().toString();
                String priority = spPriority.getSelectedItem().toString();
                String date = Date.getDateString();

                bundle.putString(NAME_CODE, name);
                bundle.putString(PRIORITY_CODE, priority);
                bundle.putString(DATE_CODE, date);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else {
                Toast.makeText(this, getString(R.string.toast_newtask_string), Toast.LENGTH_SHORT).show();
            }

        });
    }
}