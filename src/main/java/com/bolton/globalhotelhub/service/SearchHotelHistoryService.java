package com.bolton.globalhotelhub.service;

import com.bolton.globalhotelhub.dto.response.HotelReportResponseDTO;
import com.bolton.globalhotelhub.dto.response.SearchHotelHistoryResponseDTO;
import com.bolton.globalhotelhub.dto.response.UserReportResponseDTO;

import java.util.List;

public interface SearchHotelHistoryService {
    List<SearchHotelHistoryResponseDTO> getSearchHotelHistoryByUser (Long userId);
    List<SearchHotelHistoryResponseDTO> getAllSearchHotelHistory();

    List<HotelReportResponseDTO> getHotelReport();

    List<HotelReportResponseDTO> getTopRatedHotels();

    List<UserReportResponseDTO> getUserReport();
}
