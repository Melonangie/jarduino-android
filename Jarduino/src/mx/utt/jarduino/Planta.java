package mx.utt.jarduino;

public class Planta {
	private String id, nombre;

	public String getNombre() {
		return nombre;
	}

	public String getId() {
		return id;
	}

	public Planta() {
		nombre = "";
		id = "";
	}

	public Planta(String clave, String nombrePlanta) {
		id = clave;
		nombre = nombrePlanta;
	}
}
