package com.primaria.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.primaria.app.Service.UsuarioService;


import java.util.List;

@Configuration
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> usuarioService
                .findByEmail(email)
                .map(usuario -> org.springframework.security.core.userdetails.User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword())
                        .roles(usuario.getRol().name())
                        .build())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Codificador de contraseñas
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticación que usa el servicio anterior
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml",
                    "/v3/api-docs.json", 
                    "/Autenticacion/login",
                    "/usuarios/**",
                    "/grupos/**",
                    "/grados/**",
                    "/materias/**",
                    "/campoFormativo/**",
                    "/ciclosescolares/**",
                    "/alumnos/**",
                    "/Trimestres/**",
                    "/asignacion/**",
                    "/inscripcion/**",
                    "/asignacion-docente/**",
                    "/actividad/**",
                    "/calificacion/**",
                    "/actividad-alumno/**",
                    "/Tutor/**",
                    "/Tipos_Evaluacion/**",
                    "/AlumnoTutor/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .cors(Customizer.withDefaults()); // <-- usar el nuevo método para habilitar CORS con la configuración definida

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Permitir ambos orígenes: Angular y Ionic
        config.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8100"));

        // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, OPTIONS)
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir todos los headers
        config.setAllowedHeaders(List.of("*"));

        // Permitir credenciales (cookies, Authorization headers)
        config.setAllowCredentials(true);

        // Duración de cache para preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    
    // AuthenticationManager para poder usarlo en controladores si se necesita
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProvider());
        return builder.build();
    }
}