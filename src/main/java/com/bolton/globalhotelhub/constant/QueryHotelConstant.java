package com.bolton.globalhotelhub.constant;

public class QueryHotelConstant {
    public static final String GET_HOTEL_REPORT = "select location,checkin,checkout,rooms,adults,child,maxPrice,minPrice,count(*) as count from search_hotel_history group by location,checkin,checkout,rooms,adults,child,maxPrice,minPrice order by count(*) DESC";
}
