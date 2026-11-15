package cl.summonercloset.summonercloset.resumen;

import cl.summonercloset.summonercloset.campeon.Campeon;
import cl.summonercloset.summonercloset.campeon.CampeonRepository;
import cl.summonercloset.summonercloset.chroma.ChromaRepository;
import cl.summonercloset.summonercloset.favorito.Favorito;
import cl.summonercloset.summonercloset.favorito.FavoritoRepository;
import cl.summonercloset.summonercloset.skin.SkinRepository;
import cl.summonercloset.summonercloset.usuario.UsuarioRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/resumen")
public class ResumenController {

    private final UsuarioRepository usuarioRepository;
    private final CampeonRepository campeonRepository;
    private final SkinRepository skinRepository;
    private final ChromaRepository chromaRepository;
    private final FavoritoRepository favoritoRepository;

    public ResumenController(UsuarioRepository usuarioRepository, CampeonRepository campeonRepository, SkinRepository skinRepository, ChromaRepository chromaRepository, FavoritoRepository favoritoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.campeonRepository = campeonRepository;
        this.skinRepository = skinRepository;
        this.chromaRepository = chromaRepository;
        this.favoritoRepository = favoritoRepository;
    }

    @GetMapping
    public Map<String, Object> obtener() {
        List<Favorito> favoritos = favoritoRepository.findAll();

        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("usuarios", usuarioRepository.count());
        resumen.put("administradores", usuarioRepository.countByRolId("admin"));
        resumen.put("campeones", campeonRepository.count());
        resumen.put("skins", skinRepository.count());
        resumen.put("chromas", chromaRepository.count());
        resumen.put("favoritos", favoritos.size());
        resumen.put("topFavoritos", topFavoritos(favoritos));
        return resumen;
    }

    private List<Map<String, Object>> topFavoritos(List<Favorito> favoritos) {
        Map<String, Long> conteo = favoritos.stream()
                .collect(Collectors.groupingBy(f -> f.getCampeon().getId(), Collectors.counting()));

        return conteo.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(entrada -> {
                    Campeon campeon = campeonRepository.findById(entrada.getKey()).orElseThrow();
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", campeon.getId());
                    item.put("name", campeon.getName());
                    item.put("image", campeon.getImage());
                    item.put("cantidad", entrada.getValue());
                    return item;
                })
                .toList();
    }
}