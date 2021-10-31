package com.rad.ms.corona_view.access.Service;

import com.rad.ms.corona_view.access.User;
import com.rad.ms.corona_view.access.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccessService implements IAccessService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // TO-DO: check with Raz how to handle not valid inputs.
    @Override
    public User addUser(User user) {
        if (user == null || user.getPassword() == null || user.getUsername() == null)
            return null;
        if (validUsername(user.getUsername()) && validPassword(user.getPassword())){
            return userRepository.save(user);
        }

        return null;
    }

    @Override
    public User getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null); // if found returns user, else null.
    }

    @Override
    public User updateUser(String userId, User user) {
        Optional<User> user_to_update_opt = userRepository.findById(userId);
        if (user_to_update_opt.isPresent()){
            User user_to_update = user_to_update_opt.get();
            updateNotNullFields(user_to_update, user);
            userRepository.save(user_to_update);
            return user_to_update;
        }
        return null; // CHECK WHAT TO RETURN IN CASE OF FAILURE
    }


    //TO DO: CHECK WHAT DESIRED RETURN VALUE
    @Override
    public boolean deleteUser(String userId) {
        // userRepository.deleteById(userId); - this one is void - can't know if deleted successfully
        Optional<User> user_to_delete_opt = userRepository.findById(userId);
        if (user_to_delete_opt.isPresent()){
            User user_to_delete = user_to_delete_opt.get();
            userRepository.delete(user_to_delete);
            return true;
        }
        return false;
    }

    private void updateNotNullFields(User user_to_update, User updated_user) {
        if (updated_user.getPassword() != null && validPassword((updated_user.getPassword())))
            user_to_update.setPassword(updated_user.getPassword());
        // CHECK IF CAN UPDATE USERNAME (ID) AND IF SO IF NEED TO DELETE OLD RECORD
//        if (updated_user.getUsername() != null && validUsername(updated_user.getUsername()))
//            user_to_update.setUsername(updated_user.getUsername());
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
