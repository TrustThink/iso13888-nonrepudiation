import org.nonrepudiationtokens.builder.NROTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.time.Instant;
import java.util.Arrays;

public class NrotExample {

    public static void main(String[] args) throws IOException{

        Path filePath = Paths.get("input/ieee1609dot2-signed-message.bin");
        Path outputDir = Paths.get("output");
        Path outputFile = outputDir.resolve("nrot.bin");
        byte[] referencedSignedObject = Files.readAllBytes(filePath);

        MessageImprint imprint = ImprintCalculator.sha256(referencedSignedObject);

        NROTBuilder token = new NROTBuilder()
                .policyId("policy-1")
                .originatorId("distribution-system-A")
                .recipientId("regulation-system-B")
                .tokenGenerationTime(Instant.now())
                .transmissionTime(Instant.now())
                .subjectImprint(imprint);

        System.out.println(token.build());

        byte[] unsignedNrot = token.buildDer();

        System.out.println("Generated NROT (DER encoded):");
        System.out.println(Arrays.toString(unsignedNrot));

        Files.write(outputFile, unsignedNrot);

        System.out.println("NROT length: " + unsignedNrot.length);

        // // Decoding
        // NonRepudiationToken token = Asn1TokenDecoder.decode(unsignedNrot);

        // System.out.println("Policy: " + token.getPolicyId());
        // System.out.println("Originator: " + token.getOriginatorId());
        // System.out.println("Recipient: " + token.getRecipientId());
    }
}