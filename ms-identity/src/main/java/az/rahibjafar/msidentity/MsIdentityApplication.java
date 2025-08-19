package az.rahibjafar.msidentity;

import az.rahibjafar.msidentity.storage.UserDb;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Identity API", version = "v1", description = "Identity REST API")
)
public class MsIdentityApplication {
    public static void main(String[] args) {
        UserDb.loadSeeds();

        SpringApplication.run(MsIdentityApplication.class, args);
    }

}
