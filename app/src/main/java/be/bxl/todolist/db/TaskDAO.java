package be.bxl.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import be.bxl.todolist.R;
import be.bxl.todolist.enums.Priority;
import be.bxl.todolist.models.Task;
import be.bxl.todolist.utils.Convert;

public class TaskDAO {

    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public TaskDAO(Context context) {
        this.context = context;
    }

    public TaskDAO openWritable() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public TaskDAO openReadable() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public ContentValues convertTaskToContentValue(Task task) {
        ContentValues cv = new ContentValues();

        cv.put(DBInfo.Task.COLUMN_NAME, task.getName());
        cv.put(DBInfo.Task.COLUMN_PRIORITY, Convert.convertPriorityToString(context, task.getPriority()));
        cv.put(DBInfo.Task.COLUMN_DATE, task.getDate());

        return cv;
    }

    public Task convertCursortoTask(Cursor c) {
        long id = c.getLong(c.getColumnIndex(DBInfo.Task.COLUMN_ID));
        String name = c.getString(c.getColumnIndex(DBInfo.Task.COLUMN_NAME));
        Priority priority = Convert.convertStringToPriority(context, c.getString(c.getColumnIndex(DBInfo.Task.COLUMN_PRIORITY)));
        String date = c.getString(c.getColumnIndex(DBInfo.Task.COLUMN_DATE));

        return new Task(id, name, priority, date);
    }

    public long insert(Task task) {
        ContentValues cv = convertTaskToContentValue(task);
        return db.insert(DBInfo.Task.TABLE_NAME, null, cv);
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasks = new ArrayList<>();

        Cursor c = db.query(DBInfo.Task.TABLE_NAME, null, null, null, null, null, null);

        if(c.moveToFirst()) {
            while (!c.isAfterLast()) {
                tasks.add(convertCursortoTask(c));
                c.moveToNext();
            }
        }

        return tasks;
    }

    public long delete(long taskId) {
        return db.delete(DBInfo.Task.TABLE_NAME,
                DBInfo.Task.COLUMN_ID + "=" + taskId, null);
    }

    public long deleteAll() {
        return db.delete(DBInfo.Task.TABLE_NAME
                , null, null);
    }



}
