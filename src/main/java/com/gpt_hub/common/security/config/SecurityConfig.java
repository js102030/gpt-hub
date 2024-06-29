package com.gpt_hub.common.security.config;


import static com.gpt_hub.domain.user.enumtype.Role.ADMIN;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.gpt_hub.common.security.CustomAuthenticationFailureHandler;
import com.gpt_hub.common.security.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring().requestMatchers(toH2Console())
                .requestMatchers("/static/**", "swagger-ui/**", "/v3/api-docs/**", "swagger-ui.html");
    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/", "/login", "/signup", "/api/**")
                                .permitAll()

                                .requestMatchers("/admin/**").hasAnyRole(ADMIN.name())

                                .anyRequest().authenticated()
                );

        httpSecurity
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(customAuthenticationFailureHandler())
                );

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);

        httpSecurity
                .sessionManagement(auth -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        httpSecurity
                .sessionManagement(auth -> auth
                        .sessionFixation()
                        .changeSessionId()
                );

        httpSecurity
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                );

        return httpSecurity.build();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}