package com.mss.blood.donation.services;

import com.mss.blood.donation.dto.BloodDonorDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface BloodDonorService {

    BloodDonorDTO addBloodDonor(BloodDonorDTO bloodDonorDTO, MultipartFile bloodDonorImage) throws IOException;

}
