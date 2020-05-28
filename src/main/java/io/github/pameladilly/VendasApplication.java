package io.github.pameladilly;

import io.github.pameladilly.domain.entity.Cliente;
import io.github.pameladilly.domain.repository.Clientes;
import org.h2.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {

//    @Bean
//    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes){
//        return args -> {
//            Cliente oCliente = new Cliente("Fulano");
//            clientes.save(oCliente);
//        };
//    }

    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args);
    }
}
