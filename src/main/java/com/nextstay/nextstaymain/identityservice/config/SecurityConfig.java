package com.nextstay.nextstaymain.identityservice.config;

import com.nextstay.nextstaymain.identityservice.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nextstay.nextstaymain.identityservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration 
@EnableWebSecurity //for spring's web support
@EnableMethodSecurity //for @PreAuthorize annotation
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}") //retrieves jwt sercet from env file
    private String jwtSecret;

    @Value("${password.encoding.pepper}") //retrieves pepper
    private String pepper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //disables cross site forgery to avoid cookie attacks 
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Match the paths from your UserController
                        .requestMatchers("/api/users/create/**", "/login").permitAll() 
                        .requestMatchers("/api/admin/**").hasRole("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //doesnt save session in memory. each request provides token 
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))) //configures app to look for token holder
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                // wraps bcrypt to include pepper for extra layer of security
                return bCryptPasswordEncoder.encode(rawPassword + pepper);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return bCryptPasswordEncoder.matches(rawPassword + pepper, encodedPassword);
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) { //finds user by their data 
        var authProvider = new DaoAuthenticationProvider(userDetailsService); //checks db to verify a person exists in it
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new JpaUserDetailsService(userRepository);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256"); // signs jwt with secret and hashes it using the hmacsha algorithm
        return NimbusJwtDecoder.withSecretKey(key).build(); //nimbus generates the jwt during login
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKey key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
        return NimbusJwtEncoder.withSecretKey(key).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() { //jwt claims used to assign role permissions
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        //Enum already has "ROLE_", so we'll set prefix to empty string
        grantedAuthoritiesConverter.setAuthorityPrefix(""); 

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}