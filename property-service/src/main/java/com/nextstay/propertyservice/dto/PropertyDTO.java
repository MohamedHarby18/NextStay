package com.nextstay.propertyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String description;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private BigDecimal pricePerNight;
    private int maxGuests;
    private int bedrooms;
    private int bathrooms;
    private String propertyType;
    private boolean available;
}
