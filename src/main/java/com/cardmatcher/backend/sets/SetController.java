package com.cardmatcher.backend.sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping("/load-sets")
    public String loadSets() {
        setService.loadSetsFromAPI();
        return "Sets loaded!";
    }
}
