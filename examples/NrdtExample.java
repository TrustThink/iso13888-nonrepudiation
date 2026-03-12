import org.nonrepudiationtokens.builder.NRDTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.time.Instant;
import java.util.Arrays;

public class NrdtExample {

    public static void main(String[] args) throws IOException{

        Path filePath = Paths.get("input/ieee1609dot2-signed-message.bin");
        Path outputDir = Paths.get("output");
        Path outputFile = outputDir.resolve("nrdt.bin");
        byte[] referencedSignedObject = Files.readAllBytes(filePath);

        MessageImprint imprint = ImprintCalculator.sha256(referencedSignedObject);

        NRDTBuilder token =new NRDTBuilder()
                .policyId("policy-1")
                .recipientId("distribution-system-A")
                .originatorId("regulation-system-B")
                .tokenGenerationTime(Instant.now())
                .receiptTime(Instant.now())
                .subjectImprint(imprint);

        System.out.println(token.build());

        byte[] unsignedNrdt = token.buildDer();

        System.out.println("Generated NRDT (DER encoded):");
        System.out.println(Arrays.toString(unsignedNrdt));

        Files.write(outputFile, unsignedNrdt);

        System.out.println("NROT length: " + unsignedNrdt.length);

        // // Decoding
        // NonRepudiationToken token = Asn1TokenDecoder.decode(unsignedNrot);

        // System.out.println("Policy: " + token.getPolicyId());
        // System.out.println("Originator: " + token.getOriginatorId());
        // System.out.println("Recipient: " + token.getRecipientId());
    }
}