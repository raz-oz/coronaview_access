package com.rad.ms.corona_view.access.Security;

import com.rad.ms.corona_view.access.JWT.CustomAuthenticationFilter;
import com.rad.ms.corona_view.access.JWT.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    enum securityType {
        BASIC,
        BEARER,
        OAUTH2
    }

    private interface ConfigureHandler {
        void configure(HttpSecurity http) throws Exception;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<Integer, ConfigureHandler> configureHandlerMap;

    public SecurityConfig(){
        configureHandlerMap = new HashMap<>();
        // Basic Security Config
        configureHandlerMap.put(securityType.BASIC.ordinal(), (http -> {
            http
                    .csrf().disable()
                    .formLogin().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/registration/**", "/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            http.httpBasic();
        }));

        // Bearer Security Config
        configureHandlerMap.put(securityType.BEARER.ordinal(), (http -> {
            http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/registration/**", "/login")
                    .permitAll()
                    .and()
                    .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
                    .authorizeRequests().anyRequest().authenticated();
        }));

        // OAuth2 Security Config
        configureHandlerMap.put(securityType.BEARER.ordinal(), (http -> {
            http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/registration/**", "/login")
                    .permitAll()
                    .and()
                    .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
                    .authorizeRequests().anyRequest().authenticated();
        }));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        ConfigureHandler configureHandler = configureHandlerMap.get(securityType.OAUTH2.ordinal());
        configureHandler.configure(http);
    }

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }



//    @Configuration
//    @Order(3)
//    public static class BasicWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .formLogin().disable()
//                    .authorizeRequests()
//                    .antMatchers("/", "/registration/**", "/login")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated();
//            http.httpBasic();
//        }
//    }
//    @Configuration
//    @Order(2)
//    public static class JwtWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//
//
//        protected void configure(HttpSecurity http) throws Exception {
//
//            http
//                    .csrf().disable()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/", "/registration/**", "/login")
//                    .permitAll()
//                    .and()
//                    .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
//                    .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
//                    .authorizeRequests().anyRequest().authenticated();
//        }
//    }
//
//    @Configuration
//    @Order(1)
//    public static class OAuth2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
//
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            // @formatter:off
//            http
//                    .authorizeRequests(a -> a
//                            .antMatchers("/", "/error", "/webjars/**", "/login/oauth2", "/logout").permitAll()
//                            .anyRequest().authenticated()
//                    )
//                    .exceptionHandling(e -> e
//                            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                    )
//                    .oauth2Login();
//            // @formatter:on
//        }
//
//    }
}
