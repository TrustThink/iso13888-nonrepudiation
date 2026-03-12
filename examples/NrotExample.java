import org.nonrepudiationtokens.builder.NROTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.time.Instant;

public class NrotExample {

    public static void main(String[] args) throws IOException{

        Path filePath = Paths.get("input/ieee1609dot2-signed-message.bin");
        byte[] referencedSignedObject = Files.readAllBytes(filePath);

        MessageImprint imprint = ImprintCalculator.sha256(referencedSignedObject);

        byte[] unsignedNrot = new NROTBuilder()
                .policyId("policy-1")
                .originatorId("distribution-system-A")
                .recipientId("regulation-system-B")
                .tokenGenerationTime(Instant.now())
                .transmissionTime(Instant.now())
                .subjectImprint(imprint)
                .buildDer();

        System.out.println("NROT length: " + unsignedNrot.length);

        // // Decoding
        // NonRepudiationToken token = Asn1TokenDecoder.decode(unsignedNrot);

        // System.out.println("Policy: " + token.getPolicyId());
        // System.out.println("Originator: " + token.getOriginatorId());
        // System.out.println("Recipient: " + token.getRecipientId());
    }
}