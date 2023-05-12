package com.test.security;

import com.test.mapper.UserDetailsServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Controller
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceMapper userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/index").anonymous()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/src/css/**").permitAll()
//                .antMatchers("/js/**")..anonymous()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/icon/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/components/**").permitAll()

//                .anyRequest().authenticated()

                .and()
                .csrf().disable()

                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .usernameParameter("login")
                .passwordParameter("password")

                .and()
                .logout();
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/client/**")
                .addResourceLocations("/client/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) {
        authentication.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}