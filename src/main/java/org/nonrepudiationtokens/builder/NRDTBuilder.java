package org.nonrepudiationtokens.builder;

import org.nonrepudiationtokens.encoding.Asn1TokenEncoder;
import org.nonrepudiationtokens.model.MessageImprint;
import org.nonrepudiationtokens.model.NonRepudiationOfDeliveryToken;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public final class NRDTBuilder {

    private String policyId;
    private String recipientId;
    private String originatorId;
    private Instant tokenGenerationTime;
    private Instant receiptTime;
    private MessageImprint subjectImprint;
    private byte[] contextInfo;

    public NRDTBuilder policyId(String policyId) {
        this.policyId = policyId;
        return this;
    }

    public NRDTBuilder recipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public NRDTBuilder originatorId(String originatorId) {
        this.originatorId = originatorId;
        return this;
    }

    public NRDTBuilder tokenGenerationTime(Instant tokenGenerationTime) {
        this.tokenGenerationTime = tokenGenerationTime;
        return this;
    }

    public NRDTBuilder receiptTime(Instant receiptTime) {
        this.receiptTime = receiptTime;
        return this;
    }

    public NRDTBuilder subjectImprint(MessageImprint subjectImprint) {
        this.subjectImprint = subjectImprint;
        return this;
    }

    public NRDTBuilder contextInfo(byte[] contextInfo) {
        this.contextInfo = contextInfo == null ? null : Arrays.copyOf(contextInfo, contextInfo.length);
        return this;
    }

    public NonRepudiationOfDeliveryToken build() {
        validate();
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

    public byte[] buildDer() {
        return Asn1TokenEncoder.encode(build());
    }

    private void validate() {
        Objects.requireNonNull(policyId, "policyId must not be null");
        Objects.requireNonNull(recipientId, "recipientId must not be null");
        Objects.requireNonNull(originatorId, "originatorId must not be null");
        Objects.requireNonNull(tokenGenerationTime, "tokenGenerationTime must not be null");
        Objects.requireNonNull(subjectImprint, "subjectImprint must not be null");
    }
}
