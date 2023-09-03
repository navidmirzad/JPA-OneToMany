package com.example.jpaonetomany.repositories;

import com.example.jpaonetomany.Model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {

}
