package com.example.drivingschoolmanagerandplanner.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.drivingschoolmanagerandplanner.models.Student;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;

    // Students
    private static final String DB_NAME = "studentsdb";
    private static final String TABLE_Students = "students";
    private static final String STUDENT_ID = "id";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String ADDRESS_LINE = "addressLine";
    private static final String SUBURB = "suburb";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";
    private static final String POSTCODE = "postcode";

    // Lessons
    private static final String TABLE_Lessons = "lessons";
    private static final String LESSON_ID = "id";
    private static final String NOTES = "notes";
    private static final String MEETING_ADDRESS = "meetingAddress";
    private static final String IS_PACKAGE = "isPackageLesson";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";

    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_Students + "("
                + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + PHONE + " INTEGER,"
                + EMAIL + " TEXT,"
                + ADDRESS_LINE + " TEXT,"
                + SUBURB + " TEXT,"
                + STATE + " TEXT,"
                + COUNTRY + " TEXT,"
                + POSTCODE + " INTEGER"+ ")";
        db.execSQL(CREATE_TABLE_STUDENTS);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Students);
        // Create tables again
        onCreate(db);
    }

//https://www.tutlane.com/tutorial/android/android-sqlite-database-with-examples

//region Student queries

    // Adding new Student
   public long InsertStudentDetails(String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country) {
       // Get the database to writable mode
       SQLiteDatabase db = this.getWritableDatabase();

       //A new ContentValues object to create map of values, the table columns are used as keys
       ContentValues cValues = new ContentValues();
       cValues.put(FIRST_NAME, firstName);
       cValues.put(LAST_NAME, lastName);
       cValues.put(PHONE, phone);
       cValues.put(EMAIL, email);
       cValues.put(ADDRESS_LINE, addressLine);
       cValues.put(SUBURB, suburb);
       cValues.put(STATE, state);
       cValues.put(POSTCODE, postcode);
       cValues.put(COUNTRY, country);

       // Insert the new row, returning the primary key value of the new row
       long newRowId = db.insert(TABLE_Students, null, cValues);
       db.close();

       return  newRowId;
   }

    // Get a list of Student Details
    public ArrayList<Student> GetStudents(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Student> studentList = new ArrayList<>();
        String query = "SELECT firstname, lastname, phone, email, addressLine, suburb, state, postcode, country FROM "+ TABLE_Students;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                Student student = new Student(
                        cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                        , cursor.getString(cursor.getColumnIndex(LAST_NAME))
                        , cursor.getInt(cursor.getColumnIndex(PHONE))
                        , cursor.getString(cursor.getColumnIndex(EMAIL))
                        , cursor.getString(cursor.getColumnIndex(ADDRESS_LINE))
                        , cursor.getString(cursor.getColumnIndex(SUBURB))
                        , cursor.getString(cursor.getColumnIndex(STATE))
                        , cursor.getInt(cursor.getColumnIndex(POSTCODE))
                        , cursor.getString(cursor.getColumnIndex(COUNTRY))
                );
                studentList.add(student);
                cursor.moveToNext();
            }
        }

        return  studentList;
    }

    // Get Student Details based on studentId
    public ArrayList<HashMap<String, String>> GetStudentById(int studentId){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        String query = "SELECT firstname, lastname, phone, email, addressLine, suburb, state, postcode, country FROM "+ TABLE_Students;
        Cursor cursor = db.query(TABLE_Students, new String[]{FIRST_NAME, LAST_NAME, PHONE, EMAIL, ADDRESS_LINE, SUBURB, STATE, POSTCODE, COUNTRY},
                STUDENT_ID+ "=?",new String[]{String.valueOf(studentId)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> student = new HashMap<>();
            student.put("firstname",cursor.getString(cursor.getColumnIndex(FIRST_NAME)));
            student.put("lastname",cursor.getString(cursor.getColumnIndex(LAST_NAME)));
            student.put("phone",cursor.getString(cursor.getColumnIndex(PHONE)));
            student.put("email",cursor.getString(cursor.getColumnIndex(EMAIL)));
            student.put("addressLine",cursor.getString(cursor.getColumnIndex(ADDRESS_LINE)));
            student.put("suburb",cursor.getString(cursor.getColumnIndex(SUBURB)));
            student.put("state",cursor.getString(cursor.getColumnIndex(STATE)));
            student.put("postcode",cursor.getString(cursor.getColumnIndex(POSTCODE)));
            student.put("country",cursor.getString(cursor.getColumnIndex(COUNTRY)));
            studentList.add(student);
        }
        return  studentList;
    }

    // Delete User Details
    public void DeleteStudent(int studentId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Students, STUDENT_ID+" = ?",new String[]{String.valueOf(studentId)});
        db.close();
    }

    // Update User Details
    public int UpdateStudentDetails(String firstName, String lastName, String phone, String email, String addressLine, String suburb, String state, String postcode, String country, int studentId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(FIRST_NAME, firstName);
        cValues.put(LAST_NAME, lastName);
        cValues.put(PHONE, phone);
        cValues.put(EMAIL, email);
        cValues.put(ADDRESS_LINE, addressLine);
        cValues.put(SUBURB, suburb);
        cValues.put(STATE, state);
        cValues.put(POSTCODE, postcode);
        cValues.put(COUNTRY, country);
        int count = db.update(TABLE_Students, cValues, STUDENT_ID+" = ?",new String[]{String.valueOf(studentId)});
        return  count;
    }
//endregion

}
