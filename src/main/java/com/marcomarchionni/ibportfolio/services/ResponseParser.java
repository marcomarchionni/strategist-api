package com.marcomarchionni.ibportfolio.services;

import com.marcomarchionni.ibportfolio.models.dtos.FlexQueryResponseDto;

import java.util.List;


public interface ResponseParser<T> {

    List<T> parse (FlexQueryResponseDto dto, String type);

}
