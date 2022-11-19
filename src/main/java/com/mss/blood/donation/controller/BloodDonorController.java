package com.mss.blood.donation.controller;

import com.mss.blood.donation.dto.BloodDonorDTO;
import com.mss.blood.donation.repository.BloodDonationHistoryRepository;
import com.mss.blood.donation.services.impl.BloodDonationHistoryServiceImpl;
import com.mss.blood.donation.services.impl.BloodDonorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/blood/donor")
public class BloodDonorController {

    @Autowired
    private BloodDonorServiceImpl bloodDonorService;

    @Autowired
    private BloodDonationHistoryServiceImpl bloodDonationHistoryService;

    // registration blood donor api
    @GetMapping(path = "/registration")
    public String addBloodDonor(Model model) {
        model.addAttribute("bloodDonorDTO", new BloodDonorDTO());
        model.addAttribute("message", "");
        return "registration-blood-donor";
    }

    // save blood donor api
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

    // get all blood donor api
    @GetMapping(path = "/all")
    @ResponseBody
    public String getAllBloodDonors(Model model) {
        model.addAttribute("bloodDonorDTOS", this.bloodDonorService.getAllBloodDonor());
        for (BloodDonorDTO bloodDonorDTO : this.bloodDonorService.getAllBloodDonor()) {
            if (bloodDonorDTO.getLastBloodDonationDate() != null) {
                LocalDate date = bloodDonorDTO.getLastBloodDonationDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                System.out.println(ChronoUnit.DAYS.between(date, LocalDate.now()));
                System.out.println(date);
            }
        }
//        LocalDate beginDate = LocalDate.of(2022, 6,15);
//        LocalDate today = LocalDate.now();
//        System.out.println(ChronoUnit.DAYS.between(beginDate, today));
        return "show-all-blood-donor";
    }

    // get all active blood donor api
    @GetMapping(path = "/all/active")
    @ResponseBody
    public List<BloodDonorDTO> getAllActiveBloodDonors(Model model) {
        model.addAttribute("bloodDonorDTOS", this.bloodDonorService.getAllActiveBloodDonor());
        return this.bloodDonorService.getAllActiveBloodDonor();
//        return "show-all-active-blood-donor";
    }

    // image show api
    @GetMapping(path = "/verify/{id}/{verifyCode}")
    public String verifyBloodDonor(@PathVariable("id") Integer id,
                                   @PathVariable("verifyCode") String verifyCode,
                                   Model model) {
        boolean isMatch = this.bloodDonorService.verifyBloodDonor(id, verifyCode);
        if (isMatch)
            model.addAttribute("message", "Your email verifying successfully....");
        else
            model.addAttribute("message", "Your email don't verifying successfully....");
        return "home";
    }

    @GetMapping(path = "/confirm/{bloodDonationHistory}")
    public String confirmBloodDonation(@PathVariable("bloodDonationHistory") Long bloodDonationHistory,
                                       Model model) {
        this.bloodDonationHistoryService.confirmBloodDonation(bloodDonationHistory);
        model.addAttribute("message", "Okay Done!!! Send E-Mail");
        return "home";
    }

}
