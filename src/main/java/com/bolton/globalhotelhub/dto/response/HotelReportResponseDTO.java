package com.bolton.globalhotelhub.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelReportResponseDTO {
    private String location;
    private String checkin;
    private String checkout;
    private Number rooms;
    private Number adults;
    private Number child;
    private Double maxPrice;
    private Double minPrice;
}
