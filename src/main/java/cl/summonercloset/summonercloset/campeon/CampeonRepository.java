package cl.summonercloset.summonercloset.campeon;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampeonRepository extends JpaRepository<Campeon, String> {

    List<Campeon> findByNameContainingIgnoreCase(String name);

    List<Campeon> findByRoleId(String role);

    List<Campeon> findByNameContainingIgnoreCaseAndRoleId(String name, String role);
}