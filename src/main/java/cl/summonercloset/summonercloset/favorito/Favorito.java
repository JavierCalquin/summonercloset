package cl.summonercloset.summonercloset.favorito;

import cl.summonercloset.summonercloset.campeon.Campeon;
import cl.summonercloset.summonercloset.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name = "favorito", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario", "campeon"}))
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "campeon", nullable = false)
    private Campeon campeon;

    public Favorito() {
    }

    public Favorito(Usuario usuario, Campeon campeon) {
        this.usuario = usuario;
        this.campeon = campeon;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Campeon getCampeon() { return campeon; }
}