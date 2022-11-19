package com.mss.blood.donation.repository;

import com.mss.blood.donation.entities.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long> {

    List<BloodDonor> findAllByBloodGroupName(String bloodGroup);
}
