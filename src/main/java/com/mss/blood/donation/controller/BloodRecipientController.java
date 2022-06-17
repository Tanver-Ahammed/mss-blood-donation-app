package com.mss.blood.donation.controller;

import com.mss.blood.donation.dto.BloodRecipientDTO;
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

    // registration blood recipient api
    @GetMapping(path = "/registration")
    public String registrationBloodRecipient(Model model) {
        model.addAttribute("bloodRecipientDTO", new BloodRecipientDTO());
        model.addAttribute("message", "");
        return "registration-blood-recipient";
    }

    // save blood recipient api
    @PostMapping(path = "/save")
    public String registrationBloodRecipient(@Valid @ModelAttribute("bloodRecipientDTO") BloodRecipientDTO bloodRecipientDTO, BindingResult result,
                                         @RequestParam(value = "bloodRecipientImage", required = false) MultipartFile bloodRecipientImage,
                                         Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("bloodRecipientDTO", bloodRecipientDTO);
            System.err.println(result);
            return "registration-blood-recipient";
        }
        System.out.println(bloodRecipientImage.getOriginalFilename());
        BloodRecipientDTO resultBloodRecipientDTO = this.bloodRecipientService.registrationBloodRecipient(bloodRecipientDTO, bloodRecipientImage);

        model.addAttribute("bloodRecipientDTO", new BloodRecipientDTO());
        model.addAttribute("message", "blood recipient is successfully added...");
        return "registration-blood-recipient";
    }

}
