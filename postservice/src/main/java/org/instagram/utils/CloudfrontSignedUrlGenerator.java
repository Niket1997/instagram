package org.instagram.utils;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;


@Component
@Slf4j
public class CloudfrontSignedUrlGenerator {
    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;

    @Value("${aws.cloudfront.key-pair-id}")
    private String keyPairId;

    @Value("${aws.cloudfront.private-key}")
    private String privateKey;

    public String generateSignedUrlForResource(String key) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] privateKeyByteArray = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByteArray);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey myPrivateKey = keyFactory.generatePrivate(keySpec);
        Date expirationDate = new Date(new Date().getTime() + 1000 * 60 * 10); // 10 minutes validity
        String resourceUrl = "https://" + cloudFrontDomain + "/" + key;
        String newSignedUrl = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(resourceUrl, keyPairId, myPrivateKey, expirationDate);
        log.info("URL is " + newSignedUrl);
        return newSignedUrl;
    }

}
