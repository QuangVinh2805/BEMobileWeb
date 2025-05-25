package com.example.mobile_store.controller;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ColorController {
    @Autowired
    ColorService colorService;


    @GetMapping("/color")
    public ResponseEntity<List<Color>> findAll() {
        return colorService.findAll();
    }

    @PostMapping("/color/create")
    public Color create(@RequestParam String name) {
        return colorService.add(name);
    }

    @PutMapping("/color/update")
    public Color edit(@RequestParam Long id, @RequestParam String name) {
        return colorService.update(id, name);
    }

    @DeleteMapping("/color/delete")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        colorService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
