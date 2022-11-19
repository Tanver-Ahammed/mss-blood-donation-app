package com.mss.blood.donation.services;

import com.mss.blood.donation.dto.BloodRecipientDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface BloodRecipientService {

    // registration blood recipient
    BloodRecipientDTO registrationBloodRecipient(BloodRecipientDTO bloodRecipientDTO, MultipartFile bloodRecipientImage)
            throws IOException;

}
