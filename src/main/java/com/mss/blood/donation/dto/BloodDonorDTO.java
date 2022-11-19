package com.mss.blood.donation.dto;

import com.mss.blood.donation.entities.BloodDonationHistory;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodDonorDTO {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 50, message = "blood donor's name must be min of 3 to 50 character")
    private String name;

    @Min(value = 18, message = "blood donor's age must be minimum 18 years")
    @Max(value = 50, message = "blood donor's age must be maximum 50 years")
    private Integer age;

    @NotEmpty
    private String contact;

    @NotEmpty
    private String email;

    @NotEmpty
    private String bloodGroupName;

    @NotEmpty
    @Size(min = 3, max = 100, message = "blood donor's address must be min of 3 to 100 character")
    private String address;

    private String image;

    @NotEmpty
    private String password;

    private String verificationCode;

    private String role;

    private boolean isAvailable;

    private Date lastBloodDonationDate;

    private List<BloodDonationHistory> bloodDonationHistories;

}
