package samul.shopper.services;

import samul.shopper.dtos.UserDto;
import samul.shopper.dtos.UserRoleDto;
import samul.shopper.entities.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<User> getAllUsers();
    void updateUserRole(UserRoleDto userRoleDto);
}
