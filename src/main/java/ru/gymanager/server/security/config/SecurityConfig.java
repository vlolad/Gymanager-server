package ru.gymanager.server.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .anyRequest().permitAll();

        // Если добавить, то любой запрос должен быть с хедором авторизации
        // При возникновении ошибок в авторизации первым делом проверить этот момент
        // http.exceptionHandling().authenticationEntryPoint(securityAuthenticationEntryPoint);

        // Добавляем свой фильтр, в котором проверяется правильность токена
        return http.build();
    }
}
