package com.mss.blood.donation.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BloodDonor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String contact;

    private String email;

    private String bloodGroupName;

    private String address;

    private String image;

    private String password;

    private String verificationCode;

    private String role;

    private boolean isAvailable;

    private Date lastBloodDonationDate;

    @OneToMany(mappedBy = "bloodDonor", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BloodDonationHistory> bloodDonationHistories;

}
