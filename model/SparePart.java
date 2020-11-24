package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private PartType type;
    // private ImageIcon buyDoc;
    // private ImageIcon partPhoto;
    private int quantity;
    private float price;

    public SparePart(String symbol, String name, PartType type, int quantity, float price) {
        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    // id, nazwa, typ (ENUM), cena, dokument zakupu, wykorzystanie

}
