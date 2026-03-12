package org.nonrepudiationtokens.model;

import java.util.Arrays;
import java.util.Objects;

public final class MessageImprint {

    private final HashAlgorithm hashAlgorithm;
    private final byte[] hashValue;

    public MessageImprint(HashAlgorithm hashAlgorithm, byte[] hashValue) {
        this.hashAlgorithm = Objects.requireNonNull(hashAlgorithm, "hashAlgorithm must not be null");
        this.hashValue = Arrays.copyOf(
                Objects.requireNonNull(hashValue, "hashValue must not be null"),
                hashValue.length
        );
    }

    public HashAlgorithm getHashAlgorithm() {
        return hashAlgorithm;
    }

    public byte[] getHashValue() {
        return Arrays.copyOf(hashValue, hashValue.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MessageImprint{hashAlgorithm=").append(hashAlgorithm);
        sb.append(", hashValue=");
        if (hashValue != null) {
            sb.append(bytesToHex(hashValue));
        } else {
            sb.append("null");
        }
        sb.append('}');
        return sb.toString();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }
}
