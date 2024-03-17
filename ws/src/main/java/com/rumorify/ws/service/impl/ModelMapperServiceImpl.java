package com.rumorify.ws.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.rumorify.ws.service.ModelMapperService;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tokay
 */
@Service
@AllArgsConstructor
@Data
public class ModelMapperServiceImpl implements ModelMapperService {
    private final ModelMapper modelMapper;

    @Override
    public ModelMapper forResponse() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }

}
