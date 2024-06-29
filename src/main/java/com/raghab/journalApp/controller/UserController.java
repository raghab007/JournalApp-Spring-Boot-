package com.raghab.journalApp.controller;
import com.raghab.journalApp.entity.User;
import com.raghab.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser() {
        List<User> all = userService.getAll();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




    @PutMapping
    public ResponseEntity<?> updateById(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName  = authentication.getName();
        try {
            User oldUser = userService.findByUserName(userName);
            if (oldUser != null) {
                oldUser.setUserName(user.getUserName()!= null && !user.getUserName().isEmpty() ? user.getUserName() : oldUser.getUserName());
                oldUser.setPassword(user.getPassword()!= null && !user.getPassword().isEmpty() ? user.getPassword() : oldUser.getPassword());
                userService.saveEntry(oldUser);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUserName(@RequestBody User user) {
        try {
            User old = userService.findByUserName(user.getUserName());
            if (old != null) {
                userService.removeByUserName(old.getUserName());
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
