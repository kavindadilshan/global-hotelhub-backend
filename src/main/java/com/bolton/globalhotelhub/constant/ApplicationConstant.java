package com.bolton.globalhotelhub.constant;

public class ApplicationConstant {

    /**
     * API Response
     */
    public static final String API_BASE_URL = "/v1";

    public static final String APPLICATION_ERROR_OCCURRED_MESSAGE = "Unexpected Error Occurred";

    /**
     * URL Pattern
     */

    public static final String BOOKING_COM_URL = "https://www.booking.com/searchresults.en-gb.html?ss=%s&checkin=%s&checkout=%s&group_adults=%s&no_rooms=%s&group_children=%s&nflt=price=LKR-%s-%s-1";

    public static final String HOTEL_COM_URL = "https://www.hotels.com/Hotel-Search?adults=%s&children=%s&d1=%s&d2=%s&destination=%s&rooms=%s&price=%s&price=%s";

    public static final String AIR_BNB_URL = "https://www.airbnb.com/s/%s/homes?adults=%s&children=%s&checkin=%s&checkout=%s&min_bedrooms=%s&price_min=%s&price_max=%s";
}
