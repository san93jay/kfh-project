package com.sanjay.kfh.security;

import com.sanjay.kfh.serviceImpl.KfhStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Sanjay Vishwakarma
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class KfhSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private KfhJwtAuthenticationEntryPoint kfhJwtAuthenticationEntryPoint;

    @Autowired
    private KfhStudentService kfhStudentService;

    @Autowired
    private KfhJwtRequestFilter kfhJwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(kfhStudentService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/student/auth/**","/api/student/role/**" ).permitAll()
                .antMatchers("/swagger-ui.html","/webjars/springfox-swagger-ui/**",
                        "/swagger-resources/**","/swagger-resources/**","/v2/api-docs").permitAll()
                .antMatchers("/api/courses/**").hasRole("USER")
                .antMatchers("/api/studentAllocation/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated().and().

                        exceptionHandling().authenticationEntryPoint(kfhJwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(kfhJwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

