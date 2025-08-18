package az.rahibjafar.mscustomer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(title = "Customer API", version = "v1", description = "Customer REST API")
)
@SpringBootApplication
public class MsCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCustomerApplication.class, args);
    }

}
