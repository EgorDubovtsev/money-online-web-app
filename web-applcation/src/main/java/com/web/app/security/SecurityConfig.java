package com.web.app.security;

import com.web.app.security.handler.DefaultFailureHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DefaultFailureHandler failureHandler = new DefaultFailureHandler();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/currencies/**").permitAll()
                .antMatchers("/actuator/**").anonymous()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()

                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/")
                .failureHandler(failureHandler)

                .and()
                .logout();
    }

}