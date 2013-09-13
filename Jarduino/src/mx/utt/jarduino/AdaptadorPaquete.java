/**
 * 
 */
package mx.utt.jarduino;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author arabelera
 * 
 */
public class AdaptadorPaquete extends BaseAdapter {

	/* Variables del Adaptador */
	private ArrayList<Paquete> lista_paquetes;
	private LayoutInflater inflador = null;;

	/**
	 * Constructor del Adaptador
	 */
	public AdaptadorPaquete(Context context, ArrayList<Paquete> equipos) {

		this.lista_paquetes = equipos;
		this.inflador = LayoutInflater.from(context);
	}

	/*
	 * Muestra el tamalo del array Equipos
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {

		return lista_paquetes.size();
	}

	/*
	 * Regrea el equipo en la posicion especificada
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {

		return lista_paquetes.get(position);
	}

	/*
	 * Regresa la posicion del ID en la posicion
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {

		return position;
	}

	/*
	 * Regresa la vista por renglon
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			convertView = inflador.inflate(R.layout.renglon_paquete, parent, false);

			holder = new ViewHolder();
			holder.txtClave = (TextView) convertView.findViewById(R.id.lblClave);
			holder.txtDescripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
			holder.txtTemp = (TextView) convertView.findViewById(R.id.lblTemp);
			holder.txtHum = (TextView) convertView.findViewById(R.id.lblHum);
			holder.txtUv = (TextView) convertView.findViewById(R.id.lblUv);
			holder.txtLuz = (TextView) convertView.findViewById(R.id.lblLuz);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtClave.setText(lista_paquetes.get(position).getId());
		holder.txtDescripcion.setText(lista_paquetes.get(position).getNombre());
		holder.txtTemp.setText("Temp: " + lista_paquetes.get(position).getTemp());
		holder.txtHum.setText("Hum: " + lista_paquetes.get(position).getHum());
		holder.txtUv.setText("Uva: " + lista_paquetes.get(position).getUv());
		holder.txtLuz.setText("Luz: " + lista_paquetes.get(position).getLum());

		return convertView;
	}

	/* Contenedor de la vista */
	static class ViewHolder {

		TextView txtClave;
		TextView txtDescripcion;
		TextView txtTemp;
		TextView txtHum;
		TextView txtUv;
		TextView txtLuz;
	}

}
