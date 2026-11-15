package cl.summonercloset.summonercloset.skin;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkinRepository extends JpaRepository<Skin, Long> {

    List<Skin> findByCampeonIdOrderById(String campeonId);
}