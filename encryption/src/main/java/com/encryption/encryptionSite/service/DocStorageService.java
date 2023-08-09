package com.encryption.encryptionSite.service;

import com.encryption.encryptionSite.entity.Doc;
import com.encryption.encryptionSite.entity.Document;
import com.encryption.encryptionSite.repository.DocumentRepository;
import com.encryption.encryptionSite.repository.FileRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
@Service
@Data
public class DocStorageService {
    @Autowired
    private final FileRepository fileRepository;
    @Autowired
    private  final DocumentRepository documentRepository;

    byte[] decryptedBytes;

//    private byte[] bytes1;
    private  String fileName;
    private String docType;


    public DocStorageService(FileRepository fileRepository, DocumentRepository documentRepository) {
        this.fileRepository = fileRepository;
        this.documentRepository = documentRepository;
    }

    public void saveEncryptedFile(MultipartFile multipartFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        this.fileName =multipartFile.getOriginalFilename();
        this.docType = multipartFile.getContentType();
        byte[] filebytes = multipartFile.getBytes();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //initialize the size of the key.
        keyGenerator.init(256);
        // here SecretKey is a key that is used to for encryption and decryption.
        SecretKey secretKey = keyGenerator.generateKey();

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] random = new byte[16];
        secureRandom.nextBytes(random);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(random);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //initializing the cipher object to do encryption.
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        //the actual encryption is done here with the doFinal method.
        byte[] encryptedData =  cipher.doFinal(filebytes);
        //initializing the cipher object to do decryption.
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        //the actual decryption is done here with the doFinal method.
        this.decryptedBytes = cipher.doFinal(encryptedData);

        Doc uploadedFile = new Doc(fileName,docType,encryptedData);

        fileRepository.save(uploadedFile);

    }
    public Optional<Doc>  getFile(Integer fileId){
        return fileRepository.findById(fileId);
    }

    public Optional<Document>  getDocument(Integer fileId){
        return documentRepository.findById(fileId);
    }
    public List<Doc> getFiles(){
        return fileRepository.findAll();
    }
    public List<Document> getDocuments(){
        return documentRepository.findAll();
    }







}

