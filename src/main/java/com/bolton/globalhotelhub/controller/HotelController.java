package com.bolton.globalhotelhub.controller;

import com.bolton.globalhotelhub.dto.ResponseDTO;
import com.bolton.globalhotelhub.dto.request.FilterHotelRequestDTO;
import com.bolton.globalhotelhub.dto.response.CommonDataResponseDTO;
import com.bolton.globalhotelhub.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping(value = "/filter",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser (@RequestBody FilterHotelRequestDTO filterHotelRequestDTO){
        List<ResponseDTO> responseDTOS = hotelService.getHotelByModel(filterHotelRequestDTO);
        return new ResponseEntity<>(new CommonDataResponseDTO(true, responseDTOS),
                HttpStatus.OK);
    }
}
