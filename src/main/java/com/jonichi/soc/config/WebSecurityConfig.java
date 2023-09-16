package com.jonichi.soc.config;

import com.jonichi.soc.services.JwtUsersDetailsService;
import com.jonichi.soc.utils.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //    @Autowired
    private JwtAuthenticate jwtAuthenticate;

    @Autowired
    private JwtUsersDetailsService jwtUsersDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUsersDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // Bean is used to help to create and manage objects(beans) in your application.
    // It tells Spring that a particular method be used to create and configure an object, making it easier to work
    // with objects in you Spring application.
    @Bean
    public JwtAuthenticate jwtAuthenticationEntryPointBean() {
        return new JwtAuthenticate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // This code enables your application to accept request from different domains (CORS - Cross Origin
        // Resource Sharing) and turns off feature (CSRF) because it is not applicable on our end, because we
        // use JWT/JSON Web Token
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/*/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/*/courses/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/*/students").permitAll()
                .antMatchers(HttpMethod.POST, "/api/*/instructors").permitAll()
                .antMatchers(HttpMethod.POST, "/api/*/users").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // For any other requests (not covered by the previous rules), it enforces authentication,
                // meaning users must be logged in and have a valid token to access endpoints that are not
                // mentioned above.
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPointBean()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Configures your application to process JWT (JSON Web Token) request before handling traditional username
        // and password-based authentication. This allows JWT-based authentication to be prioritized in the
        // authentication process.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUsersDetailsService).passwordEncoder(passwordEncoder());
    }
}
