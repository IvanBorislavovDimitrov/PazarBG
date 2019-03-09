package com.ivan.pazar.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(@Qualifier(value = "userDetailsServiceImpl") UserDetailsService userDetailsService,
                                 @Qualifier(value = "sha256PasswordEncoder") PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/secure/**")
                .hasAnyRole("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/users/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }
}
