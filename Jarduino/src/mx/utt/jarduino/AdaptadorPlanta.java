package mx.utt.jarduino;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorPlanta extends BaseAdapter {

	private Activity actividad;
	private ArrayList<Planta> planta;
	private static LayoutInflater inflador = null;

	public AdaptadorPlanta(Activity a, ArrayList<Planta> datos) {
		actividad = a;
		planta = datos;
		inflador = (LayoutInflater) actividad.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return planta.size();
	}

	public Object getItem(int posicion) {
		return posicion;
	}

	public long getItemId(int posicion) {
		return posicion;
	}

	public View getView(int posicion, View convertirVista, ViewGroup padre) {
		View v = convertirVista;

		if (v == null)
			v = inflador.inflate(R.layout.renglon_planta, null);

		TextView txtNombre = (TextView) v.findViewById(R.id.lblNombre);
		ImageView imgPlanta = (ImageView) v.findViewById(R.id.imgPlanta);
		Planta p = planta.get(posicion);

		txtNombre.setText(p.getNombre());

		if (p.getNombre().equals("tomates")) {
			imgPlanta.setImageResource(R.drawable.tomate);
		} else if (p.getNombre().equals("chiles")) {
			imgPlanta.setImageResource(R.drawable.chili);
		} else if (p.getNombre().equals("pepinos")) {
			imgPlanta.setImageResource(R.drawable.pepinos);
		} else if (p.getNombre().equals("rabanos")) {
			imgPlanta.setImageResource(R.drawable.rabanos);
		} else if (p.getNombre().equals("cebollas")) {
			imgPlanta.setImageResource(R.drawable.cebollas);
		} else {
			imgPlanta.setImageResource(R.drawable.def);
		}

		return v;
	}
}
