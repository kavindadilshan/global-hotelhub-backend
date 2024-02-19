package com.bolton.globalhotelhub.controller;

import com.bolton.globalhotelhub.dto.response.CommonDataResponseDTO;
import com.bolton.globalhotelhub.dto.response.SearchHotelHistoryResponseDTO;
import com.bolton.globalhotelhub.service.SearchHotelHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/v1/hotels/history")
public class SearchHotelHistoryController {

    @Autowired
    private SearchHotelHistoryService searchHotelHistoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSearchHotelHistoryByUser(@RequestParam("userId") Long userId) {
        List<SearchHotelHistoryResponseDTO> responseDTOS = searchHotelHistoryService.getSearchHotelHistoryByUser(userId);
        return new ResponseEntity<>(new CommonDataResponseDTO(true, responseDTOS), HttpStatus.OK);
    }

    @GetMapping(value = "all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSearchHotelHistory(){
        List<SearchHotelHistoryResponseDTO> responseDTOS = searchHotelHistoryService.getAllSearchHotelHistory();
        return new ResponseEntity<>(new CommonDataResponseDTO(true, responseDTOS), HttpStatus.OK);
    }

}
