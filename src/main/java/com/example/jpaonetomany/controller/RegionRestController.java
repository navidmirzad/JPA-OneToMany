package com.example.jpaonetomany.controller;

import com.example.jpaonetomany.Model.Kommune;
import com.example.jpaonetomany.Model.Region;
import com.example.jpaonetomany.repositories.RegionRepository;
import com.example.jpaonetomany.service.ApiServiceGetKommuner;
import com.example.jpaonetomany.service.ApiServiceGetRegioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class RegionRestController {

    @Autowired
    ApiServiceGetRegioner apiServiceGetRegioner;

    @Autowired
    RegionRepository regionRepository;

    @GetMapping("/getregioner")
    public List<Region> getRegioner() {
        List<Region> listRegioner = apiServiceGetRegioner.getRegion();
        return listRegioner;
    }

    @GetMapping("/regionkommune/{regionKode}")
    public List<String> getKommunerForRegion(@PathVariable String regionKode) throws Exception {
        Region region = regionRepository.findById(regionKode)
                .orElseThrow(() -> new Exception("Region not found with code: " + regionKode));
        return region.getKommuneNavne();
    }

    @PostMapping("/region")
    public ResponseEntity<Region> postRegion(@RequestBody Region region) {
        Region savedRegion = regionRepository.save(region);
        if (savedRegion == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
        }
    }

    @PutMapping("/region/{kode}")
    public ResponseEntity<Region> putRegion(@PathVariable String kode, @RequestBody Region region) {
        Optional<Region> orgRegion = regionRepository.findById(kode);
        if (orgRegion.isPresent()) {
            region.setKode(kode);
            regionRepository.save(region);
            return ResponseEntity.ok(region);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteregion/{kode}")
    public ResponseEntity<String> deleteRegion(@PathVariable String kode) {
        try {
            regionRepository.deleteById(kode);
            System.out.println("Region was found and is deleted");
            return ResponseEntity.ok().build();

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Region not found");
        }
    }


}
