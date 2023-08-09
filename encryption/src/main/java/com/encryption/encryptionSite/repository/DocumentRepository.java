package com.encryption.encryptionSite.repository;

import com.encryption.encryptionSite.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
