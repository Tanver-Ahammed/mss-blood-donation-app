package com.mss.blood.donation.controller;

import com.mss.blood.donation.dto.BloodRecipientDTO;
import com.mss.blood.donation.services.impl.BloodDonorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/blood")
public class BloodSearch {

    @Autowired
    private BloodDonorServiceImpl bloodDonorService;

    @GetMapping(path = "/search")
    public String bloodSearchAll(Model model) {
        model.addAttribute("bloodDonorDTOS", this.bloodDonorService.getAllBloodDonor());
        return "show-all-blood-donor";
    }

    @GetMapping(path = "/search/group")
    public String bloodSearchAllByGroup(@RequestParam("bloodGroupName") String bloodGroup,
                                        Model model) {
        model.addAttribute("bloodDonorDTOS", this.bloodDonorService
                .getAllBloodDonorByBloodGroup(bloodGroup));
        return "show-all-blood-donor";
    }

    @GetMapping(path = "/donation/request/{bloodDonorId}")
    public String bloodBloodDonationRequest(@PathVariable("bloodDonorId") Long bloodDonorId, Model model) {
        model.addAttribute("bloodRecipientDTO", new BloodRecipientDTO());
        model.addAttribute("message", "");
        model.addAttribute("bloodDonorId", bloodDonorId);
        return "registration-blood-recipient";
    }


}
