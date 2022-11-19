package com.mss.blood.donation.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodDonationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date bloodDonationDate;

    private boolean isDonate;

    @ManyToOne
    @JoinColumn(name = "blood_donor_id_fk", referencedColumnName = "id")
    private BloodDonor bloodDonor;

    @ManyToOne
    @JoinColumn(name = "blood_recipient", referencedColumnName = "id")
    private BloodRecipient bloodRecipient;

}
