package com.ex.TouristGuide.Controller;

import com.ex.TouristGuide.Model.TouristAttraction;
import com.ex.TouristGuide.Service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@WebMvcTest(TouristController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    @BeforeEach
    void setUp() {
        // Opsætning af MockMvc
    }

    @Test
    void testTouristRedirection() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));
    }
    @Test
    void testShowAttractions() throws Exception {
        // opretter en dummy liste af turistattraktioner
        List<TouristAttraction> dummyAttractions = new ArrayList<>();
        TouristAttraction attraction = new TouristAttraction();
        attraction.setName("Eiffel Tower");
        dummyAttractions.add(attraction);

        // faker kaldet til touristService.get() og returner dummy listen
        when(touristService.get()).thenReturn(dummyAttractions);

        // Tester request'en til "/attractions"
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"))
                .andExpect(model().attributeExists("attractions"));
    }
}
