package com.mss.blood.donation.controller;

import com.mss.blood.donation.dto.BloodDonorDTO;
import com.mss.blood.donation.services.impl.BloodDonorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(path = "/blood/donor")
public class BloodDonorController {

    @Autowired
    private BloodDonorServiceImpl bloodDonorService;

    // add blood donor
    @GetMapping(path = "/registration")
    public String addBloodDonor(Model model) {
        model.addAttribute("bloodDonorDTO", new BloodDonorDTO());
        model.addAttribute("message", "");
        return "add-blood-donor";
    }

    // save blood donor
    @PostMapping(path = "/save")
    public String saveBloodDonor(@ModelAttribute("bloodDonorDTO") BloodDonorDTO bloodDonorDTO, BindingResult result,
                                 @RequestParam(value = "bloodDonorImage", required = false) MultipartFile bloodDonorImage,
                                 Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("bloodDonorDTO", bloodDonorDTO);
            return "add-blood-donor";
        }
        System.out.println(bloodDonorImage.getOriginalFilename());
        bloodDonorDTO = this.bloodDonorService.addBloodDonor(bloodDonorDTO, bloodDonorImage);
        model.addAttribute("bloodDonorDTO", new BloodDonorDTO());
        model.addAttribute("message", "blood donor is successfully added...");
//        return "redirect:/blood/donor/add";
        return "add-blood-donor";
    }

}
