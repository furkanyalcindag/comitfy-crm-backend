package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.SettingsDTO;
import com.comitfy.crm.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.app.mapper.SettingsMapper;
import com.comitfy.crm.app.repository.SettingsRepository;
import com.comitfy.crm.app.service.SettingsService;
import com.comitfy.crm.app.specification.SettingsSpecification;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.util.common.BaseCrudController;
import com.comitfy.crm.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("settings")
public class SettingsController extends BaseCrudController<SettingsDTO, SettingsRequestDTO, Settings, SettingsRepository, SettingsMapper, SettingsSpecification, SettingsService> {

    @Autowired
    SettingsMapper settingsMapper;

    @Autowired
    SettingsService settingsService;

    @Autowired
    HelperService helperService;

    @Override
    protected SettingsService getService() {
        return settingsService;
    }

    @Override
    protected SettingsMapper getMapper() {
        return settingsMapper;
    }

    @PostMapping("/user-api")
    public ResponseEntity<SettingsRequestDTO> saveByUserId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                           @RequestBody SettingsRequestDTO settingsRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                return new ResponseEntity<>(settingsService.saveSettingsByUser(user.getUuid(), settingsRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user-api/{settingsId}")
    public ResponseEntity<String> updateSettings(@PathVariable UUID settingsId, @RequestBody SettingsRequestDTO dto) {
        User user = helperService.getUserFromSession();
        SettingsDTO settingsDTO = settingsService.findByUUID(settingsId);

        if (settingsDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            settingsService.updateSettings(settingsId, dto, user);
            //   dto.setLanguageEnum(LanguageEnum.valueOf(categoryDTO.getLanguage()));
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }

    }


    @PostMapping("/city-api")
    public ResponseEntity<List<String>> getCities() {

        String[] sehirler = {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin",
                "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale",
                "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum ", "Eskişehir",
                "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
                "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya ", "Malatya",
                "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
                "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon  ", "Tunceli", "Şanlıurfa", "Uşak",
                "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt ", "Karaman", "Kırıkkale", "Batman", "Şırnak",
                "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük ", "Kilis", "Osmaniye ", "Düzce"};

        return new ResponseEntity<>(Arrays.asList(sehirler), HttpStatus.OK);


    }


}
