package serwisAudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import serwisAudio.model.ProductForSale;

@Repository
public interface ProductForSaleRepository extends JpaRepository<ProductForSale, Integer> {

}
