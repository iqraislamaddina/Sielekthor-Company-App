package apap.tugas.sielekthor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tugas.sielekthor.model.BarangModel;

import java.util.Optional;

@Repository
public interface BarangDb extends JpaRepository<BarangModel, Long> {
    Optional<BarangModel> findByidBarang(Long idBarang);
}
