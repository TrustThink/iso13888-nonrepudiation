package org.nonrepudiationtokens.builder;

import org.nonrepudiationtokens.encoding.Asn1TokenEncoder;
import org.nonrepudiationtokens.model.MessageImprint;
import org.nonrepudiationtokens.model.NonRepudiationOfOriginToken;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public final class NROTBuilder {

    private String policyId;
    private String originatorId;
    private String recipientId;
    private Instant tokenGenerationTime;
    private Instant transmissionTime;
    private MessageImprint subjectImprint;
    private byte[] contextInfo;

    public NROTBuilder policyId(String policyId) {
        this.policyId = policyId;
        return this;
    }

    public NROTBuilder originatorId(String originatorId) {
        this.originatorId = originatorId;
        return this;
    }

    public NROTBuilder recipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public NROTBuilder tokenGenerationTime(Instant tokenGenerationTime) {
        this.tokenGenerationTime = tokenGenerationTime;
        return this;
    }

    public NROTBuilder transmissionTime(Instant transmissionTime) {
        this.transmissionTime = transmissionTime;
        return this;
    }

    public NROTBuilder subjectImprint(MessageImprint subjectImprint) {
        this.subjectImprint = subjectImprint;
        return this;
    }

    public NROTBuilder contextInfo(byte[] contextInfo) {
        this.contextInfo = contextInfo == null ? null : Arrays.copyOf(contextInfo, contextInfo.length);
        return this;
    }

    public NonRepudiationOfOriginToken build() {
        validate();
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

    public byte[] buildDer() {
        return Asn1TokenEncoder.encode(build());
    }

    private void validate() {
        Objects.requireNonNull(policyId, "policyId must not be null");
        Objects.requireNonNull(originatorId, "originatorId must not be null");
        Objects.requireNonNull(recipientId, "recipientId must not be null");
        Objects.requireNonNull(tokenGenerationTime, "tokenGenerationTime must not be null");
        Objects.requireNonNull(subjectImprint, "subjectImprint must not be null");
    }
}
