package com.comitfy.crm.component;

import com.comitfy.crm.userModule.entity.Role;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.repository.RoleRepository;
import com.comitfy.crm.userModule.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitializeDatabase implements CommandLineRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadRoleData();
        loadUserData();
    }



    private void loadRoleData() {
        if (roleRepository.count() == 0) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }


    }


    private void loadUserData() {

        if (userRepository.count() == 0) {
            User user = new User();
            user.setEmail("admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("Comitfy2022."));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ADMIN").get());
            user.setRoles(roles);


            userRepository.save(user);

        }
        System.out.println(userRepository.count());
    }


}
