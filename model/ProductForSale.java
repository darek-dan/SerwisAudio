package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductForSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productForSaleId;
    private String serial;
    private String brand;
    private String model;

    public ProductForSale(String serial, String brand, String model) {
        this.serial = serial;
        this.brand = brand;
        this.model = model;
    }
}
