package cl.summonercloset.summonercloset.posicion;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/posiciones")
public class PosicionController {
    
    private final PosicionRepository repository;

    public PosicionController(PosicionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Posicion> listar() {
        return repository.findAll();
    }
}