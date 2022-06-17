package com.mss.blood.donation.services.impl;

import com.mss.blood.donation.dto.BloodDonorDTO;
import com.mss.blood.donation.entities.BloodDonor;
import com.mss.blood.donation.repository.BloodDonorRepository;
import com.mss.blood.donation.services.BloodDonorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class BloodDonorServiceImpl implements BloodDonorService {

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    // add blood donor
    @Override
    public BloodDonorDTO registrationBloodDonor(BloodDonorDTO bloodDonorDTO, MultipartFile bloodDonorImage) throws IOException {
        if (Objects.equals(bloodDonorImage.getOriginalFilename(), "")) {
            return null;
        }
        String bloodDonorImageName = this.fileService.uploadImage(path, bloodDonorImage);
        bloodDonorDTO.setImage(bloodDonorImageName);
        bloodDonorDTO.setAvailable(true);
        return this
                .modelMapper
                .map(this.bloodDonorRepository
                                .save(this.modelMapper
                                        .map(bloodDonorDTO, BloodDonor.class)),
                        BloodDonorDTO.class);
    }
}
