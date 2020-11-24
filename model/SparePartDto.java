package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SparePartDto {  // DTO - data transfer object - obiekt wykorzystywany w formularzach do przekyzawania parametrów
    @NotBlank(message = "Musisz wpisać symbol części")
    @Size(min= 0, max = 20, message = "Maksymalnie 20 znaków")
    private String symbol;
    // @NotBlank(message = "Musisz podać model")
    @Size(min = 0, max = 50, message = "Maksymalnie 50 znaków")
    private String name;
    // private PartType type;
    @NotBlank(message = "Musisz wpisać ilość")
    // @Size(min = 10, max = 500, message = "Musisz wpisać miedzy {min} a {max} znaków")
    private int quantity;
    private float price;

}
