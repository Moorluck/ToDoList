package be.bxl.todolist.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import be.bxl.todolist.R;
import be.bxl.todolist.enums.Priority;

public class Convert {

    public static Priority convertStringToPriority(Context context, String priority) {

        if (priority.equals(context.getString(R.string.priority_high_string))) {
            return Priority.HIGH;
        }
        else if (priority.equals(context.getString(R.string.priority_medium_string))) {
            return Priority.MEDIUM;
        }
        else if (priority.equals(context.getString(R.string.priority_low_string))) {
            return Priority.LOW;
        }
        else {
            throw new RuntimeException("Priorité inconnue");
        }

    }

    public static String convertPriorityToString(Context context, Priority priority) {
        switch (priority) {
            case LOW:
                return context.getString(R.string.priority_low_string);
            case MEDIUM:
                return context.getString(R.string.priority_medium_string);
            case HIGH:
                return context.getString(R.string.priority_high_string);
            default:
                throw new RuntimeException("Impossible de convertir la priorité");
        }
    }

    public static String convertLongToDateString(long l) {

        Date date = new Date(l);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

        return dateFormat.format(date);

    }
}
