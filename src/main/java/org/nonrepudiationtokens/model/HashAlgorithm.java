package org.nonrepudiationtokens.model;

public enum HashAlgorithm {

    SHA256("SHA-256", 0),
    SHA384("SHA-384", 1),
    SHA512("SHA-512", 2);

    private final String jcaName;
    private final int asn1Value;

    HashAlgorithm(String jcaName, int asn1Value) {
        this.jcaName = jcaName;
        this.asn1Value = asn1Value;
    }

    public String getJcaName() {
        return jcaName;
    }

    public int getAsn1Value() {
        return asn1Value;
    }

    public static HashAlgorithm fromAsn1Value(int value) {
        for (HashAlgorithm algorithm : values()) {
            if (algorithm.asn1Value == value) {
                return algorithm;
            }
        }
        throw new IllegalArgumentException("Unsupported ASN.1 HashAlgorithm value: " + value);
    }
}
