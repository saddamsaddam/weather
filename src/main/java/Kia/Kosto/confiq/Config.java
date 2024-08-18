package Kia.Kosto.confiq;

import Kia.Kosto.repository.UserRepository;
import Kia.Kosto.repository.WeatherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Config {

    @Bean
    public CommandLineRunner runner(UserRepository ur, WeatherRepository we) {
        return args -> {

            System.out.println("Application started ");
        };
    }
    
   
}
