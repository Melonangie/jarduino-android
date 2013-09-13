/**
 * 
 */
package mx.utt.jarduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author arabelera
 * 
 */
public class ActividadAgregar extends Activity {
	EditText ediNombre, ediTemperatura, ediHumedad, ediUv, ediLuz;
	Button btnAceptar;
	ProgressDialog progreso;
	URLConnection conexion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_agregarplanta);

		ediNombre = (EditText) findViewById(R.id.ediNombre);
		ediTemperatura = (EditText) findViewById(R.id.ediTemp);
		ediHumedad = (EditText) findViewById(R.id.ediHumedad);
		ediUv = (EditText) findViewById(R.id.ediUv);
		ediLuz = (EditText) findViewById(R.id.ediLuz);
		btnAceptar = (Button) findViewById(R.id.btnAceptar);

		btnAceptar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!ediNombre.getText().toString().equals("")
						& !ediTemperatura.getText().toString().equals("")
						& !ediHumedad.getText().toString().equals("")
						& !ediUv.getText().toString().equals("") & !ediLuz.getText().toString().equals(""))
					new AgregarPlanta().execute();
				else
					Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT)
							.show();
			}
		});
	}

	public class AgregarPlanta extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// mostrar animacion de progreso
			super.onPreExecute();
			progreso = new ProgressDialog(ActividadAgregar.this);
			progreso.setMessage("Agregando...");
			progreso.setIndeterminate(false);
			progreso.setCancelable(false);
			progreso.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// variables
			String resultado = "";
			String nombre = (ediNombre.getText().toString());
			String temp = (ediTemperatura.getText().toString());
			String hum = (ediHumedad.getText().toString());
			String uv = (ediUv.getText().toString());
			String luz = (ediLuz.getText().toString());

			// datos que seran enviados al server
			try {
				String datos = URLEncoder.encode("nombre", "UTF-8") + "="
						+ URLEncoder.encode(nombre, "UTF-8");
				datos += "&" + URLEncoder.encode("temp", "UTF-8") + "=" + URLEncoder.encode(temp, "UTF-8");
				datos += "&" + URLEncoder.encode("humedad", "UTF-8") + "=" + URLEncoder.encode(hum, "UTF-8");
				datos += "&" + URLEncoder.encode("uv", "UTF-8") + "=" + URLEncoder.encode(uv, "UTF-8");
				datos += "&" + URLEncoder.encode("luz", "UTF-8") + "=" + URLEncoder.encode(luz, "UTF-8");

				// url que recibe los datos del servicio
				URL url = new URL(
						"http://10.0.2.2/~arabelera/Jarduino/public_html/android/agregarPaquete.php");

				// postear datos (POST)
				conexion = url.openConnection();// conexion al servicio
				conexion.setDoOutput(true);// permite enviar datos
				OutputStreamWriter escritor = new OutputStreamWriter(conexion.getOutputStream());
				escritor.write(datos);// enviar datos
				escritor.flush();// limpia escritor

				// recibir respuesta del servicio
				BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				StringBuilder texto = new StringBuilder();
				String linea = null;

				// leer respuesta linea por linea
				while ((linea = lector.readLine()) != null) {
					texto.append(linea);// concatenar a texto
				}
				resultado = texto.toString();// leer texto del StringBuilder

			} catch (UnsupportedEncodingException e) {
				resultado = "error en el encode";
			} catch (MalformedURLException e) {
				resultado = "la url no es valida";
			} catch (IOException e) {
				resultado = e.toString();
			}

			// regresar resultado
			return resultado;
		}

		@Override
		protected void onPostExecute(String resultado) {
			// terminar barra
			progreso.dismiss();
			if (resultado.equals("Planta Agregado")) {
				Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();

		}

	}
}
