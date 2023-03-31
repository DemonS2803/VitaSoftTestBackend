package ru.vitasoft.vitasofttestbackend;

import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class VitaSoftTestBackendApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(VitaSoftTestBackendApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", 9000));
        app.run();
    }

}
