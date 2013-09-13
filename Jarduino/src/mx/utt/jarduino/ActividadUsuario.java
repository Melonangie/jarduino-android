/**
 * 
 */
package mx.utt.jarduino;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author arabelera
 * 
 */
public class ActividadUsuario extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_usuario);

		ArrayList<Paquete> user = funciones.getUserDetails(getApplicationContext());

		TextView tvnombre = (TextView) findViewById(R.id.tvnombre);
		TextView tvemail = (TextView) findViewById(R.id.tvemail);
		TextView tvusuario = (TextView) findViewById(R.id.tvusuario);

		if (user != null) {
			tvnombre.setText(user.get(0).toString());
			tvemail.setText(user.get(1).toString());
			tvusuario.setText(user.get(2).toString());
		}

		Button btnLogout = (Button) findViewById(R.id.salir);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				funciones.logoutUser(getApplicationContext());
				Intent login = new Intent(getApplicationContext(), ActividadAutorizar.class);
				login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(login);
				// Closing dashboard screen
				finish();
			}
		});

	}
}
