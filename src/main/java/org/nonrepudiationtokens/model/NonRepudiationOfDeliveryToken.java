package org.nonrepudiationtokens.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public final class NonRepudiationOfDeliveryToken implements NonRepudiationToken {

    private final String policyId;
    private final String recipientId;
    private final String originatorId;
    private final Instant tokenGenerationTime;
    private final Instant receiptTime;
    private final MessageImprint subjectImprint;
    private final byte[] contextInfo;

    public NonRepudiationOfDeliveryToken(
            String policyId,
            String recipientId,
            String originatorId,
            Instant tokenGenerationTime,
            Instant receiptTime,
            MessageImprint subjectImprint,
            byte[] contextInfo) {

        this.policyId = Objects.requireNonNull(policyId, "policyId must not be null");
        this.recipientId = Objects.requireNonNull(recipientId, "recipientId must not be null");
        this.originatorId = Objects.requireNonNull(originatorId, "originatorId must not be null");
        this.tokenGenerationTime = Objects.requireNonNull(tokenGenerationTime, "tokenGenerationTime must not be null");
        this.receiptTime = receiptTime;
        this.subjectImprint = Objects.requireNonNull(subjectImprint, "subjectImprint must not be null");
        this.contextInfo = contextInfo == null ? null : Arrays.copyOf(contextInfo, contextInfo.length);
    }

    @Override
    public String getPolicyId() {
        return policyId;
    }

    @Override
    public String getRecipientId() {
        return recipientId;
    }

    @Override
    public String getOriginatorId() {
        return originatorId;
    }

    @Override
    public Instant getTokenGenerationTime() {
        return tokenGenerationTime;
    }

    public Instant getReceiptTime() {
        return receiptTime;
    }

    @Override
    public MessageImprint getSubjectImprint() {
        return subjectImprint;
    }

    @Override
    public byte[] getContextInfo() {
        return contextInfo == null ? null : Arrays.copyOf(contextInfo, contextInfo.length);
    }
}
