package com.example.Assignment4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findByAnimalClass(String animalClass);

    List<Animal> findByNameContainingIgnoreCase(String keyword);

}
