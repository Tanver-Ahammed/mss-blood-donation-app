package com.mss.blood.donation.repository;

import com.mss.blood.donation.entities.BloodRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodRecipientRepository extends JpaRepository<BloodRecipient, Long> {
}
