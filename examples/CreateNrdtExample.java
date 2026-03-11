import org.nonrepudiationtokens.builder.NRDTBuilder;
import org.nonrepudiationtokens.imprint.ImprintCalculator;
import org.nonrepudiationtokens.model.MessageImprint;

import java.time.Instant;
import java.util.Arrays;

public class CreateNrdtExample {

    public static void main(String[] args) {

        try {

            /*
             * In a real system this would be the FULL encoded signed object
             * that the receiver has validated (e.g. IEEE 1609.2 SignedData).
             */
            byte[] referencedSignedObject =
                    "Example signed message payload".getBytes();

            /*
             * Compute the imprint over the referenced signed object.
             * This becomes Imp(m) in the ISO 13888 token.
             */
            MessageImprint imprint =
                    ImprintCalculator.sha256(referencedSignedObject);

            /*
             * Build the NRDT token.
             */
            byte[] unsignedNrdt =
                    new NRDTBuilder()
                            .policyId("example-policy-1")
                            .recipientId("distribution-system-A")
                            .originatorId("regulation-system-B")
                            .tokenGenerationTime(Instant.now())
                            .receiptTime(Instant.now())
                            .subjectImprint(imprint)
                            .buildDer();

            /*
             * Print token bytes.
             * In a real system this byte array would be signed and
             * packaged into an IEEE 1609.2 SignedData structure.
             */
            System.out.println("Generated NRDT (DER encoded):");
            System.out.println(Arrays.toString(unsignedNrdt));

            System.out.println("\nToken length: " + unsignedNrdt.length + " bytes");

        } catch (Exception e) {

            System.err.println("Failed to create NRDT:");
            e.printStackTrace();

        }
    }
}
