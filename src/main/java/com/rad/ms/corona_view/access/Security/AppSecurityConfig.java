package com.rad.ms.corona_view.access.Security;

import ch.qos.logback.core.encoder.Encoder;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    //init all users as User details obj and adding them to the DB
//    @Bean
//    protected UserDetailsService userDetailsService(UserRepository repository){
//        List<UserDetails> SecurityUser = new ArrayList<>();
//        for (User u:repository.findAll())
//            SecurityUser.add(org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().username(u.getUsername()).password(u.getPassword()).roles(u.getRoleId().getName()).build());
//        return new InMemoryUserDetailsManager(SecurityUser);
//    };

    @Autowired
    protected UserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }


}
