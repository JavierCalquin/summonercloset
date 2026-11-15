package cl.summonercloset.summonercloset.salud;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SaludController {

    @GetMapping("/api/health")
    public Map<String, String> salud() {
        return Map.of("estado", "ok");
    }
}