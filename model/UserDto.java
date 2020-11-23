package serwisAudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email address is not valid")
    private String email;
    @Size(min = 8, max = 128, message = "Password must have number of characters between {min} and {max}")
    // @Pattern(regexp = "[A-Z]+.*\\d+|\\d+.*[A-Z]+", message = "Password must have ath least 1 capital letter and 1 digit")
    private String password;
}
