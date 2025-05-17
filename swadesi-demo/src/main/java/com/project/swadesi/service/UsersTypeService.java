package com.project.swadesi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.swadesi.entity.UsersType;
import com.project.swadesi.repository.UsersTypeRepository;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll() {
        return usersTypeRepository.findAll();
    }
}