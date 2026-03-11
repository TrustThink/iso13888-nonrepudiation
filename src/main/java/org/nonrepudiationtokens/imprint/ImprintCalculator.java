package org.nonrepudiationtokens.imprint;

import org.nonrepudiationtokens.model.HashAlgorithm;
import org.nonrepudiationtokens.model.MessageImprint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public final class ImprintCalculator {

    private ImprintCalculator() {
    }

    public static MessageImprint compute(HashAlgorithm algorithm, byte[] data) {
        Objects.requireNonNull(algorithm, "algorithm must not be null");
        Objects.requireNonNull(data, "data must not be null");

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm.getJcaName());
            byte[] hash = digest.digest(data);
            return new MessageImprint(algorithm, hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("JCA algorithm not available: " + algorithm.getJcaName(), e);
        }
    }

    public static MessageImprint sha256(byte[] data) {
        return compute(HashAlgorithm.SHA256, data);
    }

    public static MessageImprint sha384(byte[] data) {
        return compute(HashAlgorithm.SHA384, data);
    }

    public static MessageImprint sha512(byte[] data) {
        return compute(HashAlgorithm.SHA512, data);
    }
}
