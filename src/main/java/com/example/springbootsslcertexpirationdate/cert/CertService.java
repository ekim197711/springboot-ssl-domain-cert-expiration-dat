package com.example.springbootsslcertexpirationdate.cert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class CertService {

    public Collection<X509Certificate> gimmeCerts(String urlstr)
            throws KeyManagementException,
            NoSuchAlgorithmException,
            IOException {
        List<X509Certificate> xcerts = new ArrayList<>();
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(new KeyManager[0], new TrustManager[] {new NotSecureTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        URL url = new URL(urlstr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier((String arg0, SSLSession arg1) -> true);
        conn.connect();
        Certificate[] certs = conn.getServerCertificates();
        for (Certificate cert :certs){
            X509Certificate xcert = (X509Certificate)cert;
            xcerts.add(xcert);
        }
        conn.disconnect();
        return xcerts;
    }

    private static class NotSecureTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
