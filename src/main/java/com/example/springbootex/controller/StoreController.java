package com.example.springbootex.controller;

import com.example.springbootex.entity.Store;
import com.example.springbootex.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Store> get(@PathVariable int id) {
        return new ResponseEntity<>(storeService.get(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Store>> getAll() {
        return new ResponseEntity<>(storeService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStore(@RequestBody Store store) {
        storeService.add(store);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStore(@PathVariable int id,
                                         @RequestBody Store store) {
        storeService.update(id, store);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable int id) {
        storeService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
