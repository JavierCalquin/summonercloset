package cl.summonercloset.summonercloset.favorito;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuarioId(Long usuarioId);

    Optional<Favorito> findByUsuarioIdAndCampeonId(Long usuarioId, String campeonId);

    boolean existsByUsuarioIdAndCampeonId(Long usuarioId, String campeonId);
}