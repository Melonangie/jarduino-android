package mx.utt.jarduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ActividadPlantas extends Activity {

	ListView lstPlantas;
	private ProgressDialog progreso;
	InputStream datos = null;
	JSONObject resultado = null;
	String resultadoTexto = "";
	String url = "http://10.0.2.2/~arabelera/Jarduino/public_html/android/plantas.php";
	private static final String ETIQUETA_PLANTAS = "plantas";
	private static final String ETIQUETA_NOMBRE = "name";
	private static final String ETIQUETA_ID = "id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_plantas);
		new CargarPlantas().execute(); // iniciar proceso de lectura de equipos
	}

	public class CargarPlantas extends AsyncTask<String, Void, ArrayList<Planta>> {
		ArrayList<Planta> plantas = new ArrayList<Planta>();

		@Override
		protected void onPreExecute() {
			// mostrar animacion de progreso
			super.onPreExecute();
			progreso = new ProgressDialog(ActividadPlantas.this);
			progreso.setMessage("Cargando las Plantas...");
			progreso.setIndeterminate(false);
			progreso.setCancelable(false);
			progreso.show();
		}

		@Override
		protected ArrayList<Planta> doInBackground(String... args) {
			// realizar peticion al servidor
			try {
				DefaultHttpClient cliente = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				HttpResponse respuesta = cliente.execute(post);
				HttpEntity entidad = respuesta.getEntity();
				datos = entidad.getContent();
			} catch (UnsupportedEncodingException e) {
				Log.e("Error de conexion", "Formato no permitido" + e.toString());
			} catch (ClientProtocolException e) {
				Log.e("Error de conexion", "Error de protocolo" + e.toString());
			} catch (IOException e) {
				Log.e("Error de conexion", "Error de lectura" + e.toString());
			} catch (Exception e) {
				Log.e("Error general", "Error general" + e.toString());
			}
			// leer datos del stream
			try {
				BufferedReader lector = new BufferedReader(new InputStreamReader(datos, "iso-8859-1"), 8);
				StringBuilder texto = new StringBuilder();
				String linea = null;
				while ((linea = lector.readLine()) != null) {
					texto.append(linea + "\n");
					Log.d("linea", linea);
				}
				datos.close();
				resultadoTexto = texto.toString();
			} catch (Exception e) {
				Log.e("Error de buffer", "Error al leer datos" + e.toString());
			}
			// convertir datos a objeto JSON
			try {
				resultado = new JSONObject(resultadoTexto);
			} catch (JSONException e) {
				Log.e("Error de conversion", "Error al convertir resultado" + e.toString());
			} catch (Exception e) {
				Log.e("Error de conversion", "Error al leer datos" + e.toString());
			}
			// pasar datos de objeto JSON a arreglo JSON
			try {
				JSONArray jsonPlantas = resultado.getJSONArray(ETIQUETA_PLANTAS);
				for (int i = 0; i < jsonPlantas.length(); i++) {
					// leer elemento del arreglo JSON
					JSONObject planta = jsonPlantas.getJSONObject(i);
					// agregar estado al array list
					plantas.add(new Planta(planta.getString(ETIQUETA_ID), planta.getString(ETIQUETA_NOMBRE)));
				}
			} catch (JSONException e) {
				Log.e("Error", "Error al leer arreglo de equipos" + e.getMessage());
			} catch (Exception e) {
				Log.e("Error de conversion a arreglo", "Error general" + e.getMessage());
			}
			// enviar resultado a onPosExecute
			return plantas;
		}

		@Override
		protected void onPostExecute(ArrayList<Planta> plantas) {
			// terminar barra
			progreso.dismiss();
			// list view
			lstPlantas = (ListView) findViewById(R.id.lstPlantas);

			// adaptador
			AdaptadorPlanta ae = new AdaptadorPlanta(ActividadPlantas.this, plantas);
			// mostrar lista
			lstPlantas.setAdapter(ae);
		}
	}
}
