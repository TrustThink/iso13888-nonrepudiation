# iso13888-nonrepudiation
Java library for generating ISO/IEC 13888-3 non-repudiation evidence tokens (NROT and NRDT). The library constructs unsigned tokens compliant with ISO 13888-3 and allows integration with external cryptographic libraries for digital signature operations.


# ISO 13888 Non-Repudiation Library

The ISO 13888-3 token library generates unsigned Non-Repudiation of Origin Tokens (NROT) and Non-Repudiation of Delivery Tokens (NRDT) as ASN.1-encoded payloads. These payloads are intended to be signed and encapsulated by external processes using IEEE 1609.2 or ETSI TS 103 097 signed data structures. The library does not perform cryptographic signing or signed-message packaging.

## Features

- ISO 13888-3 compliant token structures
- Imprint generation
- ASN.1 encoding
- Token builders
