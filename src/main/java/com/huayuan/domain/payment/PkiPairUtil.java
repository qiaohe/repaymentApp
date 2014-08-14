package com.huayuan.domain.payment;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
            PrivateKey privateKey = (PrivateKey) ks.getKey("test-alias", KEY_PWD);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(signMsg.getBytes("utf-8"));
            return Base64.encodeBase64String(signature.sign());
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException
                | NoSuchAlgorithmException | IOException | SignatureException | InvalidKeyException ex) {
            throw new IllegalStateException("can not load cert from repository.");
        }
    }

    public boolean enCodeByCer(String val, String msg) {
        boolean flag = false;
        try {
            String file = PkiPairUtil.class.getResource("99bill[1].cert.rsa.20140803.cer").toURI().getPath();
            FileInputStream inStream = new FileInputStream(file);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
            PublicKey pk = cert.getPublicKey();
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pk);
            signature.update(val.getBytes());
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            System.out.println(new String(decoder.decodeBuffer(msg)));
            flag = signature.verify(decoder.decodeBuffer(msg));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("no");
        }
        return flag;
    }
}
