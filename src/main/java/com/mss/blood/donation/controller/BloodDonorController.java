package com.mss.blood.donation.controller;

import com.mss.blood.donation.dto.BloodDonorDTO;
import com.mss.blood.donation.services.impl.BloodDonorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(path = "/blood/donor")
public class BloodDonorController {

    @Autowired
    private BloodDonorServiceImpl bloodDonorService;

    // registration blood donor
    @GetMapping(path = "/registration")
    public String addBloodDonor(Model model) {
        model.addAttribute("bloodDonorDTO", new BloodDonorDTO());
        model.addAttribute("message", "");
        return "registration-blood-donor";
    }

    // save blood donor
    @PostMapping(path = "/save")
    public String registrationBloodDonor(@Valid @ModelAttribute("bloodDonorDTO") BloodDonorDTO bloodDonorDTO, BindingResult result,
                                 @RequestParam(value = "bloodDonorImage", required = false) MultipartFile bloodDonorImage,
                                 Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("bloodDonorDTO", bloodDonorDTO);
            System.err.println(result);
            return "registration-blood-donor";
        }
        System.out.println(bloodDonorImage.getOriginalFilename());
        BloodDonorDTO resultBloodDonorDTO = this.bloodDonorService.registrationBloodDonor(bloodDonorDTO, bloodDonorImage);
        if (resultBloodDonorDTO == null) {
            model.addAttribute("bloodDonorDTO", bloodDonorDTO);
            model.addAttribute("message", "please enter your image...");
            return "registration-blood-donor";
        }
        model.addAttribute("bloodDonorDTO", new BloodDonorDTO());
        model.addAttribute("message", "blood donor is successfully added...");
        return "registration-blood-donor";
    }

}
