package com.encryption.encryptionSite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User_profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long userId;
    @Column
    private String userGmailString;
    @Column
    private String userPassword;
//    @OneToMany(mappedBy ="User_profile" )
//    private List<Doc> userFile;





}
