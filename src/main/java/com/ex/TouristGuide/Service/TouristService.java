package com.ex.TouristGuide.Service;

import com.ex.TouristGuide.Model.TouristAttraction;
import com.ex.TouristGuide.Repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    @Autowired
    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    // Get all tourist attractions
    public List<TouristAttraction> get() {
        return touristRepository.findAll();
    }

    // Find an attraction by its name
    public TouristAttraction findByName(String name) {
        return touristRepository.findByName(name);
    }

    // Create a new attraction
    public void createAttraction(TouristAttraction attraction) {
        touristRepository.save(attraction);
    }

    // Edit an existing attraction
    public void editAttraction(TouristAttraction attraction) {
        touristRepository.update(attraction);
    }

    // Delete an attraction by object
    public void deleteAttraction(TouristAttraction attraction) {
        touristRepository.delete(attraction.getName());
    }
}
