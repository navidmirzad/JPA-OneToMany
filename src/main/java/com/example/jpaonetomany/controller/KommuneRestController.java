package com.example.jpaonetomany.controller;

import com.example.jpaonetomany.Model.Kommune;
import com.example.jpaonetomany.Model.Region;
import com.example.jpaonetomany.exception.ResourceNotFoundException;
import com.example.jpaonetomany.repositories.KommuneRepository;
import com.example.jpaonetomany.service.ApiServiceGetKommuner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class KommuneRestController {

    @Autowired
    ApiServiceGetKommuner apiServiceGetKommuner;

    @Autowired
    KommuneRepository kommuneRepository;

    @GetMapping("/getkommuner")
    public List<Kommune> getKommuner() {
        return apiServiceGetKommuner.getKommuner();
    }

    @GetMapping("/kommuner")
    public List<Kommune> getAll() {
        return kommuneRepository.findAll();
    }

    // hard coded, men det virker ikke - så nu koder vi den responsive lige under
    @GetMapping("kommunepage")
    public ResponseEntity<List<Kommune>> getPageOfKommuner() {
        int page = 4;
        int size = 5;
        PageRequest kommunePage = PageRequest.of(page, size);
        Pageable paging = PageRequest.of(page, size);
        Page<Kommune> pageKommune = kommuneRepository.findAll(paging);
        List<Kommune> listOfKommuner = pageKommune.getContent();
        return new ResponseEntity<>(listOfKommuner, HttpStatus.OK);
    }

    @GetMapping("kommunepagearm")
    public ResponseEntity<Map<String, Object>> getPageOfKommuner(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Kommune> pageKommune = kommuneRepository.findAll(pageable);
        List<Kommune> kommuner = pageKommune.getContent();

        if (kommuner.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            Map<String, Object> response = new HashMap<>();
            response.put("kommuner", kommuner);
            response.put("CurrentPage", pageKommune.getNumber());
            response.put("totalItems", pageKommune.getTotalElements());
            response.put("totalPages", pageKommune.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("kommunenavn/{navn}")
    public ResponseEntity<Kommune> getKommuneByName(@PathVariable String navn) {
        Kommune kommune = kommuneRepository.findKommuneByNavn(navn).orElseThrow(() ->
                new ResourceNotFoundException("Kommune ikke fundet med navn = " + navn));
        return new ResponseEntity<>(kommune, HttpStatus.OK);
    }

    @PostMapping("/kommune")
    public ResponseEntity<Kommune> postKommune(@RequestBody Kommune kommune) {
        Kommune savedKommune = kommuneRepository.save(kommune);
        if (savedKommune == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(savedKommune, HttpStatus.CREATED);
        }
    }

    @PutMapping("/kommune/{kode}")
    public ResponseEntity<Kommune> putKommune(@PathVariable String kode, @RequestBody Kommune kommune) {
        Optional<Kommune> orgKommune = kommuneRepository.findById(kode);
        if (orgKommune.isPresent()) {
            kommune.setKode(kode);
            kommuneRepository.save(kommune);
            return ResponseEntity.ok(kommune);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // add PostMapping for Kommuner

    // add PutMapping for Kommuner

    // add DeleteMapping for kommuner
}
