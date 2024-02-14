package com.bolton.globalhotelhub.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchHotelHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String location;
    private String checkin;
    private String checkout;
    private Number rooms;
    private Number adults;
    private Number child;
    private Double maxPrice;
    private Double minPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Users users;
}
