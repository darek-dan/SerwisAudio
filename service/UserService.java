package serwisAudio.service;

import serwisAudio.configuration.EncoderAlgorithm;
import serwisAudio.model.Role;
import serwisAudio.model.User;
import serwisAudio.repository.RoleRepository;
import serwisAudio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service            // servis - implementuje logikę aplikacji
public class UserService {
    @Autowired      // wstrzykiwanie zależności
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EncoderAlgorithm encoderAlgorithm;

    public UserDetails getCredentials(Authentication auth) {
        return auth != null ? (UserDetails) auth.getPrincipal() : null;
    }

    public void registerUser(User user) {
        // domyślną rolą po rejestracji jest USER -> id = 1
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1));
        user.setRoles(roles);
        user.setPassword(encoderAlgorithm.getPasswordEncoder().encode(user.getPassword())); // szyfrowanie hasła
        userRepository.save(user);          // INSERT INTO user values (?,?,?,?)
    }

    public void activateUser(int userId) {   // UPDATE user SET status = 1 WHERE user_id = ?;
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(true);
            userRepository.save(user);      // save gdy jest wywoływana na istniejącym w db obiekcie to działa jak update
        }
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsersOrderByRegistrationDateTimeDesc() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "registrationDateTime"));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public void changeUserStatus(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(!user.isStatus());
            userRepository.save(user);
        }
    }

    public void addTrader(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Role> roleOptional = roleRepository.findById(2);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();
            Set<Role> currentRoles = user.getRoles();
            currentRoles.add(role);
            user.setRoles(currentRoles);
            userRepository.save(user);
        }
    }
}
