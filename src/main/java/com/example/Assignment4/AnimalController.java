package com.example.Assignment4;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    // return a list of all animals
    @GetMapping("/all")
    public String getAllAnimals(Model model) {
        List<Animal> animals = animalService.getAllAnimals();
        System.out.println("Animals found: " + animals.size());
        model.addAttribute("animals", animals);
        return "animal-list";
    }

    // get a specific animal by its id
    @GetMapping("/animal-details")
    public Optional<Animal> getAnimalById(@PathVariable int animalId) {
        return animalService.getAnimalById(animalId);
    }

    // display the create form
    @GetMapping("/add")
    public String showCreateAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "animal-create";
    }

    // create new animal
    @PostMapping("/add")
    public String addAnimal(@ModelAttribute Animal animal) {
        animalService.saveAnimal(animal);
        return "redirect:/animals/all";
    }

    // display the update form
    @GetMapping("/update/{id}")
    public String showUpdateAnimalForm(@PathVariable int id, Model model) {
        Animal animal = animalService.getAnimalById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid animal ID: " + id));
        model.addAttribute("animal", animal);
        return "animal-update";
    }

    // updates an existing animal inside the database
    @PostMapping("/update/{id}")
    public String updateAnimal(@PathVariable int id, @ModelAttribute Animal animal) {
        animalService.updateAnimal(id, animal);
        return "redirect:/animals/all";
    }

    // deletes an existing animal from the database
    // @DeleteMapping("/{animalId}")
    @GetMapping("/delete/{id}")
    public String deleteAnimal(@PathVariable int id) {
        animalService.deleteAnimal(id);
        return "redirect:/animals/all";
    }

    // display details for a specific animal
    @GetMapping("/details/{id}")
    public String showAnimalDetails(@PathVariable int id, Model model) {
        Optional<Animal> animal = animalService.getAnimalById(id);
        if (animal.isPresent()) {
            model.addAttribute("animal", animal.get());
        } else {
            return "redirect:/animals/all";
        }
        return "animal-details";
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
