package aespa.codeeasy.config;

import aespa.codeeasy.config.ouath.PrincipalOauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PrincipalOauthUserService principalOauthUserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> {
                    auth.antMatchers("/").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login()
                .defaultSuccessUrl("/success")
                .userInfoEndpoint()
                .userService(principalOauthUserService);

        return http.build();
    }
}