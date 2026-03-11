package org.nonrepudiationtokens.model;

import java.time.Instant;

public interface NonRepudiationToken {

    String getPolicyId();

    String getOriginatorId();

    String getRecipientId();

    Instant getTokenGenerationTime();

    MessageImprint getSubjectImprint();

    byte[] getContextInfo();
}
