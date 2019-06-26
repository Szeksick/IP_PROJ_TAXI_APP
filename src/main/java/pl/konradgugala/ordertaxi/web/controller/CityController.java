package pl.konradgugala.ordertaxi.web.controller;

import pl.konradgugala.ordertaxi.business.entities.City;
import pl.konradgugala.ordertaxi.business.entities.repositories.DriveRepository;
import pl.konradgugala.ordertaxi.business.entities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class CityController {

    @Autowired
    DriveRepository driveRepository;

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/addcity")
    public String cityForm(Model model){
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("city", new City());
        return "city";
    }

    @PostMapping("/processcity")
    public String processSubject(@Valid City city,
                                 BindingResult result,
                                 Model model){
        if(result.hasErrors()){
            for (ObjectError e : result.getAllErrors()){
                System.out.println(e);
            }
            return "city";
        }
        if(cityRepository.findByTitle(city.getTitle()) != null){
            model.addAttribute("message", "W systemie istnieje miasto o nazwie " +
                    city.getTitle() + "!" + " Dodaj inne miasto.");
            return "city";
        }
        cityRepository.save(city);
        return "redirect:/";
    }

    @RequestMapping("/detailcity/{id}")
    public String showDrivesBycity(@PathVariable("id") long id, Model model){
        model.addAttribute("drivess", driveRepository.findAllByCity_Id(id));
        model.addAttribute("city", cityRepository.findById(id).get());
        model.addAttribute("cities", cityRepository.findAll());
        return "citylist";
    }

    @PostConstruct
    public void fillTables(){
       /* City city = new City();
        city.setTitle("Kielce");
        cityRepository.save(city);

        city = new City();
        city.setTitle("Chęciny");
        cityRepository.save(city);

        city = new City();
        city.setTitle("Piekoszów");
        cityRepository.save(city);

        city = new City();
        city.setTitle("Cedzyna");
        cityRepository.save(city);*/
    }
}
