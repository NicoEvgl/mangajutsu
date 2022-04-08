package com.mangajutsu.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface EnumService {
    List<String> getTypesEnumStringValues();

    List<String> getStatusEnumStringValues();

    List<String> getGenresEnumStringValues();

    List<String> getFileTypesEnumStringValues();
}
