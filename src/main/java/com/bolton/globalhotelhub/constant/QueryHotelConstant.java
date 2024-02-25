package com.bolton.globalhotelhub.constant;

public class QueryHotelConstant {
    public static final String GET_HOTEL_REPORT = "select location,checkin,checkout,rooms,adults,child,maxPrice,minPrice,count(*) as count from searchhotelhistory group by location,checkin,checkout,rooms,adults,child,maxPrice,minPrice order by count(*) DESC";
    public static final String GET_HOTEL_STATISTIC_REPORT ="select location,checkin,checkout,rooms,adults,child,maxPrice,minPrice,count(*) as count from searchhotelhistory group by location";

    public static final String GET_USER_REPORT = "select location,checkin,checkout,rooms,adults,child,maxPrice,minPrice,count(s.users_id) as filtering_count from users LEFT JOIN searchhotelhistory  s ON (s.users_id = users.id) \n" +
            "group by s.users_id order by filtering_count DESC";
}
