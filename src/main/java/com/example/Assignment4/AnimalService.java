package com.example.Assignment4;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getAnimalById(int animalId) {
        return animalRepository.findById(animalId);
    }

    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(int animalId, Animal animalDetails) {
        Animal existingAnimal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal with ID: " + animalId + " does not exist."));

        existingAnimal.setName(animalDetails.getName());
        existingAnimal.setScientificName(animalDetails.getScientificName());
        existingAnimal.setAnimalClass(animalDetails.getAnimalClass());
        existingAnimal.setHabitat(animalDetails.getHabitat());
        existingAnimal.setDescription(animalDetails.getDescription());

        return animalRepository.save(existingAnimal);
    }

    public void deleteAnimal(int animalId) {
        Animal existingAnimal = animalRepository.findById(animalId)
                .orElseThrow(() -> new EntityNotFoundException("Animal with ID: " + animalId + " does not exist."));
        animalRepository.deleteById(animalId);
    }

    public List<Animal> getAnimalsByClass(String animalClass) {
        return animalRepository.findByAnimalClass(animalClass);
    }

    public List<Animal> searchAnimalsByName(String keyword) {
        return animalRepository.findByNameContainingIgnoreCase(keyword);
    }
}
