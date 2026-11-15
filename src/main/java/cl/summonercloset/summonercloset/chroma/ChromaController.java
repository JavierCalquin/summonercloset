package cl.summonercloset.summonercloset.chroma;

import cl.summonercloset.summonercloset.skin.Skin;
import cl.summonercloset.summonercloset.skin.SkinRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class ChromaController {

    private final ChromaRepository repository;
    private final SkinRepository skinRepository;

    public ChromaController(ChromaRepository repository, SkinRepository skinRepository) {
        this.repository = repository;
        this.skinRepository = skinRepository;
    }

    @GetMapping("/skins/{skinId}/chromas")
    public List<Chroma> listar(@PathVariable Long skinId) {
        buscarSkin(skinId);
        return repository.findBySkinIdOrderById(skinId);
    }

    @GetMapping("/chromas/{id}")
    public Chroma obtener(@PathVariable Long id) {
        return buscar(id);
    }

    @PostMapping("/skins/{skinId}/chromas")
    public ResponseEntity<Chroma> crear(@PathVariable Long skinId, @Valid @RequestBody Chroma chroma) {
        chroma.setId(null);
        chroma.setSkin(buscarSkin(skinId));
        Chroma guardado = repository.save(chroma);
        return ResponseEntity.created(URI.create("/api/chromas/" + guardado.getId())).body(guardado);
    }

    @PutMapping("/chromas/{id}")
    public Chroma actualizar(@PathVariable Long id, @Valid @RequestBody Chroma datos) {
        Chroma chroma = buscar(id);
        chroma.setName(datos.getName());
        chroma.setImage(datos.getImage());
        return repository.save(chroma);
    }

    @DeleteMapping("/chromas/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        repository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Chroma buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chroma " + id + " no existe"));
    }

    private Skin buscarSkin(Long skinId) {
        return skinRepository.findById(skinId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin " + skinId + " no existe"));
    }
}