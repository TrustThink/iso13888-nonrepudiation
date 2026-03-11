package org.nonrepudiationtokens.encoding;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;
import org.nonrepudiationtokens.model.MessageImprint;
import org.nonrepudiationtokens.model.NonRepudiationOfDeliveryToken;
import org.nonrepudiationtokens.model.NonRepudiationOfOriginToken;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public final class Asn1TokenEncoder {

    private static final int SERVICE_TYPE_ORIGIN = 0;
    private static final int SERVICE_TYPE_DELIVERY = 1;

    private Asn1TokenEncoder() {
    }

    public static byte[] encode(NonRepudiationOfOriginToken token) {
        Objects.requireNonNull(token, "token must not be null");

        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(new DERUTF8String(token.getPolicyId()));
        vector.add(new ASN1Enumerated(SERVICE_TYPE_ORIGIN));
        vector.add(new DERUTF8String(token.getOriginatorId()));
        vector.add(new DERUTF8String(token.getRecipientId()));
        vector.add(toGeneralizedTime(token.getTokenGenerationTime()));

        if (token.getTransmissionTime() != null) {
            vector.add(toGeneralizedTime(token.getTransmissionTime()));
        }

        vector.add(encodeImprint(token.getSubjectImprint()));

        if (token.getContextInfo() != null) {
            vector.add(new DEROctetString(token.getContextInfo()));
        }

        return toDer(vector);
    }

    public static byte[] encode(NonRepudiationOfDeliveryToken token) {
        Objects.requireNonNull(token, "token must not be null");

        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(new DERUTF8String(token.getPolicyId()));
        vector.add(new ASN1Enumerated(SERVICE_TYPE_DELIVERY));
        vector.add(new DERUTF8String(token.getRecipientId()));
        vector.add(new DERUTF8String(token.getOriginatorId()));
        vector.add(toGeneralizedTime(token.getTokenGenerationTime()));

        if (token.getReceiptTime() != null) {
            vector.add(toGeneralizedTime(token.getReceiptTime()));
        }

        vector.add(encodeImprint(token.getSubjectImprint()));

        if (token.getContextInfo() != null) {
            vector.add(new DEROctetString(token.getContextInfo()));
        }

        return toDer(vector);
    }

    private static DERSequence encodeImprint(MessageImprint imprint) {
        ASN1EncodableVector vector = new ASN1EncodableVector();
        vector.add(new ASN1Enumerated(imprint.getHashAlgorithm().getAsn1Value()));
        vector.add(new DEROctetString(imprint.getHashValue()));
        return new DERSequence(vector);
    }

    private static DERGeneralizedTime toGeneralizedTime(Instant instant) {
        return new DERGeneralizedTime(Date.from(instant));
    }

    private static byte[] toDer(ASN1EncodableVector vector) {
        try {
            return new DERSequence(vector).getEncoded();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to DER-encode token", e);
        }
    }
}
