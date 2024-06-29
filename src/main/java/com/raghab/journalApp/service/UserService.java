package com.raghab.journalApp.service;
// controller --> service --> repository
import com.raghab.journalApp.entity.User;
import com.raghab.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user){
        try {
            userRepository.save(user);
        }catch (Exception e){
            System.out.println("Error:"+e);
        }

    }
    public void saveNewUser(User user){
        try {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);

        }catch (Exception e){
            System.out.println("Error:"+e);
        }

    }



  public List<User> getAll(){

        return userRepository.findAll();
  }

   public User findByUserName(String userName ){
       return  userRepository.findByUserName(userName);
   }


   public void removeByUserName(String userName){
        userRepository.deleteByUserName(userName);
   }
}

