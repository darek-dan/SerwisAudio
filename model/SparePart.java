package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity         // determinuje mapowanie klasy na tabelkę DB
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SparePart {
    @Id                                                     // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // auto inkrementacja
    private int partId;             // auto inkrementowany klucz głównym tabeli
    @Column(unique = true)
    private String symbol;
    private String name;
    // private ImageIcon buyDoc;
    // private ImageIcon partPhoto;
    private int quantity;
    private float price;
    private Enum<PartType> partType;

    public SparePart(String symbol, String name, int quantity, float price, Enum<PartType> partType) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.partType = partType;
    }

// id, nazwa, typ (ENUM), cena, dokument zakupu, wykorzystanie

}
