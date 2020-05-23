package com.example.drivingschoolmanagerandplanner.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.drivingschoolmanagerandplanner.models.DrivingTest;
import com.example.drivingschoolmanagerandplanner.models.Lesson;
import com.example.drivingschoolmanagerandplanner.models.Student;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final String TAG = "DbHandler" ;

    //region constants
    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "manageInstructordb";

    // Students
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
    private static final String LESSON_ID = "lessonId";
    private static final String NOTES = "notes";
    private static final String MEETING_ADDRESS = "meetingAddress";
    private static final String IS_PACKAGE = "isPackageLesson";
    private static final String START_TIME = "startTime";
    private static final String END_TIME = "endTime";
    private static final String AMOUNT = "amount";
    private static final String DAY = "day";

    // Driving Tests
    private static final String TABLE_Driving_Tests = "tests";
    private static final String TEST_ID = "testId";
    private static final String LOCATION = "location";
    private static final String TEST_DATE = "testDate";
    private static final String TEST_TIME = "testTime";
    private static final String BOOKING_NUMBER = "bookingNumber";
    private static final String RESULT = "result";


    //endregion constants

    public DbHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

   // region onCreate(SQLiteDatabase db)
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


        // create Lessons table
        String CREATE_TABLE_LESSONS = "CREATE TABLE " + TABLE_Lessons + "("
                + LESSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTES + " TEXT,"
                + AMOUNT + " REAL,"
                + DAY + " TEXT,"
                + START_TIME + " TEXT,"
                + END_TIME + " TEXT,"
                + MEETING_ADDRESS + " TEXT,"
                + IS_PACKAGE + " INTEGER,"
                + STUDENT_ID + " INTEGER,"
                + " FOREIGN KEY ("+STUDENT_ID+")  REFERENCES "+TABLE_Students+"("+STUDENT_ID+"));";
        db.execSQL(CREATE_TABLE_LESSONS);

        // create Driving Tests table
        String CREATE_TABLE_DRIVING_TESTS = "CREATE TABLE " + TABLE_Driving_Tests + "("
                + TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LOCATION + " TEXT,"
                + TEST_DATE + " TEXT,"
                + TEST_TIME + " TEXT,"
                + END_TIME + " TEXT,"
                + BOOKING_NUMBER + " TEXT,"
                + RESULT + " INTEGER,"
                + STUDENT_ID + " INTEGER,"
                + " FOREIGN KEY ("+STUDENT_ID+")  REFERENCES "+TABLE_Students+"("+STUDENT_ID+"));";
        db.execSQL(CREATE_TABLE_DRIVING_TESTS);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Students);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Lessons);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Driving_Tests);
        // Create tables again
        onCreate(db);
    }

    // endregion onCreate(SQLiteDatabase db)

//https://www.tutlane.com/tutorial/android/android-sqlite-database-with-examples

    //region Student queries
    // Adding new Student
   public long insertStudentDetails(String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country) {
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
//https://stackoverflow.com/questions/6234171/how-do-i-update-an-android-sqlite-database-column-value-to-null-using-contentval
    public ArrayList<Student> getStudents(){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Student> studentList = new ArrayList<>();
        String query = "SELECT firstname, lastname, phone, email, addressLine, suburb, state, postcode, country, id FROM "+ TABLE_Students;
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
                        , cursor.getInt(cursor.getColumnIndex(STUDENT_ID))
                );

                Log.d(TAG, "GetStudents: studentId " + cursor.getColumnIndex(STUDENT_ID));
                studentList.add(student);
                cursor.moveToNext();
            }
        }

        return  studentList;
    }

