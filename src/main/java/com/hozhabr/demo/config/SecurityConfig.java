package com.hozhabr.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {





        @Bean
        public InMemoryUserDetailsManager userDetailsManager() {
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username("user")
                    .password(("password"))
                    .roles("USER")
                    .build();
            return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            HttpSecurity httpSecurity = http.authorizeRequests(auth -> {
                        auth.antMatchers("/v1/api/demo/**","/v1/api/tasks/demo/**").hasRole("USER");
                        auth.antMatchers("/")
                                .permitAll();
                    }).csrf().disable()
                    .httpBasic(Customizer.withDefaults());

            return httpSecurity.build();
        }


    }

