package serwisAudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import serwisAudio.model.SparePart;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {

}
