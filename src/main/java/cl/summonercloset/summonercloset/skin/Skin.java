package cl.summonercloset.summonercloset.skin;

import cl.summonercloset.summonercloset.campeon.Campeon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import cl.summonercloset.summonercloset.chroma.Chroma;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({"id", "name", "image", "chromas"})
@Entity
@Table(name = "skin")
public class Skin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre puede tener como maximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "La imagen es obligatoria")
    @Size(max = 500, message = "La URL de la imagen puede tener como maximo 500 caracteres")
    @Column(nullable = false, length = 500)
    private String image;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "campeon", nullable = false)
    private Campeon campeon;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "skin", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<Chroma> chromas = new ArrayList<>();

    public Skin() {
    }

    public Skin(String name, String image, Campeon campeon) {
        this.name = name;
        this.image = image;
        this.campeon = campeon;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Campeon getCampeon() { return campeon; }
    public void setCampeon(Campeon campeon) { this.campeon = campeon; }

    public List<Chroma> getChromas() { return chromas; }
}