package az.rahibjafar.msproduct;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(title = "Product API", version = "v1", description = "Product REST API")
)
@SpringBootApplication
public class MsProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsProductApplication.class, args);
    }
}
