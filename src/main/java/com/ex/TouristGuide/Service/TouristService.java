package com.ex.TouristGuide.Service;

import com.ex.TouristGuide.Model.TouristAttraction;
import com.ex.TouristGuide.Repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {

    private final TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public void createAttraction(TouristAttraction attraction) {
        if (findByName(attraction.getName()) == null) {
            touristRepository.getTouristAttractions().add(attraction); // Add the new attraction to repo
        } else {
            throw new IllegalStateException("Attraction with name '" + attraction.getName() + "' already exists.");
        }
    }

    public void editAttraction(TouristAttraction updatedAttraction) {
        List<TouristAttraction> attractions = touristRepository.getTouristAttractions();

        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equalsIgnoreCase(updatedAttraction.getName())) {
                attraction.setDescription(updatedAttraction.getDescription());
                attraction.setCity(updatedAttraction.getCity());
                attraction.setTags(updatedAttraction.getTags());
                return;
            }
        }
        throw new IllegalStateException("Attraction with name '" + updatedAttraction.getName() + "' not found.");
    }

    public TouristAttraction findByName(String name) { // REMOVE/REFACTOR
        List<TouristAttraction> attractions = touristRepository.getTouristAttractions();
        for (TouristAttraction attraction : attractions) {
            if (attraction.getName().equals(name)) {
                return attraction;
            }
        }
        return null;
    }

    public List<TouristAttraction> get() {
        return touristRepository.getTouristAttractions();
    }

    public void deleteAttraction(TouristAttraction touristAttraction) {

        touristRepository.getTouristAttractions().removeIf(  // removeIf() handles iteration, so that we can remove elements without looping.
                attraction -> attraction.getName().equalsIgnoreCase(touristAttraction.getName())
        );

//        List<TouristAttraction> tempAttractions = touristRepository.getTouristAttractions();
//
//        for (TouristAttraction attraction : touristRepository.getTouristAttractions()){
//
//            if (attraction.getName().equalsIgnoreCase(touristAttraction.getName())) {
//                tempAttractions.remove(attraction);
//                touristRepository.setTouristAttractions(tempAttractions);
//            }
//        }

    }
}
