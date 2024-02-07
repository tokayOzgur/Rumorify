package com.rumorify.ws.service;

import org.modelmapper.ModelMapper;

/**
 * @author tokay
*/
public interface ModelMapperService {
    ModelMapper forResponse();

    ModelMapper forRequest();
}
