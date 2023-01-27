package com.kull.citylist.service;

import com.kull.citylist.exception.RoleException;
import com.kull.citylist.exception.UserException;
import com.kull.citylist.model.Role;
import com.kull.citylist.model.User;
import com.kull.citylist.repository.RoleRepository;
import com.kull.citylist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public record RoleService(RoleRepository repository, UserRepository userRepository) {

    private final static String USER_MESSAGE = "User not found";
    private final static String ROLE_MESSAGE = "Role not found";

    public void save(Role Role) {
        repository.save(Role);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public Optional findById(String id) {
        return repository.findById(id);
    }

    public List<Role> findAll() {
        return repository.findAll();
    }

    public void assignUserRole(String roleId, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(USER_MESSAGE));
        Role role = repository.findById(roleId).orElseThrow(() -> new RoleException(ROLE_MESSAGE));
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public void removeUserRole(String roleId, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(USER_MESSAGE));
        user.getRoles().removeIf(role -> role.getId().equals(roleId));
        userRepository.save(user);
    }

    public Set<Role> getUserRoles(User user) {
        return user.getRoles();
    }


}
