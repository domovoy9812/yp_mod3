package ru.yandex.practicum.bliushtein.mod3.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.yandex.practicum.bliushtein.mod3.ui.service.AuthenticationService;

@Configuration
@Profile("!test")
public class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security,
                                            @Autowired AuthenticationService authenticationService) throws Exception {
        return security
                /*.formLogin(customizer -> customizer
                        .successForwardUrl("/main")
                        .failureUrl("/error")
                )*/
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/main/**").authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(Customizer.withDefaults())
                .userDetailsService(authenticationService)
                .build();
    }
}
