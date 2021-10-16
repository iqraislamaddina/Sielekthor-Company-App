package apap.tugas.sielekthor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tugas.sielekthor.model.TipeModel;

import java.util.Optional;

@Repository
public interface TipeDb extends JpaRepository<TipeModel, Long> {
    Optional<TipeModel> findByidTipe(Long idTipe);
}
