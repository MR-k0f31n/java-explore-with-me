package ru.practicum.priv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.practicum.pub.PublicServiceApp;

/**
 * @author MR.k0F31n
 */
@SpringBootApplication
public class PrivateServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PrivateServiceApp.class, args);
    }
}
