package com.mangajutsu.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface EnumService {
    List<String> getTypesEnumStringValues();
}