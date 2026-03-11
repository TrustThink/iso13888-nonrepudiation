byte[] tokenBytes = ...;

NonRepudiationToken token = Asn1TokenDecoder.decode(tokenBytes);

System.out.println("Policy: " + token.getPolicyId());
System.out.println("Originator: " + token.getOriginatorId());
System.out.println("Recipient: " + token.getRecipientId());
