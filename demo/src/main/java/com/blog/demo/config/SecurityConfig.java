package com.blog.demo.config;


import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import com.blog.demo.security.CustomBasicAuthenticationEntryPoint;
import com.blog.demo.security.CustomBearerTokenAccessDeniedHandler;
import com.blog.demo.security.CustomBearerTokenAuthenticationEntryPoint;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
//@RequiredArgsConstructor
public class SecurityConfig {

//    @Value("${api.endpoint.base-url}")
//    private String baseUrl;

//    @Value("${jwt.public.key}")
//    RSAPublicKey publicKey;


    private final RSAPublicKey publicKey;

    private final RSAPrivateKey privateKey;
//    @Value("${jwt.private.key}")
//    RSAPrivateKey privateKey;


    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    private final CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint;

    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    public SecurityConfig(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint, CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint, CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler) throws NoSuchAlgorithmException {
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.customBearerTokenAuthenticationEntryPoint = customBearerTokenAuthenticationEntryPoint;
        this.customBearerTokenAccessDeniedHandler = customBearerTokenAccessDeniedHandler;

        // Generate a public/private key pair.
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // The generated key will have a size of 2048 bits.
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic->httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/blogs/").permitAll()
                        .requestMatchers(HttpMethod.GET, "api/blogs/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "api/blogs/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "api/blogs/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "api/blogs/").hasAnyRole("ADMIN", "AUTHOR")
                        .requestMatchers(HttpMethod.POST, "api/comments/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "api/comments/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "api/comments/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.POST, "api/votes/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "api/votes/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "api/votes/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.GET, "api/categories/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.POST, "api/categories/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "api/categories/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.PUT, "api/categories/").hasAnyRole("ADMIN", "USER", "AUTHOR")
                        .requestMatchers(HttpMethod.GET, "/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/").hasRole("ADMIN")
                        .requestMatchers(EndpointRequest.to("health", "info", "prometheus")).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info", "prometheus")).hasAuthority("ROLE_admin")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // Explicitly fallback to antMatcher inside requestMatchers.
                        .anyRequest().authenticated() // Always a good idea to put this as last.
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(this.customBearerTokenAuthenticationEntryPoint)
                        .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                ).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtGrantedAuthoritiesConverter gac = new JwtGrantedAuthoritiesConverter();
        gac.setAuthoritiesClaimName("authorities");
        gac.setAuthorityPrefix("ROLE_");

        final JwtAuthenticationConverter jac = new JwtAuthenticationConverter();
        jac.setJwtGrantedAuthoritiesConverter(gac);
        return jac;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}