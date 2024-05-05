package samul.shopper.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samul.shopper.dtos.OrderStatusDto;
import samul.shopper.dtos.UserRoleDto;
import samul.shopper.entities.User;
import samul.shopper.services.UserService;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/users")
public class UserController {

    private UserService userService;

    @GetMapping
    private ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<String> updateUserRole(@RequestBody UserRoleDto userRoleDto){
        try {
            userService.updateUserRole(userRoleDto);
            return ResponseEntity.ok("User role updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
