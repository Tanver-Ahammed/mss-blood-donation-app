package com.mss.blood.donation.services.impl;

import com.mss.blood.donation.config.AppConstants;
import com.mss.blood.donation.dto.BloodDonorDTO;
import com.mss.blood.donation.email.EmailSenderService;
import com.mss.blood.donation.entities.BloodDonor;
import com.mss.blood.donation.exception.ResourceNotFoundException;
import com.mss.blood.donation.repository.BloodDonorRepository;
import com.mss.blood.donation.services.BloodDonorService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class BloodDonorServiceImpl implements BloodDonorService {

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private EmailSenderService emailSenderService;

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
        bloodDonorDTO.setAvailable(false);
        String verificationCode = RandomString.make(64);
        bloodDonorDTO.setVerificationCode(verificationCode);
        BloodDonor bloodDonor = this.modelMapper.map(bloodDonorDTO, BloodDonor.class);
        bloodDonorDTO = this.modelMapper.map(this.bloodDonorRepository.save(bloodDonor), BloodDonorDTO.class);
        String siteURL = AppConstants.host + "/blood/donor/verify";
//        sendVerificationEmail(bloodDonorDTO, siteURL);
        return bloodDonorDTO;
    }

    // get a single blood donor
    @Override
    public BloodDonorDTO getSingleStudentById(Long bloodDonorId) {
        BloodDonor bloodDonor = this.bloodDonorRepository.findById(bloodDonorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Blood Donor", "id", bloodDonorId));
        return this.modelMapper.map(bloodDonor, BloodDonorDTO.class);
    }

    // get all blood donor
    @Override
    public List<BloodDonorDTO> getAllBloodDonor() {
        return this.bloodDonorRepository.findAll()
                .stream()
                .map(bloodDonor -> this.modelMapper
                        .map(bloodDonor, BloodDonorDTO.class))
                .toList();
    }

    @Override
    public List<BloodDonorDTO> getAllBloodDonorByBloodGroup(String bloodGroup) {
        return this.bloodDonorRepository.findAllByBloodGroupName(bloodGroup)
                .stream()
                .map(bloodDonor -> this.modelMapper
                        .map(bloodDonor, BloodDonorDTO.class))
                .toList();
    }

    // get all active blood donor
    @Override
    public List<BloodDonorDTO> getAllActiveBloodDonor() {
        return this.bloodDonorRepository.findAll()
                .stream()
                // blood donor is interested donate blood
                .filter(BloodDonor::isAvailable)
                // last blood donation more than 40 days or null
                .filter(bloodDonor -> bloodDonor.getLastBloodDonationDate() == null ||
                        ChronoUnit.DAYS.between(LocalDate.now(),
                                bloodDonor.getLastBloodDonationDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDate()) > 40)
                .map(bloodDonor -> this.modelMapper
                        .map(bloodDonor, BloodDonorDTO.class))
                .toList();
    }

    // verifying student by email
    @Override
    public boolean verifyBloodDonor(long id, String verifyCode) {
        BloodDonorDTO bloodDonorDTO = getSingleStudentById(id);
        if (bloodDonorDTO.getVerificationCode().equals(verifyCode)) {
            bloodDonorDTO.setAvailable(true);
            bloodDonorDTO.setVerificationCode(RandomString.make(64));
            this.bloodDonorRepository.save(this.modelMapper.map(bloodDonorDTO, BloodDonor.class));
            return true;
        }
        return false;
    }

    // send email for verification
    @Override
    public void sendVerificationEmail(BloodDonorDTO bloodDonorDTO, String siteURL) {
        String subject = "Please, Verify your registration";
        siteURL += "/" + bloodDonorDTO.getId() + "/" + bloodDonorDTO.getVerificationCode();
        String emailContent = "<p><b>Dear " + bloodDonorDTO.getName() + ",</b></p>"
                + "Please click the link below to verify your registration:<br>"
                + "<h1><a href=\"" + siteURL + "\" target=\"_self\">VERIFY</a></h1>"
                + "Thank you,<br>"
                + "MSS, Blood Donation App.";
        try {
            this.emailSenderService.sendEmailWithoutAttachment(bloodDonorDTO.getEmail(), subject, emailContent);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
