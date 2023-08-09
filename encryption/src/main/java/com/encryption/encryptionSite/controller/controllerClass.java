package com.encryption.encryptionSite.controller;

import com.encryption.encryptionSite.entity.Doc;
import com.encryption.encryptionSite.entity.Document;
import com.encryption.encryptionSite.entity.User_profile;
import com.encryption.encryptionSite.repository.DocumentRepository;
import com.encryption.encryptionSite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class controllerClass {

    @Autowired
    private final DocStorageService docStorageService;

    @Autowired
    OriginalDoc originalDoc;
    @Autowired
    private final UserService userService;

    public controllerClass(DocStorageService docStorageService,UserService userService) {
        this.docStorageService = docStorageService;

        this.userService = userService;
    }
    @GetMapping("/")
    public String showSigninPage(){
        return "signin";
    }
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }
    @GetMapping("/signin")
    public String showSignin1Page() {
        return "redirect:/";
    }
    @GetMapping("/tooriginaldocs")
    public String toDecryptedPage(){
        return "redirect:/original";
    }
    @GetMapping("/todecryptedocs")
    public String toEncryptedPage(){
        return "redirect:/king";
    }
    @GetMapping("/king")
    public String get(Model model){
         List<Doc> docs = docStorageService.getFiles();
         model.addAttribute("docs", docs);
         return "king";
    }
    @GetMapping("/original")
    public String getEncrypted(Model model){
        List<Document> documents = docStorageService.getDocuments();
        model.addAttribute("document", documents);
        return "original";
    }
    @PostMapping("/uploadDecryptedFiles")
    public String uploadMultipleFiles() {
          originalDoc.DecryptFileToDB();
        return "redirect:/original";
    }
    @PostMapping("/uploadFiles")
    public String uploadMultipleEncryptedFiles(@RequestParam("files")MultipartFile [] files) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        for (MultipartFile file:files){
            docStorageService.saveEncryptedFile(file);
        }
        return "redirect:/king";
    }

//method to get an encrypted file from the database
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId) {
        Doc file = docStorageService.getFile(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename =\"" + file.getDocName() + "")
                .body(new ByteArrayResource(file.getData()));
    }
    //method to get decrypted/original file from the database.
    @GetMapping("/downloadFiles/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadOriginalFile(@PathVariable Integer fileId) {
        Document file = docStorageService.getDocument(fileId).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename =\"" + file.getDocName() + "")
                .body(new ByteArrayResource(file.getEncryptedData()));
    }




    @PostMapping("/register")
    public String addUser(@ModelAttribute User_profile user){
        userService.register(user);
        return "redirect:/king";
    }

    @PostMapping("/newRegister")
    public String addNewUser(@ModelAttribute User_profile user){
        userService.register(user);
        return "redirect:/";
    }

}
