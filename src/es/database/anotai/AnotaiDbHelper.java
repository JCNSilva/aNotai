package es.database.anotai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AnotaiDbHelper extends SQLiteOpenHelper {

    /** The BD instance. */
    private static AnotaiDbHelper instance;

    /** The database version constant DATABASE_VERSION. */
    public static final int DATABASE_VERSION = 5;

    /** The database file name constant DATABASE_NAME. */
    public static final String DATABASE_NAME = "Anotai.db";
    
    
    
    //-------------------------------------------------------------------------DONE

    /** The Constant TABLE_NAME. */
    public static final String DISCIPLINEENTRY_TABLE_NAME = "disciplineTable";

    /** The Constant COLUMN_ID. */
    public static final String DISCIPLINEENTRY_COLUMN_ID = "_id";

    /** The Constant COLUMN_NAME. */
    public static final String DISCIPLINEENTRY_COLUMN_NAME = "name";

    /** The Constant COLUMN_TEACHER_NAME. */
    public static final String DISCIPLINEENTRY_COLUMN_TEACHER = "teacher";

    /** The Constant SQL_CREATE_DISCIPLINE_TABLE. */
    private static final String SQL_CREATE_DISCIPLINE_TABLE = "CREATE TABLE "
            + DISCIPLINEENTRY_TABLE_NAME + "(" + DISCIPLINEENTRY_COLUMN_ID
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + DISCIPLINEENTRY_COLUMN_NAME + " TEXT NOT NULL, "
            + DISCIPLINEENTRY_COLUMN_TEACHER + " TEXT NOT NULL);";

    /** The Constant SQL_DELETE_DISCIPLINE_TABLE. */
    private static final String SQL_DELETE_DISCIPLINE_TABLE = "DROP TABLE IF EXISTS "
            + DISCIPLINEENTRY_TABLE_NAME + ";";
    
    
    
    //------------------------------------------------------------------------

    /** The Constant TABLE_NAME. */
    public static final String TASKENTRY_TABLE_NAME = "taskTable";

    /** The Constant COLUMN_ID. */
    public static final String TASKENTRY_COLUMN_ID = "_id";

    /** The Constant COLUMN_NAME. */
    public static final String TASKENTRY_COLUMN_DESCRIPTION = "description";

    /** The Constant COLUMN_CADASTER_DATE. */
    public static final String TASKENTRY_COLUMN_CADASTER_DATE = "cadasterDate";

    /** The Constant COLUMN_DEADLINE_DATE. */
    public static final String TASKENTRY_COLUMN_DEADLINE_DATE = "deadlineDate";

    /** The Constant COLUMN_PRIORITY. */
    public static final String TASKENTRY_COLUMN_PRIORITY = "priority";

    /** The Constant COLUMN_GRADE. */
    public static final String TASKENTRY_COLUMN_GRADE = "grade";

    /** The Constant COLUMN_ID_DISCIPLINE. */
    public static final String TASKENTRY_COLUMN_ID_DISCIPLINE = "id_discipline";
    
    public static final String TASKENTRY_COLUMN_SUBCLASS = "subclass";

    /** The Constant SQL_CREATE_TASK. */
    public static final String SQL_CREATE_TASK = "CREATE TABLE "
            + TASKENTRY_TABLE_NAME + "(" 
    		+ TASKENTRY_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TASKENTRY_COLUMN_DESCRIPTION + " TEXT NOT NULL, "
            + TASKENTRY_COLUMN_CADASTER_DATE + " TEXT NOT NULL, "
            + TASKENTRY_COLUMN_DEADLINE_DATE + " TEXT NOT NULL, "
            + TASKENTRY_COLUMN_PRIORITY + " TEXT NOT NULL, "
            + TASKENTRY_COLUMN_GRADE + " TEXT NOT NULL, "
            + TASKENTRY_COLUMN_ID_DISCIPLINE + " INTEGER NOT NULL, "
            + TASKENTRY_COLUMN_SUBCLASS + " TEXT NOT NULL, "
            + "FOREIGN KEY (" + TASKENTRY_COLUMN_ID_DISCIPLINE + ") REFERENCES " 
            + DISCIPLINEENTRY_TABLE_NAME + " ("+ DISCIPLINEENTRY_COLUMN_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    /** The Constant SQL_DELETE_TASK_TABLE. */
    private static final String SQL_DELETE_TASK_TABLE = "DROP TABLE IF EXISTS "
            + TASKENTRY_TABLE_NAME + ";";
    
    
    
    //-----------------------------------------------------------------------------

    /** The Constant TABLE_NAME. */
    public static final String CLASSMATEENTRY_TABLE_NAME = "classmateTable";

    /** The Constant COLUMN_ID. */
    public static final String CLASSMATEENTRY_COLUMN_ID = "_id";

    /** The Constant COLUMN_NAME. */
    public static final String CLASSMATEENTRY_COLUMN_NAME = "name";

    /** The Constant COLUMN_ID_GROUP. */
    public static final String CLASSMATEENTRY_COLUMN_ID_TASK = "id_homework";

    /** The Constant SQL_CREATE_CLASSMATE. */
    private static final String SQL_CREATE_CLASSMATE = "CREATE TABLE "
            + CLASSMATEENTRY_TABLE_NAME + "(" + CLASSMATEENTRY_COLUMN_ID
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + CLASSMATEENTRY_COLUMN_NAME + " TEXT NOT NULL, "
            + CLASSMATEENTRY_COLUMN_ID_TASK + " INTEGER NOT NULL, "
            + " FOREIGN KEY (" + CLASSMATEENTRY_COLUMN_ID_TASK + ") REFERENCES "
            + TASKENTRY_TABLE_NAME + " (" + TASKENTRY_COLUMN_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE );";

    /** The Constant SQL_DELETE_EMERGENCY_TABLE. */
    private static final String SQL_DELETE_CLASSMATE_TABLE = "DROP TABLE IF EXISTS "
            + CLASSMATEENTRY_TABLE_NAME + ";";
    
    
    
    //---------------------------------------------------------------------------DONE

    /** The Constant TABLE_NAME. */
    public static final String PHONENUMBERS_TABLE_NAME = "phoneNumbersTable";
    
    /** The Constant COLUMN_ID. */
    public static final String PHONENUMBERS_COLUMN_ID = "_id";

    /** The Constant COLUMN_PHONE_NUMBER. */
    public static final String PHONENUMBERS_COLUMN_PHONE_NUMBER = "phoneNumber";

    /** The Constant COLUMN_ID_CLASSMATE. */
    public static final String PHONENUMBERS_COLUMN_ID_CLASSMATE = "id_classmate";

    /** The Constant SQL_CREATE_PHONE_NUMBERS */
    private static final String SQL_CREATE_PHONE_NUMBERS = "CREATE TABLE "
            + PHONENUMBERS_TABLE_NAME + "("
            + PHONENUMBERS_COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
            + PHONENUMBERS_COLUMN_PHONE_NUMBER + " TEXT NOT NULL, "
            + PHONENUMBERS_COLUMN_ID_CLASSMATE + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + PHONENUMBERS_COLUMN_ID_CLASSMATE + ") REFERENCES " 
            + CLASSMATEENTRY_TABLE_NAME + " ("+ CLASSMATEENTRY_COLUMN_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE );";;

    /** The Constant SQL_DELETE_EMERGENCY_TABLE. */
    private static final String SQL_DELETE_PHONE_NUMBERS_TABLE = "DROP TABLE IF EXISTS "
            + PHONENUMBERS_TABLE_NAME + ";";
    
    
  //---------------------------------------------------------------------------DONE

    public AnotaiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static AnotaiDbHelper getInstance(final Context context) {
        synchronized (AnotaiDbHelper.class) {
            if (instance == null) {
                instance = new AnotaiDbHelper(context);
            }
            return instance;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
     * .SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase dbHelper) {
        dbHelper.execSQL(SQL_CREATE_DISCIPLINE_TABLE);
        dbHelper.execSQL(SQL_CREATE_TASK);
        dbHelper.execSQL(SQL_CREATE_CLASSMATE);
        dbHelper.execSQL(SQL_CREATE_PHONE_NUMBERS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.database.sqlite.SQLiteOpenHelper
     * #onUpgrade(android.database.sqlite .SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase dbHelper, int oldVersion,
            int newVersion) {
        dbHelper.execSQL(SQL_DELETE_DISCIPLINE_TABLE);
        dbHelper.execSQL(SQL_DELETE_CLASSMATE_TABLE);
        dbHelper.execSQL(SQL_DELETE_TASK_TABLE);
        dbHelper.execSQL(SQL_DELETE_PHONE_NUMBERS_TABLE);
        onCreate(dbHelper);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.database.sqlite.SQLiteOpenHelper#onDowngrade(android.database
     * .sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onDowngrade(SQLiteDatabase dbHelper, int oldVersion,
            int newVersion) {
        onUpgrade(dbHelper, oldVersion, newVersion);
    }
}
