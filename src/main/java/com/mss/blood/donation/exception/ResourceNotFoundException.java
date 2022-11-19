package com.mss.blood.donation.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;

    private String resourceFieldName;

    private Long resourceFieldValue;

    public ResourceNotFoundException(String resourceName, String resourceFieldName, Long resourceFieldValue) {
        super(String.format("%s is not found %s: %d", resourceName, resourceFieldName, resourceFieldValue));
    }

}
