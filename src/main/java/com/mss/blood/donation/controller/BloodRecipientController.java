package com.mss.blood.donation.controller;

import com.mss.blood.donation.dto.BloodDonationHistoryDTO;
import com.mss.blood.donation.dto.BloodRecipientDTO;
import com.mss.blood.donation.services.impl.BloodDonationHistoryServiceImpl;
import com.mss.blood.donation.services.impl.BloodRecipientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/blood/recipient")
public class BloodRecipientController {

    @Autowired
    private BloodRecipientServiceImpl bloodRecipientService;

    @Autowired
    private BloodDonationHistoryServiceImpl bloodDonationHistoryService;

    // registration blood recipient api
//    @GetMapping(path = "/registration")
//    public String registrationBloodRecipient(Model model) {
//        model.addAttribute("bloodRecipientDTO", new BloodRecipientDTO());
//        model.addAttribute("message", "");
//        return "registration-blood-recipient";
//    }

    // save blood recipient api
    @PostMapping(path = "/save")
    public String registrationBloodRecipient(@Valid @ModelAttribute("bloodRecipientDTO") BloodRecipientDTO bloodRecipientDTO, BindingResult result,
                                             @RequestParam(value = "bloodRecipientImage", required = false) MultipartFile bloodRecipientImage,
                                             @RequestParam(value = "bloodDonorId", required = false) Long bloodDonorId,
                                             Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("bloodRecipientDTO", bloodRecipientDTO);
            System.err.println(result);
            return "registration-blood-recipient";
        }
        System.out.println(bloodRecipientImage.getOriginalFilename());
        BloodDonationHistoryDTO resultBloodDonationHistoryDTO = this.bloodDonationHistoryService
                .addBloodDonationHistory(bloodRecipientDTO, bloodRecipientImage, bloodDonorId);

        model.addAttribute("bloodRecipientDTO", new BloodRecipientDTO());
        model.addAttribute("message", "send email successfully and waiting for confirmation...");
        return "registration-blood-recipient";
    }

}
