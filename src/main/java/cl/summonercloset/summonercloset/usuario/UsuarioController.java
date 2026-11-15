package cl.summonercloset.summonercloset.usuario;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UsuarioController {

    private final UsuarioRepository repository;
    private final RolUsuarioRepository rolRepository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repository, RolUsuarioRepository rolRepository, PasswordEncoder encoder) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.encoder = encoder;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario) {
        validarClave(usuario.getClave());
        String correo = usuario.getCorreo().trim().toLowerCase();
        if (repository.existsByCorreoIgnoreCase(correo)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "correo ya esta registrado");
        }
        usuario.setId(null);
        usuario.setCorreo(correo);
        usuario.setClave(encoder.encode(usuario.getClave()));
        usuario.setRol(buscarRol("cliente"));
        Usuario guardado = repository.save(usuario);
        return ResponseEntity.created(URI.create("/api/usuarios/" + guardado.getId())).body(guardado);
    }

    @PostMapping("/sesiones")
    public Usuario iniciarSesion(@RequestBody LoginRequest datos) {
        if (datos.correo() == null || datos.clave() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "correo y contraseña son obligatorios");
        }
        Usuario usuario = repository.findByCorreoIgnoreCase(datos.correo().trim())
                .filter(u -> encoder.matches(datos.clave(), u.getClave()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "correo o contraseña incorrectos"));

        if (Boolean.TRUE.equals(datos.comoAdmin()) && !usuario.getRol().getId().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "esta cuenta no es de administrador");
        }
        return usuario;
    }

    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return buscar(id);
    }

    @PutMapping("/usuarios/{id}")
    public Usuario actualizar(@PathVariable Long id, @Valid @RequestBody Usuario datos) {
        Usuario usuario = buscar(id);
        String correo = datos.getCorreo().trim().toLowerCase();

        repository.findByCorreoIgnoreCase(correo)
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(otro -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "correo ya esta registrado");
                });

        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(correo);
        if (datos.getRol() != null && datos.getRol().getId() != null) {
            usuario.setRol(buscarRol(datos.getRol().getId()));
        }
        if (datos.getClave() != null && !datos.getClave().isBlank()) {
            validarClave(datos.getClave());
            usuario.setClave(encoder.encode(datos.getClave()));
        }
        return repository.save(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        repository.delete(buscar(id));
        return ResponseEntity.noContent().build();
    }

    private Usuario buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario " + id + " no existe"));
    }

    private RolUsuario buscarRol(String id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol " + id + " no existe"));
    }

    private void validarClave(String clave) {
        if (clave == null || clave.length() < 6 || clave.length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña debe tener entre 6 y 20 caracteres");
        }
    }
}