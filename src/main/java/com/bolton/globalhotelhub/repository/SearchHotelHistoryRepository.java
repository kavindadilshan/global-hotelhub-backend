package com.bolton.globalhotelhub.repository;

import com.bolton.globalhotelhub.entity.SearchHotelHistory;
import com.bolton.globalhotelhub.rawdata.HotelReportRawData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHotelHistoryRepository extends JpaRepository<SearchHotelHistory,Long> {

}
