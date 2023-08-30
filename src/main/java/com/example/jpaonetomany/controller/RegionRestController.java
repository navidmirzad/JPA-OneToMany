package com.example.jpaonetomany.controller;

import com.example.jpaonetomany.Model.Region;
import com.example.jpaonetomany.service.ApiServiceGetRegioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionRestController {

    @Autowired
    ApiServiceGetRegioner apiServiceGetRegioner;

    @GetMapping("/getregioner")
    public List<Region> getRegioner() {
        List<Region> ListRegioner = apiServiceGetRegioner.getRegion();
        return ListRegioner;
    }


}
