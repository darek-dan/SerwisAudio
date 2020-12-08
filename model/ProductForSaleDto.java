package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductForSaleDto {      // DTO - data transfer object - obiekt wykorzystywany w formularzach do przekyzawania parametrów
    @NotBlank(message = "Musisz wpisać numer seryjny")
    @Size(min= 2, max = 20, message = "2 do 20 znaków")
    private String serial;
    @Size(min = 0, max = 20, message = "Maksymalnie 20 znaków")
    private String brand;
    // @NotBlank(message = "Musisz podać model")
    private String model;
}
