package samul.shopper.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import samul.shopper.dtos.UserDto;
import samul.shopper.dtos.UserRoleDto;
import samul.shopper.entities.User;
import samul.shopper.mappers.UserMapper;
import samul.shopper.repositories.RoleRepository;
import samul.shopper.repositories.UserRepository;
import samul.shopper.services.UserService;
import samul.shopper.entities.Role;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        Role role = userDto.getRole() == null ? roleRepository.findByName("user") : userDto.getRole();
        userDto.setRole(role);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserRole(UserRoleDto userRoleDto) {
        User user = userRepository.findById(userRoleDto.getId()).orElseThrow();
        Role role = roleRepository.findByName(userRoleDto.getRole());
        user.setRole(role);
        userRepository.save(user);
    }
}
