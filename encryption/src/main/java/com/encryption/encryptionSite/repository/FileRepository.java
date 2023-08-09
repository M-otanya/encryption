package com.encryption.encryptionSite.repository;

import com.encryption.encryptionSite.entity.Doc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Doc,Integer> {
}
