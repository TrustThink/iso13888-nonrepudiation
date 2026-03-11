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
}
