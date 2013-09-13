package mx.utt.jarduino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActividadPrincipal extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Check login status in database
		if (funciones.isUserLoggedIn(getApplicationContext())) {
			// user is not logged in show login screen
			Intent login = new Intent(getApplicationContext(), ActividadMenu.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();

		} else {
			// user is not logged in show login screen
			Intent login = new Intent(getApplicationContext(), ActividadAutorizar.class);
			login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();
		}
	}
}
