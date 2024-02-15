package com.bolton.globalhotelhub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterHotelRequestDTO {

    private Long userId;
    private String location;
    private String checkin;
    private String checkout;
    private String rooms;
    private String adults;
    private String child;
    private String maxPrice;
    private String minPrice;

}
