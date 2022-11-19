package com.mss.blood.donation.repository;

import com.mss.blood.donation.entities.BloodDonationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDonationHistoryRepository extends JpaRepository<BloodDonationHistory, Long> {
}
