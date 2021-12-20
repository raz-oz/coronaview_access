package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.JWT.CustomAuthenticationFilter;
import com.rad.ms.corona_view.access.JWT.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private interface ConfigureHandler {
        void myConfigure(HttpSecurity http) throws Exception;
    }

    enum securityType {
        BASIC,
        BEARER,
        OAUTH2
    }

    private Map<Integer, ConfigureHandler> configureHandlerMap  = Map.of(
            securityType.BASIC.ordinal(), (http -> {
                http
                        .csrf().disable()
                        .formLogin().disable()
                        .authorizeRequests()
                        .antMatchers("/", "/registration/**", "/login")
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
                        .antMatchers("/", "/registration/**", "/login")
                        .permitAll()
                        .and()
                        // .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                        .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
                        .authorizeRequests().anyRequest().authenticated();
                http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
            }),
            securityType.OAUTH2.ordinal(), (http -> {
                http
                        .authorizeRequests(a -> a
                                .antMatchers("/", "/error", "/webjars/**", "/login/oauth2", "/logout").permitAll()
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
        configureHandlerMap.get(securityType.BEARER.ordinal()).myConfigure(http);
    }

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
