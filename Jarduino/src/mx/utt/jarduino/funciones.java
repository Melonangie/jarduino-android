/**
 * 
 */
package mx.utt.jarduino;

import java.util.ArrayList;

import android.content.Context;

/**
 * @author arabelera
 * 
 */
public class funciones {

	/**
	 * Function get Login status
	 * */
	public static boolean isUserLoggedIn(Context context) {
		DatabaseHelper db = new DatabaseHelper(context);
		int count = db.getRowCount();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public static boolean logoutUser(Context context) {
		DatabaseHelper db = new DatabaseHelper(context);
		db.resetTables();
		return true;
	}

	/**
	 * Function to get user id
	 * */
	/*
	 * public static String getUserId(Context context) { DatabaseHelper db = new
	 * DatabaseHelper(context); String id = db.getUserId(); return id; }
	 */

	/**
	 * Function get kit status
	 * */
	public static boolean doesPaqueteExist(Context context) {
		DatabaseHelper db = new DatabaseHelper(context);
		int count = db.getRowCountKit();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

	/**
	 * Function to logout user Reset Database
	 * */
	public static boolean clearPaquetes(Context context) {
		DatabaseHelper db = new DatabaseHelper(context);
		db.resetTableKit();
		return true;
	}

	/**
	 * Getting user data from database
	 * */
	public static ArrayList<Paquete> getUserDetails(Context context) {
		DatabaseHelper db = new DatabaseHelper(context);
		ArrayList<Paquete> user = db.getUserDetails();

		return user;
	}
}
