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
    private Number rooms;
    private Number adults;
    private Number child;
    private Number maxPrice;
    private Number minPrice;

}
