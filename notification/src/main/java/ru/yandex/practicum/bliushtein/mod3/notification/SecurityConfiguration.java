package ru.yandex.practicum.bliushtein.mod3.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@Configuration
@Slf4j
public class SecurityConfiguration {

    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer() {
        return authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/notification/**").authenticated()
                        .anyRequest().permitAll();
    }
 /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/notification/**").authenticated()
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
    }*/
}
