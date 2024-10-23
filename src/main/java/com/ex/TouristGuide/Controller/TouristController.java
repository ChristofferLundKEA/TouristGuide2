package com.ex.TouristGuide.Controller;

import com.ex.TouristGuide.Model.City;
import com.ex.TouristGuide.Model.Tag;
import com.ex.TouristGuide.Model.TouristAttraction;
import com.ex.TouristGuide.Service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    // Redirect to the list of attractions
    @GetMapping("/")
    public String tourist() {
        return "redirect:/attractions";
    }

    // Display the list of all attractions
    @GetMapping("/attractions")
    public String showAttractions(Model model){
        List<TouristAttraction> attractions = touristService.get();
        model.addAttribute("attractions", attractions);
        return "attractionList";
    }

    // Display the tags of a specific attraction
    @GetMapping("/{name}/tags")
    public String showTags(@PathVariable String name, Model model){
        TouristAttraction attraction = touristService.findByName(name);
        model.addAttribute("attractionName", attraction.getName());
        model.addAttribute("attraction", attraction.getTags());
        return "tags";
    }

    // Show form to add a new attraction
    @GetMapping("/add")
    public String myForm(Model model) {
        TouristAttraction touristAttraction = new TouristAttraction();
        model.addAttribute("myModel", touristAttraction);
        model.addAttribute("cityEnum", City.values());
        model.addAttribute("tagsEnum", Tag.values());
        return "addAttraction";
    }

    // Show form to edit an existing attraction
    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.findByName(name);
        model.addAttribute("myModel", attraction);
        model.addAttribute("cityEnum", City.values());
        model.addAttribute("tagsEnum", Tag.values());
        return "editAttraction";
    }

    // Save new or updated attraction
    @PostMapping("/save")
    public String submitForm(@ModelAttribute("myModel") TouristAttraction touristAttraction) {
        if (touristService.findByName(touristAttraction.getName()) == null) {
            touristService.createAttraction(touristAttraction); // New attraction
        } else {
            touristService.editAttraction(touristAttraction);  // Existing attraction
        }
        return "redirect:/attractions";
    }

    // Delete an attraction by name
    @GetMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable String name) {
        TouristAttraction attraction = touristService.findByName(name);
        if (attraction != null) {
            touristService.deleteAttraction(attraction);
        }
        return "redirect:/attractions";
    }
}
