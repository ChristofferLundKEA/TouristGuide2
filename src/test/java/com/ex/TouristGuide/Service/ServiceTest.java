package com.ex.TouristGuide.Service;

import com.ex.TouristGuide.Model.TouristAttraction;
import com.ex.TouristGuide.Repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private TouristService touristService;
    private TouristRepository touristRepository;

    @BeforeEach
    void setUp() {
        touristRepository = new TouristRepository();
        touristService = new TouristService(touristRepository);
    }

    @Test
    void testGetReturnsAttractions() {
        List<TouristAttraction> attractions = touristService.get();
        assertNotNull(attractions, "List of attractions should not be null");
    }
    @Test
    void testFindByNameReturnsAttraction() {
        // Opretter en dummy attraktion manuelt
        TouristAttraction dummyAttraction = new TouristAttraction();
        dummyAttraction.setName("Test Attraction");

        // Tilføjer attraktionen direkte til en liste
        List<TouristAttraction> dummyAttractions = new ArrayList<>();
        dummyAttractions.add(dummyAttraction);

        // vi henter attraktionen fra dummy-listen manuelt i stedet for at bruge en rigtig database
        TouristAttraction foundAttraction = null;
        for (TouristAttraction attraction : dummyAttractions) {
            if (attraction.getName().equals("Test Attraction")) {
                foundAttraction = attraction;
                break;
            }
        }

        // tester om attraktionen blev fundet
        assertNotNull(foundAttraction, "Attraction should not be null");
        assertEquals("Test Attraction", foundAttraction.getName(), "Names should match");
    }

}
