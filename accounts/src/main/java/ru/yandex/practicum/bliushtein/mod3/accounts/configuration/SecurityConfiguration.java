package ru.yandex.practicum.bliushtein.mod3.accounts.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@Configuration
public class SecurityConfiguration {
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer() {
        return authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/account-service/user/authenticate/**").authenticated()
                        .anyRequest().permitAll();
    }
}
