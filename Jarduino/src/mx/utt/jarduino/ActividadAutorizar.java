/**
 * 
 */
package mx.utt.jarduino;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
public class ActividadAutorizar extends Activity {

	private EditText mEtNum1;
	private EditText mEtNum2;

	// JSON Response node names
	private static String KEY_UID = "id";
	private static String KEY_NAME = "nombre";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "subscribe_date";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actividad_autorizar);

		mEtNum1 = (EditText) findViewById(R.id.usuario);
		mEtNum2 = (EditText) findViewById(R.id.clave);

		Button btnPostRequest = (Button) findViewById(R.id.bAutorizar);
		btnPostRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

				final String num1 = mEtNum1.getText().toString();
				final String num2 = mEtNum2.getText().toString();
				if (num1 != null && !num1.equals("") && num2 != null && !num2.equals("")) {
					StringRequest myReq = new StringRequest(Method.POST,
							"http://10.0.2.2/~arabelera/Jarduino/public_html/android/logIn.php",
							createMyReqSuccessListener(), createMyReqErrorListener()) {

						protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
							Map<String, String> params = new HashMap<String, String>();
							params.put("Id", num1);
							params.put("contrasena", num2);
							return params;
						};
					};
					queue.add(myReq);
				}
			}
		});

	}

	private Response.Listener<String> createMyReqSuccessListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				if (response.equals("Usuario o Contrasena Incorrecto")) {
					Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

					// Store user details in SQLite Database
					DatabaseHelper db = new DatabaseHelper(getApplicationContext());
					JSONObject json_user;
					try {
						json_user = new JSONObject(response);
						funciones.logoutUser(getApplicationContext());
						db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL),
								json_user.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

					} catch (JSONException e) {
						e.printStackTrace();
					}

					// Launch Dashboard Screen
					Intent mp = new Intent(getApplicationContext(), ActividadMenu.class);
					startActivity(mp);

				}
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_principal, menu);
		return true;
	}

}
