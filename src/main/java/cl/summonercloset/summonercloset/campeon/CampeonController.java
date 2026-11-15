package cl.summonercloset.summonercloset.campeon;

import cl.summonercloset.summonercloset.posicion.Posicion;
import cl.summonercloset.summonercloset.posicion.PosicionRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/campeones")
public class CampeonController {

    private final CampeonRepository repository;
    private final PosicionRepository posicionRepository;

    public CampeonController(CampeonRepository repository, PosicionRepository posicionRepository) {
        this.repository = repository;
        this.posicionRepository = posicionRepository;
    }

    @GetMapping
    public List<Campeon> listar(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String role) {
        boolean sinName = name == null || name.isBlank();
        boolean sinRole = role == null || role.isBlank() || role.equals("todos");

        if (sinName && sinRole) {
            return repository.findAll();
        }
        if (sinRole) {
            return repository.findByNameContainingIgnoreCase(name);
        }
        if (sinName) {
            return repository.findByRoleId(role);
        }
        return repository.findByNameContainingIgnoreCaseAndRoleId(name, role);
    }

    @GetMapping("/{id}")
    public Campeon obtener(@PathVariable String id) {
        return buscar(id);
    }

    @PostMapping
    public ResponseEntity<Campeon> crear(@Valid @RequestBody Campeon campeon) {
        if (repository.existsById(campeon.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un campeon con id " + campeon.getId());
        }
        campeon.setRole(buscarPosicion(campeon.getRole()));
        Campeon guardado = repository.save(campeon);
        return ResponseEntity.created(URI.create("/api/campeones/" + guardado.getId())).body(guardado);
    }

    @PutMapping("/{id}")
    public Campeon actualizar(@PathVariable String id, @Valid @RequestBody Campeon datos) {
        Campeon campeon = buscar(id);
        campeon.setName(datos.getName());
        campeon.setRole(buscarPosicion(datos.getRole()));
        campeon.setImage(datos.getImage());
        campeon.setDescription(datos.getDescription());
        return repository.save(campeon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        repository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Campeon buscar(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeon " + id + " no existe"));
    }

    private Posicion buscarPosicion(Posicion role) {
        if (role == null || role.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La posicion es obligatoria");
        }
        return posicionRepository.findById(role.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La posicion " + role.getId() + " no existe"));
    }
}