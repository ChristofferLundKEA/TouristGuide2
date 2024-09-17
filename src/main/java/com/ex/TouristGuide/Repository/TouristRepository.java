package com.ex.TouristGuide.Repository;

import com.ex.TouristGuide.Model.Tag;
import com.ex.TouristGuide.Model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private List<TouristAttraction> touristAttractions = new ArrayList<>();

    public TouristRepository(){
        populateAttractions();
    }

    private void populateAttractions(){
        touristAttractions.add(new TouristAttraction(1, "Tivoli", "Forlystelsespark",
                "Copenhagen K", List.of(Tag.ENTERTAINMENT, Tag.ROLLER_COASTER, Tag.CANDY)));

        touristAttractions.add(new TouristAttraction(2, "Københavns ZOO", "Zoologisk have",
                "Frederiksberg", List.of(Tag.ANIMALS, Tag.PLAYGROUND, Tag.ENTERTAINMENT)));

        touristAttractions.add(new TouristAttraction(3, "Nationalmuseet", "Danmarks statslige, kulturhistoriske hovedmuseum",
                "Copenhagen K", List.of(Tag.MUSEUM, Tag.ANCIENT_TIMES, Tag.PAINTINGS, Tag.CAFE, Tag.WEDDINGS)));
    }

    public List<TouristAttraction> getTouristAttractions() {
        return touristAttractions;
    }
}
