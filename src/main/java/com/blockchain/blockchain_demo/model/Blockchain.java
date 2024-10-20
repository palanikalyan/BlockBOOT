package com.blockchain.blockchain_demo.model;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        // Create the genesis block
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block("Genesis Block", "0");
    }

    public void addBlock(String data) {
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Block data cannot be empty");
        }
        Block previousBlock = getLatestBlock();
        Block newBlock = new Block(data, previousBlock.getHash());
        chain.add(newBlock);
    }

    public List<Block> getChain() {
        return new ArrayList<>(chain); // Return a copy to prevent external modifications
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Verify hash
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            // Verify mining difficulty
            String target = "0".repeat(4);
            if (!currentBlock.getHash().substring(0, 4).equals(target)) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return chain.size();
    }
}