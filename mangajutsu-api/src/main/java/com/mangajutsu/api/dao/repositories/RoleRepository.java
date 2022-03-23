package com.mangajutsu.api.dao.repositories;

import com.mangajutsu.api.dao.entities.RoleEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Integer> {
    RoleEntity findByCode(String code);

    RoleEntity findByNameRole(String nameRole);
}
