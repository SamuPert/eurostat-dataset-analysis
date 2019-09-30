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
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class OopProject2Application {

	public static void main(String[] args) {
		
		
		try
		{
			byte dataBuffer[] = new byte[1024];
		    int bytesRead;
		    
		    System.out.println("SCARICO IL DATASET..");
			
			InputStream datasetStream = new URL( Configurations.FILE_URL ).openStream();
			BufferedInputStream in = new BufferedInputStream( datasetStream  );
			
			FileOutputStream fileOutputStream = new FileOutputStream( Configurations.FILE_NAME );
			
		    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
		        fileOutputStream.write(dataBuffer, 0, bytesRead);
		    }
		    
		    datasetStream.close();
		    fileOutputStream.close();
		    in.close();
			
		    System.out.println("DATASET SCARICATO..");
		} catch (IOException e)
		{
			System.err.println("Errore nel download del file...");
			System.exit(-1);
		}

		SpringApplication.run(OopProject2Application.class, args);
		
	}


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
