package com.SpringBot.service.impl;

import com.SpringBot.DTO.ProductDto;
import com.SpringBot.DTO.SelectionDto;
import com.SpringBot.model.entity.Product;
import com.SpringBot.model.entity.Selection;
import com.SpringBot.model.repository.SelectionRepository;
import com.SpringBot.service.SelectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SelectionServiceImpl implements SelectionService {
    private final SelectionRepository selectionRepository;

    @Override
    public List<SelectionDto> findAll() {
        List<Selection> selections = selectionRepository.findAll();
        List<SelectionDto> selectionDtoList = transfer(selections);
        return selectionDtoList;
    }

    private void mapper(Selection selection, SelectionDto selectionDto) {
        selectionDto.setId(selection.getId());
        selectionDto.setName(selection.getName());
        selectionDto.setDescription(selection.getDescription());
    }

    private List<SelectionDto> transfer(List<Selection> selections) {
        List<SelectionDto> selectionDtoList = new ArrayList<>();
        for (Selection selection : selections) {
            SelectionDto selectionDto = new SelectionDto();
            mapper(selection, selectionDto);
            selectionDtoList.add(selectionDto);
        }
        return selectionDtoList;
    }
}
