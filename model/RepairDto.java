package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RepairDto {  // DTO - data transfer object - obiekt wykorzystywany w formularzach do przekyzawania parametrów
    private String serial;
    @NotBlank(message = "Musisz wpisać markę sprzętu")
    @Size(min= 0, max = 20, message = "Maksymalnie 20 znaków")
    private String brand;
    @NotBlank(message = "Musisz podać model")
    @Size(min = 0, max = 50, message = "Maksymalnie 50 znaków")
    private String model;
    @NotBlank(message = "Musisz opisać usterkę")
    @Size(min = 10, max = 500, message = "Musisz wpisać miedzy {min} a {max} znaków")
    private String userFailDescription;
    @
    private MultipartFile userImage;
    private String additionalInfo;

    public RepairDto(String serial, String brand, String model, String userFailDescription, String additionalInfo) {
        this.serial = serial;
        this.brand = brand;
        this.model = model;
        this.userFailDescription = userFailDescription;
        this.additionalInfo = additionalInfo;
    }
    // private String userImagePath;


// private String diagDescription;


}
