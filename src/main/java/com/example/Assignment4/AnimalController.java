package com.example.Assignment4;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // return a list of all animals
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    // get a specific animal by its id
    @GetMapping("/{animalId}")
    public Optional<Animal> getAnimalById(@PathVariable int animalId) {
        return animalService.getAnimalById(animalId);
    }

    // adds a new animal to the database and saves it
    @PostMapping
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {
        Animal savedAnimal = animalService.addAnimal(animal);
        return new ResponseEntity<>(savedAnimal, HttpStatus.CREATED);
    }

    // updates an existing animal inside the database
    @PutMapping("/{animalId}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable int animalId, @RequestBody Animal animalDetails) {
        Animal updatedAnimal = animalService.updateAnimal(animalId, animalDetails);
        return new ResponseEntity<>(updatedAnimal, HttpStatus.OK);
    }

    // deletes an existing animal from the database
    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable int animalId) {
        animalService.deleteAnimal(animalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // gets all animals by a certain class or species
    @GetMapping("/class/{animalClass}")
    public ResponseEntity<List<Animal>> getAnimalsByClass(@PathVariable String animalClass) {
        List<Animal> animals = animalService.getAnimalsByClass(animalClass);
        if (animals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }

    // searches the database for a keyword
    @GetMapping("/search")
    public ResponseEntity<List<Animal>> searchAnimals(@RequestParam String keyword) {
        List<Animal> animals = animalService.searchAnimalsByName(keyword);
        if (animals.isEmpty()) {
            return new ResponseEntity<>(animals, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }
}
