package es.database.anotai;

import java.nio.channels.ClosedByInterruptException;

import es.model.anotai.Classmate;
import es.model.anotai.Discipline;
import es.model.anotai.Exam;
import es.model.anotai.GroupHomework;
import es.model.anotai.IndividualHomework;
import es.model.anotai.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AnotaiDbHelper extends SQLiteOpenHelper {

    /** The BD instance. */
    private static AnotaiDbHelper instance;

    /** The database version constant DATABASE_VERSION. */
    public static final int DATABASE_VERSION = 4;

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
    //public static final String TASKENTRY_COLUMN_NAME = "name";

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
//            + TASKENTRY_COLUMN_NAME + " TEXT NOT NULL, "
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

    /** The Constant SQL_DELETE_EMERGENCY_TABLE. */
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
    
    
    
    //---------------------------------------------------------------------------------------
    /** The Constant TABLE_NAME. */
    public static final String INDIVIDUALHOMEWORK_TABLE_NAME = "individualHomeWorkTable";

    /** The Constant COLUMN_ID_TASK. */
    public static final String INDIVIDUALHOMEWORK_COLUMN_ID_TASK = "id_task";

    /** The Constant SQL_CREATE_INDIVIDUAL_HOME_WORK. */
    private static final String SQL_CREATE_INDIVIDUAL_HOME_WORK = "CREATE TABLE "
            + INDIVIDUALHOMEWORK_TABLE_NAME
            + "("
            + INDIVIDUALHOMEWORK_COLUMN_ID_TASK
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT)";

    /** The Constant SQL_DELETE_EMERGENCY_TABLE. */
    private static final String SQL_DELETE_INDIVIDUAL_HOME_WORK_TABLE = "DROP TABLE IF EXISTS "
            + INDIVIDUALHOMEWORK_TABLE_NAME;
    
    
    
    //---------------------------------------------------------------------------------------

    /** The Constant TABLE_NAME. */
    public static final String GROUPHOMEWORK_TABLE_NAME = "groupHomeWorkTable";

    /** The Constant COLUMN_ID_TASK. */
    public static final String GROUPHOMEWORK_COLUMN_ID_TASK = "_id";

    /** The Constant SQL_CREATE_INDIVIDUAL_HOME_WORK. */
    private static final String SQL_CREATE_GROUP_HOME_WORK = "CREATE TABLE "
            + GROUPHOMEWORK_TABLE_NAME + "(" + GROUPHOMEWORK_COLUMN_ID_TASK
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT)";

    /** The Constant SQL_DELETE_EMERGENCY_TABLE. */
    private static final String SQL_DELETE_GROUP_HOME_WORK_TABLE = "DROP TABLE IF EXISTS "
            + GROUPHOMEWORK_TABLE_NAME;
    
    
    
    //--------------------------------------------------------------------------------------

    /** The Constant TABLE_NAME. */
    public static final String EXAM_TABLE_NAME = "examTable";

    /** The Constant COLUMN_ID_TASK. */
    public static final String EXAM_COLUMN_ID_TASK = "id_task";

    /** The Constant SQL_CREATE_EXAM. */
    private static final String SQL_CREATE_EXAM = "CREATE TABLE "
            + EXAM_TABLE_NAME + "(" + EXAM_COLUMN_ID_TASK
            + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT)";

    /** The Constant SQL_DELETE_EMERGENCY_TABLE. */
    private static final String SQL_DELETE_EXAM_TABLE = "DROP TABLE IF EXISTS "
            + EXAM_TABLE_NAME;   
    
    
    //----------------------------------------------------------------------------------

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
        /*dbHelper.execSQL(SQL_CREATE_EXAM);
        dbHelper.execSQL(SQL_CREATE_INDIVIDUAL_HOME_WORK);
        dbHelper.execSQL(SQL_CREATE_GROUP_HOME_WORK);*/
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
//        dbHelper.execSQL(SQL_DELETE_EXAM_TABLE);
//        dbHelper.execSQL(SQL_DELETE_GROUP_HOME_WORK_TABLE);
//        dbHelper.execSQL(SQL_DELETE_INDIVIDUAL_HOME_WORK_TABLE);
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
    
 

    /*public final long addDiscipline(final Discipline discipline) {
        final SQLiteDatabase dbHelper = getWritableDatabase();
        final ContentValues valuesDiscipline = new ContentValues();

        valuesDiscipline.put(DISCIPLINEENTRY_COLUMN_NAME, discipline.getName());
        valuesDiscipline.put(DISCIPLINEENTRY_COLUMN_TEACHER,
                discipline.getTeacher());

        final long disciplineId = dbHelper.insert(DISCIPLINEENTRY_TABLE_NAME,
                null, valuesDiscipline);

        discipline.setId(disciplineId);

        for (final Task task : discipline.getTasks()) {

            final ContentValues valuesTask = new ContentValues();
            valuesTask.put(TASKENTRY_COLUMN_ID, task.getId());
            valuesTask.put(TASKENTRY_COLUMN_ID_DISCIPLINE, discipline.getId());
            valuesTask.put(TASKENTRY_COLUMN_DESCRIPTION, task.getDescription());
            valuesTask.put(TASKENTRY_COLUMN_CADASTER_DATE,
                    task.getCadasterDateText());
            valuesTask.put(TASKENTRY_COLUMN_DEADLINE_DATE,
                    task.getDeadlineDateText());
            valuesTask.put(TASKENTRY_COLUMN_GRADE, task.getGrade());
            // valuesTask.put(TASKENTRY_COLUMN_PRIORITY, task.getPriority());
            // TODO Encontrar uma maneira de armazenar tarefas com prioridade.

            final long taskID = dbHelper.insert(TASKENTRY_TABLE_NAME, null,
                    valuesTask);

            task.setId(taskID);

            if (task instanceof IndividualHomework) {
                final ContentValues vIndividualWork = new ContentValues();

                vIndividualWork.put(INDIVIDUALHOMEWORK_COLUMN_ID_TASK,
                        task.getId());

                final long individualWorkID = dbHelper.insert(
                        INDIVIDUALHOMEWORK_TABLE_NAME, null, vIndividualWork);

                ((IndividualHomework) task).setId(individualWorkID);
            }

            if (task instanceof Exam) {
                final ContentValues valuesExam = new ContentValues();
                valuesExam.put(EXAM_COLUMN_ID_TASK, task.getId());

                final long examID = dbHelper.insert(
                        INDIVIDUALHOMEWORK_TABLE_NAME, null, valuesExam);

                ((Exam) task).setId(examID);
            }

            if (task instanceof GroupHomework) {
                final ContentValues valuesGroupWork = new ContentValues();
                valuesDiscipline
                        .put(GROUPHOMEWORK_COLUMN_ID_TASK, task.getId());

                final long groupWorkID = dbHelper.insert(
                        GROUPHOMEWORK_TABLE_NAME, null, valuesGroupWork);

                ((GroupHomework) task).setId(groupWorkID);

                for (final Classmate classmate : ((GroupHomework) task)
                        .getGroup()) {
                    final ContentValues valuesClassmate = new ContentValues();
                    valuesClassmate.put(CLASSMATEENTRY_COLUMN_ID,
                            classmate.getId());
                    valuesClassmate.put(CLASSMATEENTRY_COLUMN_ID_TASK,
                            task.getId());
                    valuesClassmate.put(CLASSMATEENTRY_COLUMN_NAME,
                            classmate.getName());

                    final long classmateID = dbHelper.insert(
                            CLASSMATEENTRY_TABLE_NAME, null, valuesClassmate);

                    classmate.setId(classmateID);

                    for (final String numPhone : classmate.getPhoneNumbers()) {
                        final ContentValues valuesPhoneNumbers = new ContentValues();
                        valuesPhoneNumbers.put(
                                PHONENUMBERS_COLUMN_ID_CLASSMATE,
                                classmate.getId());
                        valuesPhoneNumbers.put(
                                PHONENUMBERS_COLUMN_PHONE_NUMBER, numPhone);

                        dbHelper.insert(PHONENUMBERS_TABLE_NAME, null,
                                valuesPhoneNumbers);
                    }
                }
            }
        }

        CloseDB();

        return disciplineId;
    }

    private void CloseDB() {
        final SQLiteDatabase dbHelper = getReadableDatabase();
        if (dbHelper != null && dbHelper.isOpen()) {
            dbHelper.close();

        }
    }*/

}
