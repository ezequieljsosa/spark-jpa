package dds;

import javax.persistence.*;

@Entity
public class Multa {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String fecha;
	private String tipo;


	
	public Multa() {
		super();
	
	}
	
	public Multa(String fecha, String tipo) {
		super();
		this.fecha = fecha;
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
}
