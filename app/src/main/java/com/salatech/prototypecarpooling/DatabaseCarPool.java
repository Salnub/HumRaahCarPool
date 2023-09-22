package com.salatech.prototypecarpooling;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCarPool extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CarpoolApp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "Users";
    public static final String TABLE_RIDES = "Rides";
    public static final String TABLE_CARS = "Cars";

    // Columns for the Users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_NUMBER = "number";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    // Columns for the Rides table
    public static final String COLUMN_RIDE_ID = "id";
    public static final String COLUMN_RIDE_DRIVER_ID = "driver_id";
    public static final String COLUMN_RIDE_DEPARTURE_LOCATION = "departure_location";
    public static final String COLUMN_RIDE_DESTINATION = "destination";
    public static final String COLUMN_RIDE_DEPARTURE_TIME = "departure_time";

    // Columns for the Cars table
    public static final String COLUMN_CAR_ID = "id";
    public static final String COLUMN_CAR_OWNER_ID = "owner_id";
    public static final String COLUMN_CAR_MAKE = "make";
    public static final String COLUMN_CAR_MODEL = "model";
    public static final String COLUMN_CAR_YEAR = "year";

    public DatabaseCarPool(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_NUMBER + " TEXT, "
                + COLUMN_USER_EMAIL + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT)");

        // Create Cars table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CARS + " ("
                + COLUMN_CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CAR_OWNER_ID + " INTEGER, "
                + COLUMN_CAR_MAKE + " TEXT, "
                + COLUMN_CAR_MODEL + " TEXT, "
                + COLUMN_CAR_YEAR + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_CAR_OWNER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))");

        // Create Rides table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RIDES + " ("
                + COLUMN_RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RIDE_DRIVER_ID + " INTEGER, "
                + COLUMN_RIDE_DEPARTURE_LOCATION + " TEXT, "
                + COLUMN_RIDE_DESTINATION + " TEXT, "
                + COLUMN_RIDE_DEPARTURE_TIME + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_RIDE_DRIVER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades, if needed
        // For a simple example, you can drop and recreate tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDES);
        onCreate(db);
    }

    // Method to check if an email exists in the database
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ?";
            cursor = db.rawQuery(query, new String[]{email});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    // Method to insert user data into the database
    public boolean insertData(String name,String number, String email, String password) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        contentValues.put("name", name);
        contentValues.put("number",number);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("USERS", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from USERS where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
