/*
package nl.joost.xmluploader.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/index.html", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(withDefaults())
        .csrf(csrf -> csrf.disable()); // Updated method to disable CSRF

    return http.build();
  }
}*/
