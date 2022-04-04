package com.mangajutsu.api.services.impl;

import java.util.ArrayList;
import java.util.List;

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
}
