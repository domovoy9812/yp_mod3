package ru.yandex.practicum.bliushtein.mod3.accounts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                //.anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> {
                                    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                                    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
                                        log.debug("incoming JWT:{}", jwt);
                                        List<String> roles = (List<String>) ((Map) jwt.getClaim("realm_access")).get("roles");
                                        return roles.stream().map(SimpleGrantedAuthority::new)
                                                .map(GrantedAuthority.class::cast).toList();
                                    });
                                    jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter);
                                }
                        )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
