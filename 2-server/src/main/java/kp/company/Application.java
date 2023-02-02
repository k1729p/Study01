package kp.company;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The web application uses web framework <b>Spring Web MVC</b>.
 *
 */
@SpringBootApplication
public class Application {

	/**
	 * The constructor.
	 */
	public Application() {
		super();
	}

	/**
	 * The entry point for the application.
	 * 
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {

		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(Application.class, args);
	}

}