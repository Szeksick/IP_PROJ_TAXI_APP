package pl.konradgugala.ordertaxi.business.service;

import pl.konradgugala.ordertaxi.business.entities.Drive;
import pl.konradgugala.ordertaxi.business.entities.City;
import pl.konradgugala.ordertaxi.business.entities.Role;
import pl.konradgugala.ordertaxi.business.entities.User;
import pl.konradgugala.ordertaxi.business.entities.repositories.DriveRepository;
import pl.konradgugala.ordertaxi.business.entities.repositories.CityRepository;
import pl.konradgugala.ordertaxi.business.entities.repositories.RoleRepository;
import pl.konradgugala.ordertaxi.business.entities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DriveRepository driveRepository;

    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("TAXI"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole=roleRepository.findByRole("USER");
        Role taxiRole = roleRepository.findByRole("TAXI");

        User user = new User("konradgugala@gmail.com", "gugala", "Konrad", "Gugala",true,"konrad");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("dk@kowalski.pl", "kowalski", "Dawid", "Kowalski",true,"dawid");
        user.setRoles(Arrays.asList(taxiRole));
        userRepository.save(user);

        user = new User("admin@admin.com","admin","Admin","User",true,"admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        City city = new City("Kielce");
        Drive drive = new Drive("Wrzosowa 4","Zimna 16", "Spieszy mi się", city);
        city.getDrives().add(drive);
        cityRepository.save(city);
        driveRepository.save(drive);

        city = new City("Chęciny");
        Drive drive1 = new Drive("Południowa 7","Hoża 17","Proszę podjechać o 14", city);
        city.getDrives().add(drive1);
        Drive drive2 = new Drive("Trapezowa 8","Kielce Krakowska 10","byle była klima bo gorąco", city);
        city.getDrives().add(drive2);
        cityRepository.save(city);
        driveRepository.save(drive1);
        driveRepository.save(drive2);

        city = new City("Masłów");
        drive = new Drive("Masłów I 149", "Lotnisko","szybko" , city);
        city.getDrives().add(drive);
        cityRepository.save(city);
        driveRepository.save(drive);
    }
}
