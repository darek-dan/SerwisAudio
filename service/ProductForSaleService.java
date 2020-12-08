package serwisAudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import serwisAudio.model.ProductForSale;
import serwisAudio.model.Repair;
import serwisAudio.repository.ProductForSaleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductForSaleService {

    @Autowired
    ProductForSaleRepository productForSaleRepository;

    public void addProductForSale(String serial, String brand, String model){
        productForSaleRepository.save(new ProductForSale(serial, brand, model));
    }
    public List<ProductForSale> getAllProductsForSale() {
        return productForSaleRepository.findAll(Sort.by(Sort.Direction.ASC, "brand"));
    }
    public List<Integer> generatePagesIndexes(List<ProductForSale> products){
        int noOfPages = (getAllProductsForSale().size() / 5) + 1;
        List<Integer> pagesIndexes = new ArrayList<>();
        for (int i = 0; i < noOfPages; i++){
            pagesIndexes.add(i + 1);
        }
        return pagesIndexes;
    }

    public List<ProductForSale> getAllProductsForSale(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 5, Sort.by(Sort.Direction.ASC, "brand"));
        Page<ProductForSale> postPage = productForSaleRepository.findAll(pageable);
        return postPage.getContent();
    }
}
