package mx.utt.jarduino;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ActividadMenu extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_menu);

		// Acciones del menu
		ImageButton bkit = (ImageButton) findViewById(R.id.btn_pqtes);
		ImageButton buser = (ImageButton) findViewById(R.id.btn_user);
		ImageButton bbnewkit = (ImageButton) findViewById(R.id.btn_agregar);
		ImageButton barduino = (ImageButton) findViewById(R.id.btn_arduino);

		/* Agregar Paquete */
		bkit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent newkit = new Intent(getApplicationContext(), ActividadPaquete.class);
				startActivity(newkit);
			}
		});

		/* Ver Paquetes */
		bbnewkit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent kit = new Intent(getApplicationContext(), ActividadAgregar.class);
				startActivity(kit);
			}
		});

		/* Ver Usuarios */
		buser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent uaurios = new Intent(getApplicationContext(), ActividadUsuario.class);
				startActivity(uaurios);
			}
		});

		/* Arduino */
		barduino.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent arduino = new Intent(getApplicationContext(), ActividadArduino.class);
				startActivity(arduino);
			}
		});

	}

}
