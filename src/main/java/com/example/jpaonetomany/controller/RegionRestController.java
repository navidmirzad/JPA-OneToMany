package com.example.jpaonetomany.controller;

import com.example.jpaonetomany.Model.Kommune;
import com.example.jpaonetomany.Model.Region;
import com.example.jpaonetomany.repositories.RegionRepository;
import com.example.jpaonetomany.service.ApiServiceGetKommuner;
import com.example.jpaonetomany.service.ApiServiceGetRegioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
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

    // add PostMapping for Regioner

    // add PutMapping for Regioner

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
