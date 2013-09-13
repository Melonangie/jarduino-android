/**
 * 
 */
package mx.utt.jarduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author arabelera
 * 
 */
public class ActividadArduino extends Activity {

	Button btnBomba, btnLuz, btnUv;
	TextView lblIdBomba, lblIdLuz, lblIdUv, lblStatusBomba, lblStatusLuz, lblStatusUv;
	int seleccion = 0;
	URLConnection conexion;
	private ProgressDialog progreso;
	ListView lstControles;
	InputStream datos = null;
	JSONObject resultado = null;
	String resultadoTexto = "";
	String url = "http://10.0.2.2/~arabelera/Jarduino/public_html/android/controles.php";
	private static final String ETIQUETA_CONTROLES = "manipular";
	private static final String ETIQUETA_ID = "id";
	private static final String ETIQUETA_EQUIPO = "equipo";
	private static final String ETIQUETA_STATUS = "status";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_arduino);
		btnBomba = (Button) findViewById(R.id.btnBomba);
		btnLuz = (Button) findViewById(R.id.btnLuz);
		btnUv = (Button) findViewById(R.id.btnUv);
		lblIdBomba = (TextView) findViewById(R.id.lblIdBomba);
		lblIdLuz = (TextView) findViewById(R.id.lblIdLuz);
		lblIdUv = (TextView) findViewById(R.id.lblIdUv);
		lblStatusBomba = (TextView) findViewById(R.id.lblStatusB);
		lblStatusLuz = (TextView) findViewById(R.id.lblStatusLuz);
		lblStatusUv = (TextView) findViewById(R.id.lblStatusUv);
		new CargarControles().execute();
		btnBomba.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				seleccion = 1;
				new PostearStatus().execute();
				if (lblStatusBomba.getText().equals("0")) {
					lblStatusBomba.setText("1");
					btnBomba.setBackgroundColor(Color.rgb(153, 204, 0));
					btnBomba.setText("Bomba Prendida");
				} else {
					if (lblStatusBomba.getText().equals("1")) {
						lblStatusBomba.setText("2");
						btnBomba.setBackgroundColor(Color.rgb(204, 0, 0));
						btnBomba.setText("Bomba Apagada");
					} else {
						if (lblStatusBomba.getText().equals("2")) {
							lblStatusBomba.setText("1");
							btnBomba.setBackgroundColor(Color.rgb(153, 204, 0));
							btnBomba.setText("Bomba Prendida");
						}
					}
				}

			}
		});
		btnLuz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				seleccion = 2;
				new PostearStatus().execute();
				if (lblStatusLuz.getText().equals("0")) {
					lblStatusLuz.setText("1");
					btnLuz.setBackgroundColor(Color.rgb(153, 204, 0));
					btnLuz.setText("Lampara de Luz Prendida");
				} else {
					if (lblStatusLuz.getText().equals("1")) {
						lblStatusLuz.setText("2");
						btnLuz.setBackgroundColor(Color.rgb(204, 0, 0));
						btnLuz.setText("Lampara de Luz Apagada");
					} else {
						if (lblStatusLuz.getText().equals("2")) {
							lblStatusLuz.setText("1");
							btnLuz.setBackgroundColor(Color.rgb(153, 204, 0));
							btnLuz.setText("Lampara de Luz Prendida");
						}
					}
				}
			}
		});
		btnUv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				seleccion = 3;
				new PostearStatus().execute();
				if (lblStatusUv.getText().equals("0")) {
					lblStatusUv.setText("1");
					btnUv.setBackgroundColor(Color.rgb(153, 204, 0));
					btnUv.setText("Lampara Uva Prendida");
				} else {
					if (lblStatusUv.getText().equals("1")) {
						lblStatusUv.setText("2");
						btnUv.setBackgroundColor(Color.rgb(204, 0, 0));
						btnUv.setText("Lampara Uva Apagada");
					} else {
						if (lblStatusUv.getText().equals("2")) {
							lblStatusUv.setText("1");
							btnUv.setBackgroundColor(Color.rgb(153, 204, 0));
							btnUv.setText("Lampara Uva Prendida");
						}
					}
				}
			}
		});
	}

	public class CargarControles extends AsyncTask<String, Void, ArrayList<Control>> {
		ArrayList<Control> controles = new ArrayList<Control>();

		@Override
		protected void onPreExecute() {
			// mostrar animacion de progreso
			super.onPreExecute();
			progreso = new ProgressDialog(ActividadArduino.this);
			progreso.setMessage("Cargando los controles...");
			progreso.setIndeterminate(false);
			progreso.setCancelable(false);
			progreso.show();
		}

		@Override
		protected ArrayList<Control> doInBackground(String... args) {
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
				JSONArray jsonControles = resultado.getJSONArray(ETIQUETA_CONTROLES);
				for (int i = 0; i < jsonControles.length(); i++) {
					// leer elemento del arreglo JSON
					JSONObject control = jsonControles.getJSONObject(i);
					// agregar estado al array lista
					controles.add(new Control(control.getString(ETIQUETA_ID), control
							.getString(ETIQUETA_EQUIPO), control.getString(ETIQUETA_STATUS)));
				}
			} catch (JSONException e) {
				Log.e("Error", "Error al leer arreglo de equipos" + e.getMessage());
			} catch (Exception e) {
				Log.e("Error de conversion a arreglo", "Error general" + e.getMessage());
			}
			// enviar resultado a onPosExecute
			return controles;
		}

		@Override
		protected void onPostExecute(ArrayList<Control> controles) {
			progreso.dismiss();
			for (Control c : controles) {

				if (c.getEquipo().equals("LUv")) {
					lblIdUv.setText(c.getId().toString());
					if (c.getStatus().equals("0")) {
						btnUv.setBackgroundColor(Color.rgb(204, 0, 0));
						btnUv.setText("Lampara Uv Apagada");
						lblStatusUv.setText("0");
					} else {
						if (c.getStatus().equals("1")) {
							btnUv.setBackgroundColor(Color.rgb(153, 204, 0));
							btnUv.setText("Lampara Uva Prendida");
							lblStatusUv.setText("1");
						} else {
							btnUv.setBackgroundColor(Color.rgb(204, 0, 0));
							btnUv.setText("Lampara Uv Apagada");
							lblStatusUv.setText("2");
						}
					}
				}
				if (c.getEquipo().equals("LL")) {
					lblIdLuz.setText(c.getId().toString());

					if (c.getStatus().equals("0")) {
						btnLuz.setBackgroundColor(Color.rgb(204, 0, 0));
						btnLuz.setText("Lampara de Luz Apagada");
						lblStatusLuz.setText("0");
					} else {
						if (c.getStatus().equals("1")) {
							btnLuz.setBackgroundColor(Color.rgb(153, 204, 0));
							btnLuz.setText("Lampara de Luz Prendida");
							lblStatusLuz.setText("1");
						} else {
							btnLuz.setBackgroundColor(Color.rgb(204, 0, 0));
							btnLuz.setText("Lampara de Luz Apagada");
							lblStatusLuz.setText("2");
						}
					}
				}
				if (c.getEquipo().equals("BA")) {
					lblIdBomba.setText(c.getId().toString());

					if (c.getStatus().equals("0")) {
						btnBomba.setBackgroundColor(Color.rgb(204, 0, 0));
						btnBomba.setText("Bomba Apagada");
						lblStatusBomba.setText("0");
					} else {
						if (c.getStatus().equals("1")) {
							btnBomba.setBackgroundColor(Color.rgb(153, 204, 0));
							btnBomba.setText("Bomba Prendida");
							lblStatusBomba.setText("1");
						} else {
							btnBomba.setBackgroundColor(Color.rgb(204, 0, 0));
							btnBomba.setText("Bomba Apagada");
							lblStatusBomba.setText("2");
						}
					}
				}

			}
		}
	}

	public class PostearStatus extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// mostrar animacion de progreso
			super.onPreExecute();
			progreso = new ProgressDialog(ActividadArduino.this);
			progreso.setMessage("Actualizando...");
			progreso.setIndeterminate(false);
			progreso.setCancelable(false);
			progreso.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// variables
			String resultado = "";

			String id = "";
			String status = "";
			switch (seleccion) {
			case 1:
				id = (lblIdBomba.getText().toString());
				if (lblStatusBomba.getText().equals("0") || lblStatusBomba.getText().equals("2")) {
					status = "1";
				}
				if (lblStatusBomba.getText().equals("1")) {
					status = "2";
				}
				break;
			case 2:
				id = (lblIdLuz.getText().toString());
				if (lblStatusLuz.getText().equals("o") || lblStatusLuz.getText().equals("2")) {
					status = "1";
				}
				if (lblStatusLuz.getText().equals("1")) {
					status = "2";
				}
				break;
			case 3:
				id = (lblIdUv.getText().toString());
				if (lblStatusUv.getText().equals("o") || lblStatusUv.getText().equals("2")) {
					status = "1";
				}
				if (lblStatusUv.getText().equals("1")) {
					status = "2";
				}
				break;
			}
			// datos que seran enviados al server
			try {
				String datos = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
				datos += "&" + URLEncoder.encode("status", "UTF-8") + "="
						+ URLEncoder.encode(status, "UTF-8");

				// url que recibe los datos del servicio
				URL url = new URL("http://10.0.2.2/~arabelera/Jarduino/public_html/android/manipulacion.php");

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
				resultado = "No se pudo logear usuario";
			} catch (MalformedURLException e) {
				resultado = "No se pudo logear usuario";
			} catch (IOException e) {
				resultado = "No se pudo logear usuario";
			}

			// regresar resultado
			return resultado;
		}

		@Override
		protected void onPostExecute(String resultado) {
			// terminar barra
			progreso.dismiss();
			Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();

		}

	}
}
