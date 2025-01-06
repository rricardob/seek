package org.ricardo.examen.infraestructure.config;

import lombok.RequiredArgsConstructor;
import org.ricardo.examen.infraestructure.util.enums.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class HttpSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionMangConfig-> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//sesion sin estado
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(builderMetodo()) ;
        return http.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderMetodo() {
        return authConfig -> {
            authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
            authConfig.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
            authConfig.requestMatchers("/error").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/api/v1/client/create").hasAuthority(Permission.WRITE.name());
            authConfig.requestMatchers(HttpMethod.POST, "/api/v1/client/batch").hasAuthority(Permission.WRITE.name());
            authConfig.requestMatchers(HttpMethod.GET, "/api/v1/client/metrics").hasAuthority(Permission.READ.name());
            authConfig.requestMatchers(HttpMethod.GET, "/api/v1/client/info").hasAuthority(Permission.READ.name());
            authConfig.requestMatchers(HttpMethod.GET, "/actuator/**").hasAuthority(Permission.READ.name());
            authConfig.anyRequest().denyAll();
        };
    }
}
