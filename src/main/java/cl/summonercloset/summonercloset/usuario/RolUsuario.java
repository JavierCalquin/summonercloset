package cl.summonercloset.summonercloset.usuario;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@JsonPropertyOrder({"id", "nombre"})
@Entity
@Table(name = "rol_usuario")
public class RolUsuario {

    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 40)
    private String nombre;

    public RolUsuario() {
    }

    public RolUsuario(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}