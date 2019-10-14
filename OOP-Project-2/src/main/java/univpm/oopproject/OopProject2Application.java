package univpm.oopproject;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import univpm.oopproject.dataset.Dataset;
import univpm.oopproject.utils.Configurations;
import univpm.oopproject.utils.Utils;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe principale di avvio del progetto Spring
 * @author Samuele Perticarari & Martina Rossi
 *
 */
@RestController
@SpringBootApplication
public class OopProject2Application {

	/**
	 * Metodo di avvio del progetto.
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		Utils.downloadDataset();
		
		// Carica e parsa il dataset in memoria.
		Dataset.loadAndParseDataset( Configurations.FILE_NAME );
		
		// Lancia il web-server Spring.
		SpringApplication.run(OopProject2Application.class, args);
		
	}

	/**
	 * Metodo che restituisce le informazioni sui Bean caricati dal programma.
	 * @param ctx Contesto applicazione
	 * @return Argomenti per il Bean di Spring
	 */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println("- " + beanName);
            }

        };
    }

}
