package com.bolton.globalhotelhub.service;

import com.bolton.globalhotelhub.dto.ResponseDTO;
import com.bolton.globalhotelhub.dto.request.FilterHotelRequestDTO;

import java.util.List;

public interface HotelService {
    List<ResponseDTO> getHotelByModel(FilterHotelRequestDTO filterHotelRequestDTO);
}
