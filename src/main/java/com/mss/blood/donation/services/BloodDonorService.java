package com.mss.blood.donation.services;

import com.mss.blood.donation.dto.BloodDonorDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface BloodDonorService {

    // add blood donor
    BloodDonorDTO registrationBloodDonor(BloodDonorDTO bloodDonorDTO, MultipartFile bloodDonorImage) throws IOException;

    // get a single blood donor
    BloodDonorDTO getSingleStudentById(Long id);

    // get all blood donor
    List<BloodDonorDTO> getAllBloodDonor();

    // get all blood donor by blood group
    List<BloodDonorDTO> getAllBloodDonorByBloodGroup(String bloodGroup);

    // get all active blood donor
    List<BloodDonorDTO> getAllActiveBloodDonor();

    // verifying student by email
    boolean verifyBloodDonor(long id, String verifyCode);

    // send email for verification
    void sendVerificationEmail(BloodDonorDTO bloodDonorDTO, String siteURL);

}
