package cl.summonercloset.summonercloset.chroma;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChromaRepository extends JpaRepository<Chroma, Long> {

    List<Chroma> findBySkinIdOrderById(Long skinId);
}