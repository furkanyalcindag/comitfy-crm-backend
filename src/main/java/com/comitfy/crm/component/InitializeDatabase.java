package com.comitfy.crm.component;

import com.comitfy.crm.app.entity.City;
import com.comitfy.crm.app.entity.Currency;
import com.comitfy.crm.app.entity.District;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.app.repository.CityRepository;
import com.comitfy.crm.app.repository.CurrencyRepository;
import com.comitfy.crm.app.repository.DistrictRepository;
import com.comitfy.crm.app.repository.SettingsRepository;
import com.comitfy.crm.userModule.entity.Role;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.repository.RoleRepository;
import com.comitfy.crm.userModule.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitializeDatabase implements CommandLineRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    SettingsRepository settingsRepository;


    @Override
    public void run(String... args) throws Exception {
        loadRoleData();
        loadUserData();
        loadCurrency();
        loadCity();
        loadDistrict();
        loadSettings();
    }

    private void loadCity() throws FileNotFoundException {


        if (cityRepository.count() > 0) {

        } else {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<City>> typeReference = new TypeReference<List<City>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/db/cities.json");
            try {
                List<City> cityList = mapper.readValue(inputStream, typeReference);

                for (City city : cityList) {

                    cityRepository.save(city);

                }

                System.out.println("city Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save city: " + e.getMessage());
            }
        }


    }


    private void loadDistrict() throws FileNotFoundException {

        if (districtRepository.count() > 0) {

        } else {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<District>> typeReference = new TypeReference<List<District>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/db/districts.json");
            try {
                List<District> cityList = mapper.readValue(inputStream, typeReference);

                for (District city : cityList) {

                    districtRepository.save(city);

                }

                System.out.println("district Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save city: " + e.getMessage());
            }
        }

    }


    private void loadRoleData() {
        if (roleRepository.count() == 0) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);
        }


    }

    private void loadCurrency() {
        if (currencyRepository.count() == 0) {
            Currency currency = new Currency();
            currency.setIsDefault(Boolean.TRUE);
            currency.setName("TL:-:TRY");
            currency.setSymbol("₺");
            currency.setExchangeRate(BigDecimal.ONE);
            currencyRepository.save(currency);
        }


    }


    private void loadUserData() {

        if (userRepository.count() == 0) {
            User user = new User();
            user.setEmail("admin");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("Comitfy2022."));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ADMIN").get());
            user.setRoles(roles);


            userRepository.save(user);

        }
        System.out.println(userRepository.count());
    }


    private void loadSettings() throws FileNotFoundException {


        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Settings>> typeReference = new TypeReference<List<Settings>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/db/settings.json");
        try {
            List<Settings> settingsList = mapper.readValue(inputStream, typeReference);

            for (Settings settings : settingsList) {
                settings.setCurrent(Boolean.TRUE);

                try{
                    settingsRepository.save(settings);
                    System.out.println("settings Saved!");
                }
                catch (Exception e){
                    System.out.println("duplicated setting: " + e.getMessage());
                }


            }


        } catch (IOException e) {
            System.out.println("duplicated setting: " + e.getMessage());
        }


    }


}
