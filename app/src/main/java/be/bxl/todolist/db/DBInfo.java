package be.bxl.todolist.db;

public class DBInfo {

    public static final String DBname = "db_task";
    public static final int version = 1;

    public static class Task {

        public static final String TABLE_NAME = "task";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_DATE = "date";

        public static final String CREATE_REQUEST = "CREATE TABLE " + TABLE_NAME + "( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PRIORITY + " TEXT, "
                + COLUMN_DATE + " STRING);";

        public static final String UPGRADE_REQUEST = "DROP TABLE " + TABLE_NAME;

    }
}
