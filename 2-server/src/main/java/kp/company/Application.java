package kp.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

/**
 * The web application uses web framework <b>Spring Web MVC</b>.
 */
@SpringBootApplication
public class Application {
    /**
     * The primary entry point for launching the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(Application.class, args);
    }

}