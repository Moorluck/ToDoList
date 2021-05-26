package be.bxl.todolist.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Date {
    public static String getDateString() {

        return java.time.LocalDate.now().toString();

    }
}