//    // Get Student Details based on studentId
    public ArrayList<HashMap<String, String>> getStudentsById(int studentId){
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

    //https://stackoverflow.com/questions/26355615/getting-single-row-from-table-in-sqlite-android/26356260
    public Student GetStudentById(int studentId){

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_Students + " WHERE "
                + STUDENT_ID + " = " + studentId;

        Log.d(TAG , "GetStudentById query " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Student student = new Student();
        student.setFullName(cursor.getString(cursor.getColumnIndex(FIRST_NAME))
                , cursor.getString(cursor.getColumnIndex(LAST_NAME)));
        student.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS_LINE))
               , cursor.getString(cursor.getColumnIndex(SUBURB))
               , cursor.getString(cursor.getColumnIndex(STATE))
               , cursor.getInt(cursor.getColumnIndex(POSTCODE))
               , cursor.getString(cursor.getColumnIndex(COUNTRY)));
        student.setContactDetails(  cursor.getInt(cursor.getColumnIndex(PHONE))
               , cursor.getString(cursor.getColumnIndex(EMAIL)));
        return student;
    }

    // Delete Student Details
    public void deleteStudent(int studentId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Students, STUDENT_ID+" = ?",new String[]{String.valueOf(studentId)});
        db.close();
    }

    // Update Student Details
    public int updateStudentDetails(String firstName, String lastName, String phone, String email, String addressLine, String suburb, String state, String postcode, String country, int studentId){
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

    // endregion Student queries

    //region Lessons Queries

    public long insertLessonDetails(String notes, Float amount, String day, String startTime, String endTime, String meetingAddress, int isPackageLesson, int studentId) {
        // Get the database to writable mode
        SQLiteDatabase db = this.getWritableDatabase();

        //A new ContentValues object to create map of values, the table columns are used as keys
        ContentValues cValues = new ContentValues();
        cValues.put(NOTES, notes);
        cValues.put(AMOUNT, amount);
        cValues.put(DAY, day);
        cValues.put(START_TIME, startTime);
        cValues.put(END_TIME, endTime);
        cValues.put(MEETING_ADDRESS, meetingAddress);
        cValues.put(IS_PACKAGE, isPackageLesson);
        cValues.put(STUDENT_ID, studentId);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Lessons, null, cValues);
        db.close();

        return newRowId;
    }

    public ArrayList<Lesson> getLessons() {


        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Lesson> lessonsList = new ArrayList<>();
        String query = "SELECT notes, amount, day, startTime, endTime, meetingAddress, isPackageLesson, id FROM " + TABLE_Lessons;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                Lesson student = new Lesson(
                        cursor.getString(cursor.getColumnIndex(NOTES))
                        , cursor.getFloat(cursor.getColumnIndex(AMOUNT))
                        , cursor.getString(cursor.getColumnIndex(DAY))
                        , cursor.getString(cursor.getColumnIndex(START_TIME))
                        , cursor.getString(cursor.getColumnIndex(END_TIME))
                        , cursor.getString(cursor.getColumnIndex(MEETING_ADDRESS))
                        , cursor.getInt(cursor.getColumnIndex(IS_PACKAGE))
                        , cursor.getInt(cursor.getColumnIndex(STUDENT_ID))
                );
                lessonsList.add(student);
                cursor.moveToNext();
            }
        }
        return lessonsList;
    }

    // Delete Lesson Details
    public void deleteLesson(int lessonId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Lessons, LESSON_ID+" = ?",new String[]{String.valueOf(lessonId)});
        db.close();
    }

    // Update Lesson Details
    public int updateLessonDetails(String notes, Float amount, String day, String startTime, String endTime, String meetingAddress, int isPackageLesson, int studentId, int lessonId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(NOTES, notes);
        cValues.put(AMOUNT, amount);
        cValues.put(DAY, day);
        cValues.put(START_TIME, startTime);
        cValues.put(END_TIME, endTime);
        cValues.put(MEETING_ADDRESS, meetingAddress);
        cValues.put(IS_PACKAGE, isPackageLesson);
        cValues.put(STUDENT_ID, studentId);
        int count = db.update(TABLE_Lessons, cValues, LESSON_ID+" = ?",new String[]{String.valueOf(lessonId)});
        return  count;
    }
        //endregion

    //region Driving Tests

    public long insertTestDetails(String location, String testDate, String testTime, String bookingNumber, int result, int studentId) {
        // Get the database to writable mode
        SQLiteDatabase db = this.getWritableDatabase();

        //A new ContentValues object to create map of values, the table columns are used as keys
        ContentValues cValues = new ContentValues();
        cValues.put(LOCATION, location);
        cValues.put(TEST_DATE, testDate);
        cValues.put(TEST_TIME, testTime);
        cValues.put(BOOKING_NUMBER, bookingNumber);
        cValues.put(RESULT, result);
        cValues.put(STUDENT_ID, studentId);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Driving_Tests, null, cValues);
        db.close();

        return newRowId;
    }

    public ArrayList<DrivingTest> getDrivingTests() {
        // AddingNewLessons
        //DrivingTest(String location, String date, String time, int bookingNumber, int result, int studentId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DrivingTest> testsList = new ArrayList<>();
        String query = "SELECT location, testDate, testTime, endTime, bookingNumber, result, id FROM " + TABLE_Driving_Tests;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                DrivingTest test = new DrivingTest(
                        cursor.getString(cursor.getColumnIndex(LOCATION))
                        , cursor.getString(cursor.getColumnIndex(TEST_DATE))
                        , cursor.getString(cursor.getColumnIndex(TEST_TIME))
                        , cursor.getString(cursor.getColumnIndex(BOOKING_NUMBER))
                        , cursor.getInt(cursor.getColumnIndex(RESULT))
                        , cursor.getInt(cursor.getColumnIndex(STUDENT_ID))
                );
                testsList.add(test);
                cursor.moveToNext();
            }
        }
        return testsList;
    }

    // Delete User Details
    public void deleteTest(int testId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Driving_Tests, TEST_ID+" = ?",new String[]{String.valueOf(testId)});
        db.close();
    }

    // Update User Details
    public int updateTestDetails(String location, String testDate, String testTime, int bookingNumber, int result, int studentId, int testId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(LOCATION, location);
        cValues.put(TEST_DATE, testDate);
        cValues.put(TEST_TIME, testTime);
        cValues.put(BOOKING_NUMBER, bookingNumber);
        cValues.put(RESULT, result);
        cValues.put(STUDENT_ID, studentId);
        int count = db.update(TABLE_Driving_Tests, cValues, TEST_ID+" = ?",new String[]{String.valueOf(testId)});
        return  count;
    }

    //endregion Driving Tests






////region Packages Queries
//
////endregion

}
