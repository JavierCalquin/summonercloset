package cl.summonercloset.summonercloset.campeon;

import cl.summonercloset.summonercloset.posicion.Posicion;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import cl.summonercloset.summonercloset.skin.Skin;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import cl.summonercloset.summonercloset.favorito.Favorito;
import com.fasterxml.jackson.annotation.JsonIgnore;


@JsonPropertyOrder({"id", "name", "role", "image", "description", "skins"})
@Entity
@Table(name = "campeon")
public class Campeon {

    @Id
    @NotBlank(message = "El id es obligatorio")
    @Size(max = 40, message = "El id puede tener como maximo 40 caracteres")
    @Pattern(regexp = "[a-z0-9_]+", message = "El id solo puede tener minusculas, numeros y _")
    @Column(length = 40)
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "El nombre puede tener como maximo 60 caracteres")
    @Column(nullable = false, length = 60)
    private String name;

    @NotNull(message = "La posicion es obligatoria")
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private Posicion role;

    @NotBlank(message = "La imagen es obligatoria")
    @Size(max = 500, message = "La URL de la imagen puede tener como maximo 500 caracteres")
    @Column(nullable = false, length = 500)
    private String image;

    @Size(max = 500, message = "La descripcion puede tener como maximo 500 caracteres")
    @Column(length = 500)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "campeon", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")

    private List<Skin> skins = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "campeon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorito> favoritos = new ArrayList<>();

    public Campeon() {
    }

    public Campeon(String id, String name, Posicion role, String image, String description) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.image = image;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Posicion getRole() { return role; }
    public void setRole(Posicion role) { this.role = role; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Skin> getSkins() { return skins; }
}