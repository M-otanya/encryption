package com.encryption.encryptionSite.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doc {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer Id;
    private String docName;
    private String docType;
//    @ManyToOne
//    private List<User_profile> userDoc;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public Doc(String docName, String docType, byte[] data) {
        this.docName = docName;
        this.docType = docType;
        this.data = data;

    }

    public Doc(byte[] data) {
        this.data = data;
    }
}
