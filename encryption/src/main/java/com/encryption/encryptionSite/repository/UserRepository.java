package com.encryption.encryptionSite.repository;

import com.encryption.encryptionSite.entity.User_profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User_profile,Integer> {

}
