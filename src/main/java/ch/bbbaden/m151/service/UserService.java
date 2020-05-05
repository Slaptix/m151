package ch.bbbaden.m151.service;

import ch.bbbaden.m151.entity.Role;
import ch.bbbaden.m151.entity.User;
import ch.bbbaden.m151.repository.RoleRepository;
import ch.bbbaden.m151.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public User saveAdmin(User admin) {
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        admin.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        admin.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(admin);
    }

}