package com.nextstay.propertyservice.service;

import com.nextstay.propertyservice.entity.Property;
import com.nextstay.propertyservice.repository.PropertyRepository;
import com.nextstay.propertyservice.dto.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = Property.builder()
                .hostId(propertyDTO.getHostId())
                .title(propertyDTO.getTitle())
                .description(propertyDTO.getDescription())
                .address(propertyDTO.getAddress())
                .city(propertyDTO.getCity())
                .country(propertyDTO.getCountry())
                .zipCode(propertyDTO.getZipCode())
                .pricePerNight(propertyDTO.getPricePerNight())
                .maxGuests(propertyDTO.getMaxGuests())
                .bedrooms(propertyDTO.getBedrooms())
                .bathrooms(propertyDTO.getBathrooms())
                .propertyType(Property.PropertyType.valueOf(propertyDTO.getPropertyType().toUpperCase()))
                .available(true)
                .build();

        Property savedProperty = propertyRepository.save(property);
        return mapToDTO(savedProperty);
    }

    public PropertyDTO getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        return mapToDTO(property);
    }

    public List<PropertyDTO> getPropertiesByHostId(Long hostId) {
        return propertyRepository.findByHostId(hostId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PropertyDTO> getAvailableProperties() {
        return propertyRepository.findByAvailable(true)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PropertyDTO> getPropertiesByCity(String city) {
        return propertyRepository.findByCity(city)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setAddress(propertyDTO.getAddress());
        property.setCity(propertyDTO.getCity());
        property.setCountry(propertyDTO.getCountry());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setMaxGuests(propertyDTO.getMaxGuests());
        property.setAvailable(propertyDTO.isAvailable());

        Property updatedProperty = propertyRepository.save(property);
        return mapToDTO(updatedProperty);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    private PropertyDTO mapToDTO(Property property) {
        return PropertyDTO.builder()
                .id(property.getId())
                .hostId(property.getHostId())
                .title(property.getTitle())
                .description(property.getDescription())
                .address(property.getAddress())
                .city(property.getCity())
                .country(property.getCountry())
                .zipCode(property.getZipCode())
                .pricePerNight(property.getPricePerNight())
                .maxGuests(property.getMaxGuests())
                .bedrooms(property.getBedrooms())
                .bathrooms(property.getBathrooms())
                .propertyType(property.getPropertyType().toString())
                .available(property.isAvailable())
                .build();
    }
}
