package com.mss.blood.donation.dto;

import com.mss.blood.donation.entities.BloodDonor;
import com.mss.blood.donation.entities.BloodRecipient;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodDonationHistoryDTO {

    private Long id;

    private Date bloodDonationDate;

    private boolean isDonate;

    private BloodDonor bloodDonor;

    private BloodRecipient bloodRecipient;

}
