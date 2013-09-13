/**
 * 
 */
package mx.utt.jarduino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * @author arabelera
 * 
 */
public class ActividadPaquete extends ListActivity {

	// JSON nodes
	private static final String KEY_KITS = "paquetes";
	private static final String KEY_KITID = "id";
	private static final String KEY_NAME = "nombre";
	private static final String KEY_TEMP = "temp";
	private static final String KEY_HUM = "hum";
	private static final String KEY_UV = "uv";
	private static final String KEY_LUM = "lum";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.actividad_paquete);

		// final String id = funciones.getUserId(getApplicationContext());

		RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

		StringRequest myReq = new StringRequest(Method.POST,
				"http://10.0.2.2/~arabelera/Jarduino/public_html/android/paquetes.php",
				createMyReqSuccessListener(), createMyReqErrorListener()) {

			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				// params.put("Id", id);
				return params;
			};
		};
		queue.add(myReq);
	}

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// Store user details in SQLite Database
				DatabaseHelper db = new DatabaseHelper(getApplicationContext());

				// arreglo de marcas
				ArrayList<Paquete> lista_paquetes = new ArrayList<Paquete>();

				try {
					JSONObject resultado = new JSONObject(response);
					JSONArray jsonPaquetes = resultado.getJSONArray(KEY_KITS);
					for (int i = 0; i < jsonPaquetes.length(); i++) {
						// leer elemento del arreglo JSON
						JSONObject paquete = jsonPaquetes.getJSONObject(i);

						// Storing each json item in variable
						String id = paquete.getString(KEY_KITID);
						String nombre = paquete.getString(KEY_NAME);
						String temp = paquete.getString(KEY_TEMP);
						String hum = paquete.getString(KEY_HUM);
						String uv = paquete.getString(KEY_UV);
						String lum = paquete.getString(KEY_LUM);

						db.addPaquete(id, nombre, temp, hum, uv, lum);

						// adding each child node topaquete object
						lista_paquetes.add(new Paquete(id, nombre, temp, hum, uv, lum));
					}

				} catch (JSONException e) {
					Log.e("Error", "Error al leer arreglo de marca" + e.toString());
				} catch (Exception e) {
					Log.e("Error de conversion a arreglo", "Error general" + e.toString());
				}

				// Carga la lista de equipos mediante ele adaptador de lista
				ActividadPaquete.this.setListAdapter(new AdaptadorPaquete(getApplicationContext(),
						lista_paquetes));
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
			}
		};
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(this, ActividadPlantas.class);
		// intent.putExtra("position", position); // not what you want
		intent.putExtra("id", id); // more likely what you want
		startActivity(intent);
	}

}
