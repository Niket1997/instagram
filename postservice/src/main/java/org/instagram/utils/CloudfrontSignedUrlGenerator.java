package org.instagram.utils;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;


@Component
@Slf4j
public class CloudfrontSignedUrlGenerator {
    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;

    @Value("${aws.cloudfront.key-pair-id}")
    private String keyPairId;

    private String privateKeyFilePath = "/private_key.der";

    public String getSignedUrl(String key) throws IOException, InvalidKeySpecException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        URL filePathURL = CloudfrontSignedUrlGenerator.class.getResource(privateKeyFilePath);
        if (filePathURL == null) {
            throw new IOException("File not found");
        }
        File file = new File(filePathURL.getFile());
        Date expirationDate = new Date(new Date().getTime() + 1000 * 60 * 60); // 1 hour expiration
        String newSignedUrl = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(SignerUtils.Protocol.https, cloudFrontDomain, file, key, keyPairId, expirationDate);

        log.info("URL is " + newSignedUrl);
        return newSignedUrl;
    }

}
