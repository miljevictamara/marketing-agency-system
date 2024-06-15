package com.bsep.marketingacency.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
public class KeyStoreAccess {

    @Id
    public String fileName;

    public String filePass;

    public KeyStoreAccess(String fileName, String filePass) {
        this.fileName = fileName;
        this.filePass = filePass;
    }

    public KeyStoreAccess() {}


}
