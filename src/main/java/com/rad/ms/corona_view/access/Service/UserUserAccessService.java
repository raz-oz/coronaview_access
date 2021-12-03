package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.ErrorHandling.InvalidInputException;
import com.rad.ms.corona_view.access.Entities.User;
import com.rad.ms.corona_view.access.ErrorHandling.UserNotFoundException;
import com.rad.ms.corona_view.access.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class UserUserAccessService implements IUserAccessService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IRoleAccessService roleAccessService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = getUser(username);
       Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       authorities.add(new SimpleGrantedAuthority(user.getRoleId()));
       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        if (user == null || user.getPassword() == null || user.getUsername() == null || user.getRoleId() == null)
            throw new InvalidInputException("Request has to include password, username and role id.");
        if (validUsername(user.getUsername()) && validPassword(user.getPassword()) && roleAccessService.existsById(user.getRoleId())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }

        throw new InvalidInputException("A new user must include valid username, password and role id.");
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
    }

    public User updateUser(String userId, User user) {
        if (!userId.equals(user.getUsername())){
            throw new InvalidInputException("Can not update the user username");
        }
        Optional<User> user_to_update_opt = userRepository.findById(userId);
        if (user_to_update_opt.isPresent()){
            User user_to_update = user_to_update_opt.get();
            updateNotNullFields(user_to_update, user);
            userRepository.save(user_to_update);
            return user_to_update;
        }
        else
            throw new UserNotFoundException(userId);
    }


    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private void updateNotNullFields(User user_to_update, User updated_user) {
        if (updated_user.getPassword() != null && validPassword((updated_user.getPassword())))
            user_to_update.setPassword(updated_user.getPassword());
        if (updated_user.getEmail() != null)
            user_to_update.setEmail(updated_user.getEmail());
        if (updated_user.getCellphoneNumber() != null)
            user_to_update.setCellphoneNumber(updated_user.getCellphoneNumber());
        if (updated_user.getAccountNonExpired() != null)
            user_to_update.setAccountNonExpired(updated_user.getAccountNonExpired());
        if (updated_user.getAccountNonLocked() != null)
            user_to_update.setAccountNonLocked(updated_user.getAccountNonLocked());
        if (updated_user.getCredentialsNonExpired() != null)
            user_to_update.setCredentialsNonExpired(updated_user.getCredentialsNonExpired());
        if (updated_user.getEnabled() != null)
            user_to_update.setEnabled(updated_user.getEnabled());
        if (updated_user.getRoleId() != null && roleAccessService.existsById(updated_user.getRoleId())){
            user_to_update.setRoleId(updated_user.getRoleId());
        }
    }

    private boolean validPassword(String password) {
        int password_length = password.length();
        boolean valid;
        if (password_length < 8 || password_length > 20)
            valid = false;
        else{
            boolean hasNumeric = false, hasLowerCase = false, hasUpperCase = false, hasSpecialSymbol = false;
            boolean hasAll = false;
            ArrayList<Character> specialSymbols = getSpecialSymbols();
            for (int i = 0; i < password.length() && !hasAll; i++) {
                char curr = password.charAt(i);
                if (curr >= '0' && curr <= '9')
                    hasNumeric = true;
                else if (curr >= 'a' && curr <= 'z')
                    hasLowerCase = true;
                else if (curr >= 'A' && curr <= 'Z')
                    hasUpperCase = true;
                else if (specialSymbols.contains(curr))
                    hasSpecialSymbol = true;
                hasAll = hasNumeric & hasLowerCase & hasUpperCase & hasSpecialSymbol;
            }
            valid = hasAll;
        }
        return valid;
    }

    private ArrayList<Character> getSpecialSymbols() {
        ArrayList<Character> specialSymbols = new ArrayList<>();
        specialSymbols.add('@');specialSymbols.add('#');
        specialSymbols.add('$');specialSymbols.add('%');
        return specialSymbols;
    }

    private boolean validUsername(String username) {
        boolean valid;
        if (username.length() < 4 || username.length() > 20)
            valid = false;
        else {
            valid = !userRepository.existsById(username); // if user exist, then username is not valid.
        }
        return valid;
    }


}
