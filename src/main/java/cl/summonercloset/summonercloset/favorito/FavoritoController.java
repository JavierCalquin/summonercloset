package cl.summonercloset.summonercloset.favorito;

import cl.summonercloset.summonercloset.campeon.Campeon;
import cl.summonercloset.summonercloset.campeon.CampeonRepository;
import cl.summonercloset.summonercloset.usuario.Usuario;
import cl.summonercloset.summonercloset.usuario.UsuarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/usuarios/{usuarioId}/favoritos")
public class FavoritoController {

    private final FavoritoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final CampeonRepository campeonRepository;

    public FavoritoController(FavoritoRepository repository, UsuarioRepository usuarioRepository, CampeonRepository campeonRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.campeonRepository = campeonRepository;
    }

    @GetMapping
    public List<String> listar(@PathVariable Long usuarioId) {
        buscarUsuario(usuarioId);
        return repository.findByUsuarioId(usuarioId).stream()
                .map(favorito -> favorito.getCampeon().getId())
                .toList();
    }

    @PutMapping("/{campeonId}")
    public ResponseEntity<Void> agregar(@PathVariable Long usuarioId, @PathVariable String campeonId) {
        Usuario usuario = buscarUsuario(usuarioId);
        Campeon campeon = buscarCampeon(campeonId);
        if (!repository.existsByUsuarioIdAndCampeonId(usuarioId, campeonId)) {
            repository.save(new Favorito(usuario, campeon));
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{campeonId}")
    public ResponseEntity<Void> quitar(@PathVariable Long usuarioId, @PathVariable String campeonId) {
        buscarUsuario(usuarioId);
        buscarCampeon(campeonId);
        repository.findByUsuarioIdAndCampeonId(usuarioId, campeonId).ifPresent(repository::delete);
        return ResponseEntity.noContent().build();
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario " + id + " no existe"));
    }

    private Campeon buscarCampeon(String id) {
        return campeonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeon " + id + " no existe"));
    }
}