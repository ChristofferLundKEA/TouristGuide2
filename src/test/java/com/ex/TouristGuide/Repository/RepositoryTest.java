package com.ex.TouristGuide.Repository;

import com.ex.TouristGuide.Model.TouristAttraction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class RepositoryTest {

    @Autowired
    private TouristRepository touristRepository;

    @Test
    void testFindAll() {
        List<TouristAttraction> attractions = touristRepository.findAll();
        assertNotNull(attractions);
        assertTrue(attractions.size() > 0, "Should return some attractions from the database");
    }
    @Test
    void testFindByName() {
       // Nationalmuseet eksisterer allerede som attraktion
        String name = "Nationalmuseet";

        // Kalder metoden findByName
        TouristAttraction attraction = touristRepository.findByName(name);


        assertNotNull(attraction, "Attraction should not be null");
        assertEquals(name, attraction.getName(), "The name of the attraction should match");
    }

}

