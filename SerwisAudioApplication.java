package serwisAudio;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2     // umożliwia korzystanie ze swaggera
@SpringBootApplication
public class SerwisAudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerwisAudioApplication.class, args);
    }


}
