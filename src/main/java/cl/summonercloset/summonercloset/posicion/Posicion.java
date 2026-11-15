package cl.summonercloset.summonercloset.posicion;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@JsonPropertyOrder({"id", "nombre", "icono"})
@Entity
@Table(name = "posicion")
public class Posicion {

    @Id
    @Column(length = 20)
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 40, message = "El nombre puede tener como maximo 40 caracteres")
    @Column(nullable = false, length = 40)
    private String nombre;

    @Size(max = 500, message = "La URL del icono puede tener como maximo 500 caracteres")
    @Column(length = 500)
    private String icono;

    public Posicion() {
    }

    public Posicion(String id, String nombre, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
}