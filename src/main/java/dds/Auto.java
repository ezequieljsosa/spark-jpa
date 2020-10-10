package dds;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auto implements Cloneable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String patente;
    private String modelo;
    private String marca;
    private int anio;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_id")
    private List<Multa> multas;

    public Auto() {
        this.multas = new ArrayList<>();
    }

    public List<Multa> getMultas() {
        return new ArrayList<>(this.multas);
    }

    public void add(Multa m) {
        this.multas.add(m);
    }


    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Auto(String patente, String modelo, String marca, int anio) {
        this();
        this.patente = patente;
        this.modelo = modelo;
        this.marca = marca;
        this.anio = anio;
    }

    @Override
    public Auto clone() {
        try {
            return (Auto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
