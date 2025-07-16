package ru.yandex.practicum.bliushtein.mod3.shared.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

@Configuration
@Slf4j
@Profile("!test")
@ConditionalOnProperty("spring.security.oauth2.resourceserver.jwt.jwk-set-uri")
@EnableWebSecurity
@EnableMethodSecurity
public class SharedSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security,
            @Autowired Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> customizer
    ) throws Exception {
        return security
                .authorizeHttpRequests(customizer)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> {
                                    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                                    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
                                        log.debug("incoming JWT:{}", jwt);
                                        return Stream.of(jwt.getClaim("scope").toString().split(" "))
                                                .map(scope -> "SCOPE_" + scope)
                                                .map(SimpleGrantedAuthority::new)
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
