package com.SpringBot.service;

import com.SpringBot.DTO.SelectionDto;

import java.util.List;

public interface SelectionService {
    List<SelectionDto> findAll();
}
