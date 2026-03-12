# iso13888-nonrepudiation

Java library for generating ISO/IEC 13888-3 non-repudiation evidence tokens, including:

- Non-Repudiation of Origin Tokens (NROT)
- Non-Repudiation of Delivery Tokens (NRDT)

The library constructs unsigned ASN.1 tokens compliant with ISO/IEC 13888-3. These tokens can then be signed and packaged using external cryptographic libraries or security frameworks.

This project intentionally separates token construction from cryptographic signing, making it easy to integrate with existing security stacks.


# Overview

This library generates ASN.1-encoded token payloads that represent non-repudiation evidence according to ISO/IEC 13888-3.

The generated tokens are intended to be:

1. Created as unsigned ASN.1 structures  
2. Digitally signed using an external cryptographic library  
3. Encapsulated within a signed message container such as:

- IEEE 1609.2 SignedData
- ETSI TS 103 097 SignedData

The library does not perform cryptographic signing or message packaging.


## Why This Library Exists

ISO/IEC 13888-3 defines mechanisms for generating non-repudiation evidence tokens, but there are very few publicly available reference implementations for creating these tokens.

This library was created to provide a simple, open-source implementation that developers can use to:

- Generate ISO/IEC 13888-3 Non-Repudiation of Origin Tokens (NROT)
- Generate ISO/IEC 13888-3 Non-Repudiation of Delivery Tokens (NRDT)
- Test and validate systems that exchange non-repudiation evidence
- Produce token payloads that can be signed and packaged by external security frameworks

The project is intentionally designed as a lightweight test and development tool. It focuses on token construction and encoding while leaving cryptographic signing and message packaging to existing security libraries.

This makes the library useful for:

- Testing implementations of ISO/IEC 13888-3
- Generating example tokens for interoperability testing
- Prototyping systems that require non-repudiation evidence
- Demonstrating how evidence tokens can be integrated into secure messaging systems

# Features

- ISO/IEC 13888-3 token structures
- Generation of NROT and NRDT
- Message imprint generation (SHA-256 / SHA-384 / SHA-512)
- ASN.1 DER encoding
- ASN.1 token decoding
- Token builder APIs for safe construction
