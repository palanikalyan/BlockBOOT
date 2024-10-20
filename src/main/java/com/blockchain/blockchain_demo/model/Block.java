package com.blockchain.blockchain_demo.model;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class Block {
    private final String data;
    private final LocalDateTime timestamp;
    private final String previousHash;
    private final String hash;
    private int nonce;
    private static final int DIFFICULTY = 4; // Number of leading zeros required in hash

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = LocalDateTime.now();
        this.nonce = 0;
        this.hash = mineBlock();
    }

    private String mineBlock() {
        String target = "0".repeat(DIFFICULTY);
        String calculatedHash;
        do {
            nonce++;
            calculatedHash = calculateHash();
        } while (!calculatedHash.substring(0, DIFFICULTY).equals(target));
        return calculatedHash;
    }

    private String calculateHash() {
        String dataToHash = data + timestamp.toString() + previousHash + nonce;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }

    // Getters
    public String getData() { return data; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }
    public int getNonce() { return nonce; }
}