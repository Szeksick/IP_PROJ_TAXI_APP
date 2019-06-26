package pl.konradgugala.ordertaxi.web.controller;

import pl.konradgugala.ordertaxi.business.entities.Drive;
import pl.konradgugala.ordertaxi.business.service.UserService;
import pl.konradgugala.ordertaxi.business.entities.repositories.DriveRepository;
import pl.konradgugala.ordertaxi.business.entities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    DriveRepository driveRepository;

    @Autowired
    CityRepository cityRepository;


    @Autowired
    UserService userService;


    @RequestMapping("/")
    public String homeSite(Model model){
        model.addAttribute("drives", driveRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        if(userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
            if(userService.getUser().checkRole("TAXI") || userService.getUser().checkRole("ADMIN")){
                return "redirect:/list";
            }
        }
        return "index";
    }
    @RequestMapping("/list")
    public String listDrives(Model model){
        model.addAttribute("drives", driveRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        if(userService.getUser() != null){
            model.addAttribute("user_id", userService.getUser().getId());
        }
        if(userService.getUser().checkRole("TAXI") || userService.getUser().checkRole("ADMIN")){
           return "list";
        }
        return "list";
    }

    @GetMapping("/add")
    public String driveForm(Model model){
        model.addAttribute("drive", new Drive());
        model.addAttribute("cities", cityRepository.findAll());
        return "driveform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Drive drive,
                              BindingResult result,
                              Model model){
        if(drive.getOrderDate() == null){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            drive.setOrderDate(dateFormat.format(date));
        }
        System.out.println("object = " + drive);
        if(result.hasErrors()){
            for (ObjectError e : result.getAllErrors()){
                System.out.println(e);
            }
            model.addAttribute("cities", cityRepository.findAll());
            driveRepository.save(drive);
            return "redirect:/";
        }



        drive.setUser(userService.getUser());
        driveRepository.save(drive);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showDrive(@PathVariable("id") long id, Model model){
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("drive", driveRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateDrive(@PathVariable("id") long id, Model model){
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("drive", driveRepository.findById(id).get());
        return "driveform";
    }

    @RequestMapping("/takethis/{id}")
    public String delDrive(@PathVariable("id") long id){
        driveRepository.findById(id);
        return "redirect:/";
    }


    @GetMapping("/termsandconditions")
    public String getTermsAndCondition(){
        return "termsandconditions";
    }

    @PostMapping("/forgot-password")
    public String forgetPassword(){
        return "forgotpassword";
    }


}









