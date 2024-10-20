package com.blockchain.blockchain_demo.controller;

import com.blockchain.blockchain_demo.model.Block;
import com.blockchain.blockchain_demo.model.Blockchain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blockchain")
public class BlockController {
    private final Blockchain blockchain;

    public BlockController() {
        this.blockchain = new Blockchain();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBlock(@RequestParam String data) {
        try {
            blockchain.addBlock(data);
            return ResponseEntity.ok(Map.of(
                    "message", "Block added successfully",
                    "chainSize", blockchain.getSize(),
                    "latestBlock", blockchain.getLatestBlock()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/chain")
    public ResponseEntity<List<Block>> getBlockchain() {
        return ResponseEntity.ok(blockchain.getChain());
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateChain() {
        boolean isValid = blockchain.isChainValid();
        return ResponseEntity.ok(Map.of(
                "isValid", isValid,
                "chainSize", blockchain.getSize()
        ));
    }

    @GetMapping("/latest")
    public ResponseEntity<Block> getLatestBlock() {
        return ResponseEntity.ok(blockchain.getLatestBlock());
    }
}