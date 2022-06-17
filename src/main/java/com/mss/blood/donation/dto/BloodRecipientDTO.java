package com.mss.blood.donation.dto;

import com.mss.blood.donation.entities.BloodDonationHistory;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodRecipientDTO {

    private Long id;

    private String name;

    private String contact;

    private String email;

    private String bloodGroupName;

    private String address;

    private List<BloodDonationHistory> bloodDonationHistories;

}
