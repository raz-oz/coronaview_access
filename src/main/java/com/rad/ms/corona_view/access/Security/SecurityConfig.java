package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.JWT.CustomAuthenticationFilter;
import com.rad.ms.corona_view.access.JWT.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    private interface ConfigureHandler {
        void myConfigure(HttpSecurity http) throws Exception;
    }

    enum securityType {
        BASIC,
        BEARER,
        OAUTH2
    }

    private static final int security_config_method = securityType.BASIC.ordinal();

    private final Map<Integer, ConfigureHandler> configureHandlerMap  = Map.of(
            securityType.BASIC.ordinal(), (http -> {
                http
                        .csrf().disable()
                        .formLogin().disable()
                        .authorizeRequests()
                        .antMatchers("/home", "/registration/**", "/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated();
                http.httpBasic();
            }),

            securityType.BEARER.ordinal(), (http -> {
                http
                        .csrf().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests()
                        .antMatchers("/registration","/home", "/registration/**", "/login")
                        .permitAll()
                        .and()
                        .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
                        .authorizeRequests().anyRequest().authenticated();
                http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
            }),

            securityType.OAUTH2.ordinal(), (http -> {
                http
                        .authorizeRequests(a -> a
                                .antMatchers("/home", "/error", "/webjars/**", "/login/**").permitAll()
                                .anyRequest().authenticated()
                        )
                        .exceptionHandling(e -> e
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        )
                        .oauth2Login();
            })
    );

@Override
    protected void configure(HttpSecurity http) throws Exception {
        configureHandlerMap.get(security_config_method).myConfigure(http);
    }

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
