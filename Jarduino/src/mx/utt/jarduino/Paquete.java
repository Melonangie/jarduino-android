/**
 * 
 */
package mx.utt.jarduino;

/**
 * @author arabelera
 * 
 */
public class Paquete {
	private String id, nombre, temp, hum, uv, lum;

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTemp() {
		return temp;
	}

	public String getHum() {
		return hum;
	}

	public String getUv() {
		return uv;
	}

	public String getLum() {
		return lum;
	}

	public Paquete() {
		id = "";
		nombre = "";
		temp = "";
		hum = "";
		uv = "";
		lum = "";
	}

	public Paquete(String idPaquete, String name, String temperatura, String humedad, String uva, String luma) {
		id = idPaquete;
		nombre = name;
		temp = temperatura;
		hum = humedad;
		uv = uva;
		lum = luma;
	}
}
