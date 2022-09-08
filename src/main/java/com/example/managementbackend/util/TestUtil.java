package com.example.managementbackend.util;

import com.example.managementbackend.configuration.CustomJasyptEncryptorConfig;
import org.jasypt.encryption.StringEncryptor;

public class TestUtil {

    public static void main(String[] args) {

        CustomJasyptEncryptorConfig obj = new CustomJasyptEncryptorConfig();
        StringEncryptor stringEncryptor = obj.stringEncryptor();
        String userName = "root";
        String password = "newpassword";
        String encryptedUserName = stringEncryptor.encrypt(userName);
        String encryptedPassword = stringEncryptor.encrypt(password);
        System.out.println("encryptedUserName : " + encryptedUserName);
        System.out.println("encryptedPassword : " + encryptedPassword);

    }
}
