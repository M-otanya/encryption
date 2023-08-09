package com.encryption.encryptionSite.service;

import com.encryption.encryptionSite.entity.Document;
import com.encryption.encryptionSite.repository.DocumentRepository;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service

public class OriginalDoc {

    @Autowired
    private  final DocumentRepository documentRepository;
    @Autowired
    DocStorageService docStorageService;
      private byte[] bytes1;
       private  String fileNames;
       private String docTypes;
    public void DecryptFileToDB()  {
        this.fileNames =docStorageService.getFileName();
        this.docTypes =docStorageService.getDocType();
        byte[] kingByte =docStorageService.decryptedBytes;
        Document document = new Document(fileNames,docTypes,kingByte);
        documentRepository.save(document);
   }
}
