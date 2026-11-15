package cl.summonercloset.summonercloset.skin;

import cl.summonercloset.summonercloset.campeon.Campeon;
import cl.summonercloset.summonercloset.campeon.CampeonRepository;
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
public class SkinController {

    private final SkinRepository repository;
    private final CampeonRepository campeonRepository;

    public SkinController(SkinRepository repository, CampeonRepository campeonRepository) {
        this.repository = repository;
        this.campeonRepository = campeonRepository;
    }

    @GetMapping("/campeones/{campeonId}/skins")
    public List<Skin> listar(@PathVariable String campeonId) {
        buscarCampeon(campeonId);
        return repository.findByCampeonIdOrderById(campeonId);
    }

    @GetMapping("/skins/{id}")
    public Skin obtener(@PathVariable Long id) {
        return buscar(id);
    }

    @PostMapping("/campeones/{campeonId}/skins")
    public ResponseEntity<Skin> crear(@PathVariable String campeonId, @Valid @RequestBody Skin skin) {
        skin.setId(null);
        skin.setCampeon(buscarCampeon(campeonId));
        Skin guardada = repository.save(skin);
        return ResponseEntity.created(URI.create("/api/skins/" + guardada.getId())).body(guardada);
    }

    @PutMapping("/skins/{id}")
    public Skin actualizar(@PathVariable Long id, @Valid @RequestBody Skin datos) {
        Skin skin = buscar(id);
        skin.setName(datos.getName());
        skin.setImage(datos.getImage());
        return repository.save(skin);
    }

    @DeleteMapping("/skins/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        repository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Skin buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin " + id + " no existe"));
    }

    private Campeon buscarCampeon(String campeonId) {
        return campeonRepository.findById(campeonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeon " + campeonId + " no existe"));
    }
}