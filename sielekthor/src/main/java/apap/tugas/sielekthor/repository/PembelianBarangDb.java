package apap.tugas.sielekthor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tugas.sielekthor.model.PembelianBarangModel;

import java.util.Optional;

@Repository
public interface PembelianBarangDb extends JpaRepository<PembelianBarangModel, Long> {
    Optional<PembelianBarangModel> findByidPembelianBarang(Long idPembelianBarang);
}
