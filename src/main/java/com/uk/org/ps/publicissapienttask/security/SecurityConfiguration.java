package com.uk.org.ps.publicissapienttask.security;

import com.uk.org.ps.publicissapienttask.config.PasswordEncoderByCrypt;
import com.uk.org.ps.publicissapienttask.service.CustomUserDetailsService;
import com.uk.org.ps.publicissapienttask.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    //private final PasswordEncoderByCrypt bCryptPasswordEncoder;

    public SecurityConfiguration(CustomUserDetailsService userDetailsService
            /*, PasswordEncoderByCrypt bCryptPasswordEncoder*/) {
        this.userDetailsService = userDetailsService;
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/h2-console/**", "/swagger*/**", "/v2/api-docs",
                        "/configuration/**", "/webjars/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**", "/swagger*/**", "/v2/api-docs",
                "/configuration/**", "/webjars/**")
                .and().headers().frameOptions().sameOrigin();

        http.cors().and().authorizeRequests().antMatchers("/api/user/v1/register",
                "/api/user/v1/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), new CustomAccessDeniedHandler()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
