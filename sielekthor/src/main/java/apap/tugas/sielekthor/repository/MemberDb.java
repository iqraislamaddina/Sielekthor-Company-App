package apap.tugas.sielekthor.repository;

import org.springframework.stereotype.Repository;

import apap.tugas.sielekthor.model.MemberModel;

import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.Optional;
 
@Repository
public interface MemberDb extends JpaRepository<MemberModel, Long> {
  Optional<MemberModel> findByidMember(Long member);
}

