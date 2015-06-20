package ngdc.geomag

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
class CrowdmagApplication {

    @Autowired
    MeasurementRepository repository

    static void main(String[] args) {
        SpringApplication.run CrowdmagApplication, args
    }
/*
    @Override
    public void run(String... strings) throws Exception {
        repository.save(new Measurement(obsTime: new Date(), latitude: 40.0, longitude: -105.0))
        repository.save(new Measurement())

        // fetch all measurements
        println("Measurements found with findAll():");
        println("-------------------------------");
        for (Measurement measurement : repository.findAll()) {
            println(measurement);
        }
        println();

        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : repository.findByLastName("Bauer")) {
            System.out.println(bauer);
        }

    }
    */
}
