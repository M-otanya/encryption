package com.encryption.encryptionSite.service;

import com.encryption.encryptionSite.entity.User_profile;
import com.encryption.encryptionSite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserService  {
    @Autowired
    public UserRepository userRepository;
//    public List<User_profile> userList;

    public void register(User_profile user ){
        userRepository.save(user);
    }

}
