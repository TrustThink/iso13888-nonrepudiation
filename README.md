# iso13888-nonrepudiation
Java library for generating ISO/IEC 13888-3 non-repudiation evidence tokens (NROT and NRDT). The library constructs unsigned tokens compliant with ISO 13888-3 and allows integration with external cryptographic libraries for digital signature operations.


# ISO 13888 Non-Repudiation Library

This library implements ISO/IEC 13888-3 non-repudiation evidence tokens.

Supported tokens:

- Non-Repudiation of Origin Token (NROT)
- Non-Repudiation of Delivery Token (NRDT)

The library generates unsigned tokens and computes message imprints.
Digital signatures are applied using external cryptographic libraries.

## Features

- ISO 13888-3 compliant token structures
- Imprint generation
- ASN.1 encoding
- Token builders
