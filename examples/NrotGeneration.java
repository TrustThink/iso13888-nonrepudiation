import org.nonrepudiationtokens.builder.NROTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class NrotGeneration {

    public static void main(String[] args) throws IOException{

        if(args.length == 0) {
            System.out.println("Usage: make runNROT <File name>");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter policyID: ");
        String policyId = scanner.nextLine();

        System.out.print("Enter originatorID: ");
        String originatorId = scanner.nextLine();

        System.out.print("Enter recipientID: ");
        String recipientId = scanner.nextLine();

        Path filePath = Paths.get("input/" + args[0]);
        Path outputDir = Paths.get("output");
        Path outputFile = outputDir.resolve("nrot.bin");
        byte[] referencedSignedObject = Files.readAllBytes(filePath);

        MessageImprint imprint = ImprintCalculator.sha256(referencedSignedObject);

        NROTBuilder token = new NROTBuilder()
                .policyId(policyId)
                .originatorId(originatorId)
                .recipientId(recipientId)
                .tokenGenerationTime(Instant.now())
                .transmissionTime(Instant.now())
                .subjectImprint(imprint);

        System.out.println(token.build());

        byte[] unsignedNrot = token.buildDer();

        System.out.println("Generated NROT (DER encoded):");
        System.out.println(Arrays.toString(unsignedNrot));

        Files.write(outputFile, unsignedNrot);

        System.out.println("NROT length: " + unsignedNrot.length);
    }
}