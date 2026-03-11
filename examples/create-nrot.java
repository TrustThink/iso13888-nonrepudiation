import org.nonrepudiationtokens.builder.NROTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;

import java.time.Instant;

byte[] referencedSignedObject = "...".getBytes();

MessageImprint imprint = ImprintCalculator.sha256(referencedSignedObject);

byte[] unsignedNrot = new NROTBuilder()
        .policyId("policy-1")
        .originatorId("system-A")
        .recipientId("system-B")
        .tokenGenerationTime(Instant.now())
        .transmissionTime(Instant.now())
        .subjectImprint(imprint)
        .buildDer();
