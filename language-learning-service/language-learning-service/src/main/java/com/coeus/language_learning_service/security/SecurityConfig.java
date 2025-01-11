package com.coeus.language_learning_service.security;



import com.coeus.language_learning_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    // Şifrələmə mexanizmi üçün PasswordEncoder bean-i
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager bean-i
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Security Filter Chain konfiqurasiyası
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF müdafiəsini deaktiv edirik (lazım olsa aktiv edin)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/register").permitAll() // Açıq endpointlər
                        .anyRequest().permitAll() // Qalan bütün sorğular üçün autentifikasiya tələb olunur
                )
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic autentifikasiyanı deaktiv edirik
                .formLogin(formLogin -> formLogin.disable()); // Default form-login deaktivdir

        return http.build();
    }
}
