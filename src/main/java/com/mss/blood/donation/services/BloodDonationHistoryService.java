package com.mss.blood.donation.services;

import com.mss.blood.donation.dto.BloodDonationHistoryDTO;
import com.mss.blood.donation.dto.BloodRecipientDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BloodDonationHistoryService {

    BloodDonationHistoryDTO addBloodDonationHistory(BloodRecipientDTO bloodRecipientDTO,
                                                    MultipartFile bloodRecipientImage, Long bloodDonorId) throws IOException;

    void confirmBloodDonation(Long bloodDonationHistoryId);

}
