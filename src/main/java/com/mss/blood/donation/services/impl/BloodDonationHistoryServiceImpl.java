package com.mss.blood.donation.services.impl;

import com.mss.blood.donation.config.AppConstants;
import com.mss.blood.donation.dto.BloodDonationHistoryDTO;
import com.mss.blood.donation.dto.BloodRecipientDTO;
import com.mss.blood.donation.email.EmailSenderService;
import com.mss.blood.donation.entities.BloodDonationHistory;
import com.mss.blood.donation.entities.BloodDonor;
import com.mss.blood.donation.entities.BloodRecipient;
import com.mss.blood.donation.exception.ResourceNotFoundException;
import com.mss.blood.donation.repository.BloodDonationHistoryRepository;
import com.mss.blood.donation.repository.BloodDonorRepository;
import com.mss.blood.donation.repository.BloodRecipientRepository;
import com.mss.blood.donation.services.BloodDonationHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

@Service
public class BloodDonationHistoryServiceImpl implements BloodDonationHistoryService {


    @Autowired
    private BloodDonationHistoryRepository bloodDonationHistoryRepository;

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    @Autowired
    private BloodRecipientRepository bloodRecipientRepository;

    @Autowired
    private FileServiceImpl fileService;


    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("project.image")
    private String path;

    @Override
    public BloodDonationHistoryDTO addBloodDonationHistory(BloodRecipientDTO bloodRecipientDTO,
                                                           MultipartFile bloodRecipientImage,
                                                           Long bloodDonorId) throws IOException {

        if (!Objects.equals(bloodRecipientImage.getOriginalFilename(), "")) {
            String bloodDonorImageName = this.fileService.uploadImage(path, bloodRecipientImage);
            bloodRecipientDTO.setImage(bloodDonorImageName);
        }
        BloodRecipient bloodRecipient = this.modelMapper.map(bloodRecipientDTO, BloodRecipient.class);
        bloodRecipient = this.bloodRecipientRepository.save(bloodRecipient);
        BloodDonor bloodDonor = this.bloodDonorRepository.findById(bloodDonorId).orElseThrow(() ->
                new ResourceNotFoundException("Blood Donor", "id", bloodDonorId));
        BloodDonationHistory bloodDonationHistory = new BloodDonationHistory();
        bloodDonationHistory.setBloodDonor(bloodDonor);
        bloodDonationHistory.setBloodRecipient(bloodRecipient);
        bloodDonationHistory.setDonate(false);
        bloodDonationHistory.setBloodDonationDate(new Date());
        bloodDonationHistory = this.bloodDonationHistoryRepository.save(bloodDonationHistory);
        String siteURL = AppConstants.host + "/blood/donor/confirm/" + bloodDonationHistory.getId();

        String emailContent = "<p><b>I am " + bloodDonationHistory.getBloodRecipient().getName() + ",</b></p>"
                + "I am a patient. I need blood of " + bloodDonationHistory.getBloodRecipient().getBloodGroupName() + "<br>"
                + "Please, Try to give me your blood.<br>"
                + "contact with, Me<br>"
                + "Contact: " + bloodDonationHistory.getBloodRecipient().getContact() + "<br>"
                + "Email: " + bloodDonationHistory.getBloodRecipient().getEmail() + "<br>"
                + "<h1><a href=\"" + siteURL + "\" target=\"_self\">Confirm Donate Blood Request</a></h1>";

        sendBloodDonationRequestEmail(bloodDonationHistory, bloodDonationHistory.getBloodDonor().getEmail(), emailContent);
        return null;
    }

    @Override
    public void confirmBloodDonation(Long bloodDonationHistoryId) {
        BloodDonationHistory bloodDonationHistory = this.bloodDonationHistoryRepository.findById(bloodDonationHistoryId).orElseThrow(() ->
                new ResourceNotFoundException("blood donation history", "id", bloodDonationHistoryId));
        if (bloodDonationHistory.isDonate())
            return;
        String emailContent = "<p><b>I am " + bloodDonationHistory.getBloodDonor().getName() + ",</b></p>"
                + "I am confirmed that donate blood of you.<br>"
                + "Contact with, Me<br>"
                + "Contact: " + bloodDonationHistory.getBloodDonor().getContact() + "<br>"
                + "Email: " + bloodDonationHistory.getBloodDonor().getEmail() + "<br>"
                + "<h1><a href=\"" + "#" + "\" target=\"_self\">Okay Done!!</a></h1>";
        sendBloodDonationRequestEmail(bloodDonationHistory, bloodDonationHistory.getBloodRecipient().getEmail(), emailContent);
        bloodDonationHistory.setDonate(true);
        this.bloodDonationHistoryRepository.save(bloodDonationHistory);
    }

    // send email for verification
    public void sendBloodDonationRequestEmail(BloodDonationHistory bloodDonationHistory, String emailAddress, String emailContent) {
        String subject = "Hi " + bloodDonationHistory.getBloodDonor().getName() + ", welcome to blood donation";
        emailContent += "Thank you,<br>"
                + "MSS, Blood Donation App.";
        try {
            this.emailSenderService.sendEmailWithoutAttachment(emailAddress, subject, emailContent);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
