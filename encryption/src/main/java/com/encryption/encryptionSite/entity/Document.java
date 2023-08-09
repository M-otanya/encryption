package com.encryption.encryptionSite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Lob
    private String docName;
    private String docType;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] encryptedData;

    public Document(String docName, String docType, byte[] encryptedData) {
        this.docName = docName;
        this.docType = docType;
        this.encryptedData = encryptedData;
    }
}
