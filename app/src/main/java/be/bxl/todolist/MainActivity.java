package be.bxl.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import be.bxl.todolist.adapter.TaskAdapter;
import be.bxl.todolist.db.TaskDAO;
import be.bxl.todolist.enums.Priority;
import be.bxl.todolist.models.Task;
import be.bxl.todolist.utils.Convert;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ListItemClickListener {

    Button btnAdd, btnDelete;
    RecyclerView rvListOfTask;

    ArrayList<Task> tasks;

    TaskDAO db;

    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new TaskDAO(getApplicationContext());

        tasks = db.openReadable().getAllTask();
        db.close();

        btnAdd = findViewById(R.id.btn_main_add);
        btnDelete = findViewById(R.id.btn_main_delete_all);

        rvListOfTask = findViewById(R.id.rv_main_liste);

        rvListOfTask.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        taskAdapter = new TaskAdapter(getApplicationContext(), this, tasks);
        rvListOfTask.setAdapter(taskAdapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
            startActivityForResult(intent, NewTaskActivity.REQUEST_CODE);
        });

        btnDelete.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setCancelable(true);
            builder.setMessage(R.string.alert_dialog_delete_message);
            builder.setPositiveButton(R.string.alert_dialog_main_positive_answer, (dialog, which) -> {
                db.openWritable().deleteAll();
                updateList();
                dialog.cancel();
            });

            builder.setNegativeButton(R.string.alert_dialog_main_negative_answer, (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NewTaskActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data.getExtras() != null) {
                Bundle bundle = data.getExtras();

                String name = bundle.getString(NewTaskActivity.NAME_CODE);
                Priority priority = Convert.convertStringToPriority(getApplicationContext(),
                        bundle.getString(NewTaskActivity.PRIORITY_CODE));
                String date = bundle.getString(NewTaskActivity.DATE_CODE);

                db.openWritable().insert(new Task(name, priority, date));

                updateList();
            }
        }
    }

    @Override
    public void onBtnItemClickListener(long taskId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setMessage(R.string.alert_finish_task);
        builder.setPositiveButton(R.string.alert_dialog_main_positive_answer, (dialog, which) -> {
            db.openWritable().delete(taskId);
            updateList();
            dialog.cancel();
        });

        builder.setNegativeButton(R.string.alert_dialog_main_negative_answer, (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void updateList() {
        tasks.clear();
        tasks.addAll(db.getAllTask());

        taskAdapter.notifyDataSetChanged();
    }
}