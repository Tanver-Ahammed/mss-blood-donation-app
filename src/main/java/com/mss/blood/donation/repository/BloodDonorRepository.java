package com.mss.blood.donation.repository;

import com.mss.blood.donation.entities.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long> {
}
