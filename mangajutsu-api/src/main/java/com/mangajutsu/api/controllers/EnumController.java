package com.mangajutsu.api.controllers;

import java.util.List;

import com.mangajutsu.api.services.EnumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enum")
public class EnumController {

    @Autowired
    EnumService enumService;

    @GetMapping("/types")
    public List<String> getTypes() {
        return enumService.getTypesEnumStringValues();
    }
}
