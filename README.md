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

This library does not perform cryptographic signing or message packaging. Its purpose is to construct and encode the evidence tokens themselves. This design allows the library to be easily integrated into existing security architectures.


## Why This Library Exists

ISO/IEC 13888-3 defines mechanisms for generating non-repudiation evidence tokens, but there are very few publicly available reference implementations for creating these tokens. This library was created to provide a simple, open-source implementation that developers can use to:

- Generate ISO/IEC 13888-3 Non-Repudiation of Origin Tokens (NROT)
- Generate ISO/IEC 13888-3 Non-Repudiation of Delivery Tokens (NRDT)
- Test and validate systems that exchange non-repudiation evidence
- Produce token payloads that can be signed and packaged by external security frameworks

The project is designed as a lightweight test and development tool. It focuses on token construction and encoding while leaving cryptographic signing and message packaging to existing security libraries.

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

## Project Structure

src/main/java/     Library source code
examples/          Example token generation programs
lib/               External dependencies
Makefile           Build configuration



## Building the Library 

Requirements:

- Java JDK (11 or newer recommended)
- `make`

Build the library: 

-       make build

Compile example programs: 

- ​      make nrotToken
- ​      make nrdtToken

Run examples:

- ​       make runNROT
- ​       make runNRDT

Clean build artifacts:

- ​      make clean

## Dependencies

This project uses the Bouncy Castle cryptographic library for certain ASN.1 and cryptographic utility functions. Bouncy Castle is licensed under the Bouncy Castle License, a permissive open source license compatible with MIT.

Project website: https://www.bouncycastle.org

The Bouncy Castle provider JAR is included in the repository for convenience.

## Authors

This library was developed by:

Luis Arroyo ()
Brian Russell

TrustThink, LLC

TrustThink is a cybersecurity engineering firm specializing in secure system architecture, transportation, healthcare and defense cybersecurity, and secure communications frameworks.

## Contributing

Contributions are welcome.

If you would like to contribute improvements, bug fixes, or additional token examples:

1. Fork the repository
2. Create a feature branch
3. Submit a pull request

Issues and discussion are encouraged. 
