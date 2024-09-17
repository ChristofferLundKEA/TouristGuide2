package com.ex.TouristGuide.Service;

import com.ex.TouristGuide.Model.TouristAttraction;
import com.ex.TouristGuide.Repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public void createAttraction(TouristAttraction attraction) {
        if (findByNameForNew(attraction.getName()) == null) {
            // Add the new attraction
            attraction.setOriginalName(attraction.getName());
            touristRepository.getTouristAttractions().add(attraction);

        } else {
            throw new IllegalStateException("Attraction with name '" + attraction.getName() + "' already exists.");
        }
    }

    // Edit an existing attraction (used in submitForm for editing)
    public void editAttraction(TouristAttraction updatedAttraction) {
        List<TouristAttraction> attractions = touristRepository.getTouristAttractions();

        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).getName().equalsIgnoreCase(updatedAttraction.getOriginalName())) {
                attractions.set(i, updatedAttraction);  // Update existing attraction
                return;
            }
        }
        throw new IllegalStateException("Attraction with name '" + updatedAttraction.getOriginalName() + "' not found.");
    }

    public TouristAttraction findByNameForEdit(String name) {
        List<TouristAttraction> attractions = touristRepository.getTouristAttractions();
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        throw new IllegalStateException("Attraction should always exist");
    }

    public TouristAttraction findByNameForNew(String name) {
        List<TouristAttraction> attractions = touristRepository.getTouristAttractions();
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                return attraction;
            }
        }
        return null;
    }

    public TouristAttraction findByName(String name) {
        return findByNameForEdit(name);  // Use the same logic as editing, for the tags view
    }


    public List<TouristAttraction> get() {
        return touristRepository.getTouristAttractions();
    }
}
