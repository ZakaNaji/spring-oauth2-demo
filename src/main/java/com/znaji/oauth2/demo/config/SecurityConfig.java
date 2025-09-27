package com.znaji.oauth2.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyClockRoleAuthoritiesConverter());
        http.authorizeHttpRequests(authConfig -> authConfig
                .requestMatchers("/secure", "/secure-api").hasRole("ADMIN")
                .anyRequest().permitAll());
        http.formLogin(Customizer.withDefaults());
        http.oauth2Login(Customizer.withDefaults());
        http.oauth2ResourceServer(resourceServerConfig ->
                resourceServerConfig.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("znaji")
                .password("{noop}1234")
                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(user1);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(List<ClientRegistration> clients) {
        return new InMemoryClientRegistrationRepository(clients);
    }

    @Bean
    public ClientRegistration githubClient(Environment environment) {
        String githubClientId = environment.getProperty("GITHUB_CLIENT_ID");
        String githubClientSecret = environment.getProperty("GITHUB_CLIENT_SECRET");
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId(githubClientId)
                .clientSecret(githubClientSecret)
                .build();
    }
}
