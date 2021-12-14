//package com.rad.ms.corona_view.access.Security;
//
//import com.rad.ms.corona_view.access.Entities.User;
//import com.rad.ms.corona_view.access.Registration.token.ConfirmationTokenService;
//import com.rad.ms.corona_view.access.Repositories.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@Configuration
//@AllArgsConstructor
//public class AccessUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User CurrUser = userRepository.getUserByUsername(s);
//        if(CurrUser==null)
//            throw new UsernameNotFoundException(s);
//        return new UserDetailsConfig(CurrUser);
//    }
//
//}
