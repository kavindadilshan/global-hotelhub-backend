package com.bolton.globalhotelhub.repository;

import com.bolton.globalhotelhub.constant.QueryHotelConstant;
import com.bolton.globalhotelhub.entity.SearchHotelHistory;
import com.bolton.globalhotelhub.rawdata.HotelReportRawData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchHotelHistoryRepository extends JpaRepository<SearchHotelHistory, Long> {

    List<SearchHotelHistory> getSearchHotelHistoriesByUsersId(Long userId);

    @Query(value = QueryHotelConstant.GET_HOTEL_REPORT, nativeQuery = true)
    List<HotelReportRawData> getMostVisitedHotelReport();

}
