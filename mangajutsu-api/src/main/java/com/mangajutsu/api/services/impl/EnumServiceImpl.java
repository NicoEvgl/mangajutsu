package com.mangajutsu.api.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.mangajutsu.api.enums.FileTypesEnum;
import com.mangajutsu.api.enums.GenresEnum;
import com.mangajutsu.api.enums.MovieTypesEnum;
import com.mangajutsu.api.enums.StatusEnum;
import com.mangajutsu.api.enums.TypesEnum;
import com.mangajutsu.api.services.EnumService;

import org.springframework.stereotype.Service;

@Service("enumService")
public class EnumServiceImpl implements EnumService {

    @Override
    public List<String> getTypesEnumStringValues() {
        TypesEnum[] types = TypesEnum.values();
        List<String> typesStringValues = new ArrayList<>();

        for (TypesEnum type : types) {
            typesStringValues.add(type.getParam());
        }
        return typesStringValues;
    }

    @Override
    public List<String> getStatusEnumStringValues() {
        StatusEnum[] statusList = StatusEnum.values();
        List<String> statusStringValues = new ArrayList<>();

        for (StatusEnum status : statusList) {
            statusStringValues.add(status.getParam());
        }
        return statusStringValues;
    }

    @Override
    public List<String> getGenresEnumStringValues() {
        GenresEnum[] genres = GenresEnum.values();
        List<String> genresStringValues = new ArrayList<>();

        for (GenresEnum genre : genres) {
            genresStringValues.add(genre.getParam());
        }
        return genresStringValues;
    }

    @Override
    public List<String> getFileTypesEnumStringValues() {
        FileTypesEnum[] fileTypes = FileTypesEnum.values();
        List<String> fileTypesStringValues = new ArrayList<>();

        for (FileTypesEnum fileType : fileTypes) {
            fileTypesStringValues.add(fileType.getParam());
        }
        return fileTypesStringValues;
    }

    @Override
    public List<String> getMovieTypesEnumStringValues() {
        MovieTypesEnum[] movieTypes = MovieTypesEnum.values();
        List<String> movieTypesStringValues = new ArrayList<>();

        for (MovieTypesEnum movieType : movieTypes) {
            movieTypesStringValues.add(movieType.getParam());
        }
        return movieTypesStringValues;
    }
}
