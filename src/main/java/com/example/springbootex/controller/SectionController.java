package com.example.springbootex.controller;

import com.example.springbootex.entity.Section;
import com.example.springbootex.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Section> get(@PathVariable int id) {
        return new ResponseEntity<>(sectionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Section>> getAll() {
        return new ResponseEntity<>(sectionService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSection(@RequestParam int storeId,
                                        @RequestBody Section section) {
        sectionService.add(section, storeId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSection(@PathVariable int id,
                                           @RequestBody Section section) {
        sectionService.update(id, section);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteSection(@PathVariable int id) {
        sectionService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
