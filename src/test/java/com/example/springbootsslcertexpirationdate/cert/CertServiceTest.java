package com.example.springbootsslcertexpirationdate.cert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CertServiceTest {

    @Autowired
    CertService service;

    @Test
    void gimmeCerts() throws NoSuchAlgorithmException, IOException, KeyManagementException, CertificateParsingException {
//        Collection<X509Certificate> certs = service.gimmeCerts("https://www.codeinvestigator.com");
        Collection<X509Certificate> certs = service.gimmeCerts("https://codeinvestigator.com");

        for (X509Certificate cert : certs){
            System.out.println("*** CERT ***");
            System.out.println(cert.getSubjectX500Principal());
            System.out.println(cert.getSubjectAlternativeNames());
//            System.out.println(cert.toString());
            System.out.println(cert.getNotBefore());
            System.out.println(cert.getNotAfter());
        }
    }
}