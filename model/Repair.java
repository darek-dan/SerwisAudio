package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @OneToOne(fetch = FetchType.EAGER)
    private User client;
    private LocalDateTime dateAdded;
    @Type(type = "text")
    private String additionalInfo;
    @Type(type = "text")
    private String diagDescription;
    @OneToMany
    private List<SparePart> spareParts;
    @Transient
    private MultipartFile repairImage;
    private String repairImagePath;
    private boolean isCompleted;
    private RepairStatus repairStatus;

    public Repair(String serial, String brand, String model, String userFailDescription,
                  String additionalInfo, User client, LocalDateTime dateAdded, MultipartFile repairImage) {
        this.serial = serial;
        this.brand = brand;
        this.model = model;
        this.userFailDescription = userFailDescription;
        this.client = client;
        this.dateAdded = dateAdded;
        this.additionalInfo = additionalInfo;
        this.repairImage = repairImage;
        this.isCompleted = false;
        this.repairStatus = RepairStatus.REGISTERED;
    }


}
// (id, serial no., producent, model, opis usterki klienta, zdjęcia przed przyjęciem,
// data przyjęcia, dodatkowe informacje, diagnoza serwisu, części potrzebne do naprawy,
// progres, koszt naprawy, termin odbioru
