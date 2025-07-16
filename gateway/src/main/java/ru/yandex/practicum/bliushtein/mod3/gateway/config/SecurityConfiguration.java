package ru.yandex.practicum.bliushtein.mod3.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfiguration {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                //.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                //.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll())
                //.oauth2Client(withDefaults())
                //.oauth2Login(withDefaults())
                .build();
    }

    /*@Bean
    ReactiveOAuth2AuthorizedClientManager auth2AuthorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveOAuth2AuthorizedClientService authorizedClientService
    ) {
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);

        manager.setAuthorizedClientProvider(ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .refreshToken()
                .build()
        );
        manager.setAuthorizationSuccessHandler((authorizedClient, principal, attributes) -> {
            log.debug("-------------Successfully authorized: authorizedClient={}, principal={}, attributes={}", authorizedClient, principal, attributes);
            return Mono.empty();
        });

        manager.setAuthorizationFailureHandler((authorizedClient, principal, attributes) -> {
            log.debug("-------------Authorization failed: authorizedClient={}, principal={}, attributes={}", authorizedClient, principal, attributes);
            return Mono.empty();
        });
        return manager;
    }

    @Bean
    public WebClient webClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth2Filter.setDefaultOAuth2AuthorizedClient(true);
        oauth2Filter.setDefaultClientRegistrationId("gateway-client");

        return WebClient.builder()
                .filter(oauth2Filter)
                //.apply(oauth2Filter.oauth2Configuration())
                .build();
    }*/
}
