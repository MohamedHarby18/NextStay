package com.nextstay.propertyservice.controller;

import com.nextstay.propertyservice.service.PropertyService;
import com.nextstay.propertyservice.dto.PropertyDTO;
import com.nextstay.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<ApiResponse<PropertyDTO>> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO property = propertyService.createProperty(propertyDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PropertyDTO>builder()
                        .statusCode(201)
                        .message("Property created successfully")
                        .data(property)
                        .success(true)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyDTO>> getPropertyById(@PathVariable Long id) {
        PropertyDTO property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(ApiResponse.<PropertyDTO>builder()
                .statusCode(200)
                .message("Property retrieved successfully")
                .data(property)
                .success(true)
                .build());
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getPropertiesByHostId(@PathVariable Long hostId) {
        List<PropertyDTO> properties = propertyService.getPropertiesByHostId(hostId);
        return ResponseEntity.ok(ApiResponse.<List<PropertyDTO>>builder()
                .statusCode(200)
                .message("Properties retrieved successfully")
                .data(properties)
                .success(true)
                .build());
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getAvailableProperties() {
        List<PropertyDTO> properties = propertyService.getAvailableProperties();
        return ResponseEntity.ok(ApiResponse.<List<PropertyDTO>>builder()
                .statusCode(200)
                .message("Available properties retrieved successfully")
                .data(properties)
                .success(true)
                .build());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ApiResponse<List<PropertyDTO>>> getPropertiesByCity(@PathVariable String city) {
        List<PropertyDTO> properties = propertyService.getPropertiesByCity(city);
        return ResponseEntity.ok(ApiResponse.<List<PropertyDTO>>builder()
                .statusCode(200)
                .message("Properties retrieved successfully")
                .data(properties)
                .success(true)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PropertyDTO>> updateProperty(@PathVariable Long id,
            @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO property = propertyService.updateProperty(id, propertyDTO);
        return ResponseEntity.ok(ApiResponse.<PropertyDTO>builder()
                .statusCode(200)
                .message("Property updated successfully")
                .data(property)
                .success(true)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .statusCode(200)
                .message("Property deleted successfully")
                .success(true)
                .build());
    }
}
