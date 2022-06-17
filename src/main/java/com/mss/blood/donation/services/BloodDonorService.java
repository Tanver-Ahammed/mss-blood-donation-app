package com.mss.blood.donation.services;

import com.mss.blood.donation.dto.BloodDonorDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface BloodDonorService {

    BloodDonorDTO registrationBloodDonor(BloodDonorDTO bloodDonorDTO, MultipartFile bloodDonorImage) throws IOException;

    BloodDonorDTO getSingleStudentById(Long id);

    boolean verifyBloodDonor(long id, String verifyCode);

}
