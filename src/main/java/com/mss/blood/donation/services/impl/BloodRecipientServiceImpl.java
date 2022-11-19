package com.mss.blood.donation.services.impl;

import com.mss.blood.donation.dto.BloodRecipientDTO;
import com.mss.blood.donation.entities.BloodRecipient;
import com.mss.blood.donation.repository.BloodRecipientRepository;
import com.mss.blood.donation.services.BloodRecipientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class BloodRecipientServiceImpl implements BloodRecipientService {

    @Autowired
    private BloodRecipientRepository bloodRecipientRepository;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    // registration blood recipient
    @Override
    public BloodRecipientDTO registrationBloodRecipient(BloodRecipientDTO bloodRecipientDTO, MultipartFile bloodRecipientImage) throws IOException {
        if (!Objects.equals(bloodRecipientImage.getOriginalFilename(), "")) {
            String bloodDonorImageName = this.fileService.uploadImage(path, bloodRecipientImage);
            bloodRecipientDTO.setImage(bloodDonorImageName);
        }
        return this.modelMapper.map(this.bloodRecipientRepository
                        .save(this.modelMapper.map(bloodRecipientDTO, BloodRecipient.class)),
                BloodRecipientDTO.class);
    }

    // get a single blood donor


    // get all blood recipient


}
