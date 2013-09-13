/**
 * 
 */
package mx.utt.jarduino;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author arabelera
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_KIT = "paquete";

	// Login Table Columns names
	private static final String KEY_KIT_ID = "idlocal";
	private static final String KEY_KIT_KITID = "id";
	private static final String KEY_KIT_NAME = "nombre";
	private static final String KEY_KIT_TEMP = "temp";
	private static final String KEY_KIT_HUM = "hum";
	private static final String KEY_KIT_UV = "uv";
	private static final String KEY_KIT_LUM = "lum";

	// Login table name
	private static final String TABLE_LOGIN = "login";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_UID + " TEXT UNIQUE," + KEY_CREATED_AT
				+ " TEXT" + ")";

		String CREATE_KIT_TABLE = "CREATE TABLE " + TABLE_KIT + "(" + KEY_KIT_ID + " INTEGER PRIMARY KEY,"
				+ KEY_KIT_KITID + " TEXT UNIQUE," + KEY_KIT_NAME + " TEXT," + KEY_KIT_TEMP + " TEXT,"
				+ KEY_KIT_HUM + " TEXT," + KEY_KIT_UV + " TEXT," + KEY_KIT_LUM + " TEXT" + ")";

		db.execSQL(CREATE_LOGIN_TABLE);
		db.execSQL(CREATE_KIT_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email, String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting user data from database
	 * */
	/*
	 * public HashMap<String, String> getUserDetails() { HashMap<String, String>
	 * user = new HashMap<String, String>(); String selectQuery =
	 * "SELECT  * FROM " + TABLE_LOGIN;
	 * 
	 * SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor =
	 * db.rawQuery(selectQuery, null); // Move to first row
	 * cursor.moveToFirst(); if (cursor.getCount() > 0) { user.put("id",
	 * cursor.getString(1)); user.put("name", cursor.getString(2));
	 * user.put("nombre", cursor.getString(3)); user.put("created_at",
	 * cursor.getString(4)); user.put("email", cursor.getString(5)); }
	 * cursor.close(); db.close(); // return user return user; }
	 */

	/**
	 * Getting user data from database
	 * */
	public ArrayList<Paquete> getUserDetails() {
		ArrayList<Paquete> lista = new ArrayList<Paquete>();

		String selectQuery = "SELECT  * FROM " + TABLE_KIT;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			String id = cursor.getString(1);
			String name = cursor.getString(2);
			String temp = cursor.getString(3);
			String hum = cursor.getString(4);
			String uv = cursor.getString(5);
			String lum = cursor.getString(6);

			lista.add(new Paquete(id, name, temp, hum, uv, lum));
			cursor.moveToNext();

		}
		cursor.close();
		db.close();
		// return user
		return lista;
	}

	/**
	 * Getting user id from database
	 * */
	/*
	 * public String getUserId() { String id = ""; String selectQuery =
	 * "SELECT  KEY_UID FROM " + TABLE_LOGIN;
	 * 
	 * SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor =
	 * db.rawQuery(selectQuery, null); // Move to first row
	 * cursor.moveToFirst(); if (cursor.getCount() > 0) { id =
	 * cursor.getString(1); } cursor.close(); db.close(); // return user return
	 * id; }
	 */

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}

	/**
	 * Storing kit details in database
	 * */
	public void addPaquete(String id, String name, String temp, String hum, String uv, String lum) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_KIT_KITID, id); // Name
		values.put(KEY_KIT_NAME, name); // Name
		values.put(KEY_KIT_TEMP, temp); // Email
		values.put(KEY_KIT_HUM, hum); // Email
		values.put(KEY_KIT_UV, uv); // Created At
		values.put(KEY_KIT_LUM, lum); // Created At
		// Inserting Row
		db.insert(TABLE_KIT, null, values);
		db.close(); // Closing database connection
	}

	/**
	 * Getting user data from database
	 * */
	public ArrayList<Paquete> getPaqueteDetails() {
		ArrayList<Paquete> lista = new ArrayList<Paquete>();

		String selectQuery = "SELECT  * FROM " + TABLE_KIT;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			String id = cursor.getString(1);
			String name = cursor.getString(2);
			String temp = cursor.getString(3);
			String hum = cursor.getString(4);
			String uv = cursor.getString(5);
			String lum = cursor.getString(6);

			lista.add(new Paquete(id, name, temp, hum, uv, lum));
			cursor.moveToNext();

		}
		cursor.close();
		db.close();
		// return user
		return lista;
	}

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCountKit() {
		String countQuery = "SELECT  * FROM " + TABLE_KIT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void resetTableKit() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_KIT, null, null);
		db.close();
	}

}
