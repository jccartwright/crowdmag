package ngdc.geomag

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.beans.factory.annotation.Autowired

@SpringBootApplication
class Application {

    @Autowired
    MeasurementRepository repository

    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
