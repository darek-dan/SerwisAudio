package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int repairId;
    private String serial;
    private String brand;
    private String model;
    @Type(type = "text")    // typ w DB longtext
    private String userFailDescription;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private User client;
    private ImageIcon userImage;
    private LocalDateTime dateAdded;
    @Type(type = "text")
    private String additionalInfo;
    @Type(type = "text")
    private String diagDescription;
    @OneToMany
    private List<SparePart> spareParts;

    public Repair(String serial, String brand, String model, String userFailDescription,
                  String additionalInfo, User client, LocalDateTime dateAdded) {
        this.serial = serial;
        this.brand = brand;
        this.model = model;
        this.userFailDescription = userFailDescription;
        this.client = client;
        this.dateAdded = dateAdded;
        this.additionalInfo = additionalInfo;
    }
}
// (id, serial no., producent, model, opis usterki klienta, zdjęcia przed przyjęciem,
// data przyjęcia, dodatkowe informacje, diagnoza serwisu, części potrzebne do naprawy,
// progres, koszt naprawy, termin odbioru
