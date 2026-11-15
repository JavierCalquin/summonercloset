package cl.summonercloset.summonercloset.chroma;

import cl.summonercloset.summonercloset.skin.Skin;
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


@JsonPropertyOrder({"id", "name", "image"})
@Entity
@Table(name = "chroma")
public class Chroma {

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
    @JoinColumn(name = "skin", nullable = false)
    private Skin skin;

    public Chroma() {
    }

    public Chroma(String name, String image, Skin skin) {
        this.name = name;
        this.image = image;
        this.skin = skin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Skin getSkin() { return skin; }
    public void setSkin(Skin skin) { this.skin = skin; }
}