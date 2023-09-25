package com.example.jpaonetomany.service;

import com.example.jpaonetomany.Model.Region;

import java.util.List;

public interface ApiServiceGetRegioner {
    List<Region> getRegion();
    List<String> getKommuneNavne(String regionKode);
}
