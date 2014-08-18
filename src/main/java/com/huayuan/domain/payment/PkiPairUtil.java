package com.huayuan.domain.payment;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class PkiPairUtil {
    private static final char[] KEY_PWD = "memedai".toCharArray();

    public String signMsg(String signMsg) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            BufferedInputStream bis = new BufferedInputStream(new ClassPathResource("99bill-rsa.pfx").getInputStream());
            keyStore.load(bis, KEY_PWD);
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("test-alias", KEY_PWD);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(signMsg.getBytes("utf-8"));
            return Base64.encodeBase64String(signature.sign());
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException
                | NoSuchAlgorithmException | IOException | SignatureException | InvalidKeyException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public boolean enCodeByCer(String val, String msg) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ClassPathResource("99bill[1].cert.rsa.20140803.cer").getInputStream());
            PublicKey pk = cert.getPublicKey();
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pk);
            signature.update(val.getBytes());
            return signature.verify(Base64.decodeBase64(msg));
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
