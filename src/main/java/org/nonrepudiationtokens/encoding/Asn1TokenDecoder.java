package org.nonrepudiationtokens.encoding;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.nonrepudiationtokens.model.HashAlgorithm;
import org.nonrepudiationtokens.model.MessageImprint;
import org.nonrepudiationtokens.model.NonRepudiationOfDeliveryToken;
import org.nonrepudiationtokens.model.NonRepudiationOfOriginToken;
import org.nonrepudiationtokens.model.NonRepudiationToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public final class Asn1TokenDecoder {

    private static final int SERVICE_TYPE_ORIGIN = 0;
    private static final int SERVICE_TYPE_DELIVERY = 1;

    private Asn1TokenDecoder() {
    }

    public static NonRepudiationToken decode(byte[] der) {
        Objects.requireNonNull(der, "der must not be null");

        ASN1Sequence sequence = readTopLevelSequence(der);

        int serviceType = readEnumerated(sequence.getObjectAt(1));

        if (serviceType == SERVICE_TYPE_ORIGIN) {
            return decodeNROT(der);
        }
        if (serviceType == SERVICE_TYPE_DELIVERY) {
            return decodeNRDT(der);
        }

        throw new IllegalArgumentException("Unknown serviceType: " + serviceType);
    }

    public static NonRepudiationOfOriginToken decodeNROT(byte[] der) {
        Objects.requireNonNull(der, "der must not be null");

        ASN1Sequence sequence = readTopLevelSequence(der);
        int size = sequence.size();

        if (size != 6 && size != 7 && size != 8) {
            throw new IllegalArgumentException("Unexpected NROT element count: " + size);
        }

        String policyId = readUtf8(sequence.getObjectAt(0));
        int serviceType = readEnumerated(sequence.getObjectAt(1));
        if (serviceType != SERVICE_TYPE_ORIGIN) {
            throw new IllegalArgumentException("DER does not contain an NROT. serviceType=" + serviceType);
        }

        String originatorId = readUtf8(sequence.getObjectAt(2));
        String recipientId = readUtf8(sequence.getObjectAt(3));
        Instant tokenGenerationTime = readTime(sequence.getObjectAt(4));

        Instant transmissionTime = null;
        MessageImprint subjectImprint;
        byte[] contextInfo = null;

        int nextIndex = 5;
        ASN1Encodable next = sequence.getObjectAt(nextIndex);

        if (next instanceof ASN1GeneralizedTime) {
            transmissionTime = readTime(next);
            nextIndex++;
        }

        subjectImprint = readImprint(sequence.getObjectAt(nextIndex));
        nextIndex++;

        if (nextIndex < size) {
            contextInfo = readOctets(sequence.getObjectAt(nextIndex));
        }

        return new NonRepudiationOfOriginToken(
                policyId,
                originatorId,
                recipientId,
                tokenGenerationTime,
                transmissionTime,
                subjectImprint,
                contextInfo
        );
    }

    public static NonRepudiationOfDeliveryToken decodeNRDT(byte[] der) {
        Objects.requireNonNull(der, "der must not be null");

        ASN1Sequence sequence = readTopLevelSequence(der);
        int size = sequence.size();

        if (size != 6 && size != 7 && size != 8) {
            throw new IllegalArgumentException("Unexpected NRDT element count: " + size);
        }

        String policyId = readUtf8(sequence.getObjectAt(0));
        int serviceType = readEnumerated(sequence.getObjectAt(1));
        if (serviceType != SERVICE_TYPE_DELIVERY) {
            throw new IllegalArgumentException("DER does not contain an NRDT. serviceType=" + serviceType);
        }

        String recipientId = readUtf8(sequence.getObjectAt(2));
        String originatorId = readUtf8(sequence.getObjectAt(3));
        Instant tokenGenerationTime = readTime(sequence.getObjectAt(4));

        Instant receiptTime = null;
        MessageImprint subjectImprint;
        byte[] contextInfo = null;

        int nextIndex = 5;
        ASN1Encodable next = sequence.getObjectAt(nextIndex);

        if (next instanceof ASN1GeneralizedTime) {
            receiptTime = readTime(next);
            nextIndex++;
        }

        subjectImprint = readImprint(sequence.getObjectAt(nextIndex));
        nextIndex++;

        if (nextIndex < size) {
            contextInfo = readOctets(sequence.getObjectAt(nextIndex));
        }

        return new NonRepudiationOfDeliveryToken(
                policyId,
                recipientId,
                originatorId,
                tokenGenerationTime,
                receiptTime,
                subjectImprint,
                contextInfo
        );
    }

    private static ASN1Sequence readTopLevelSequence(byte[] der) {
        try (ASN1InputStream inputStream = new ASN1InputStream(new ByteArrayInputStream(der))) {
            return ASN1Sequence.getInstance(inputStream.readObject());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse DER token", e);
        }
    }

    private static String readUtf8(ASN1Encodable encodable) {
        return ((ASN1String) encodable).getString();
    }

    private static int readEnumerated(ASN1Encodable encodable) {
        ASN1Enumerated enumerated = ASN1Enumerated.getInstance(encodable);
        BigInteger value = enumerated.getValue();
        return value.intValueExact();
    }

    private static Instant readTime(ASN1Encodable encodable) {
        try {
            Date date = ASN1GeneralizedTime.getInstance(encodable).getDate();
            return date.toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid GeneralizedTime value", e);
        }
    }

    private static byte[] readOctets(ASN1Encodable encodable) {
        return ASN1OctetString.getInstance(encodable).getOctets();
    }

    private static MessageImprint readImprint(ASN1Encodable encodable) {
        ASN1Sequence sequence = ASN1Sequence.getInstance(encodable);
        if (sequence.size() != 2) {
            throw new IllegalArgumentException("MessageImprint must contain exactly 2 elements");
        }

        int algorithmValue = readEnumerated(sequence.getObjectAt(0));
        byte[] hashValue = readOctets(sequence.getObjectAt(1));

        return new MessageImprint(
                HashAlgorithm.fromAsn1Value(algorithmValue),
                hashValue
        );
    }
}
