import org.nonrepudiationtokens.builder.NRDTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class NrdtGeneration {

    public static void main(String[] args) throws IOException{

        if(args.length == 0) {
            System.out.println("Usage: make runNRDT <File name>");
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
        Path outputFile = outputDir.resolve("nrdt.bin");
        byte[] referencedSignedObject = Files.readAllBytes(filePath);

        MessageImprint imprint = ImprintCalculator.sha256(referencedSignedObject);

        NRDTBuilder token =new NRDTBuilder()
                .policyId(policyId)
                .recipientId(recipientId)
                .originatorId(originatorId)
                .tokenGenerationTime(Instant.now())
                .receiptTime(Instant.now())
                .subjectImprint(imprint);

        System.out.println(token.build());

        byte[] unsignedNrdt = token.buildDer();

        System.out.println("Generated NRDT (DER encoded):");
        System.out.println(Arrays.toString(unsignedNrdt));

        Files.write(outputFile, unsignedNrdt);

        System.out.println("NROT length: " + unsignedNrdt.length);
    }
}