package com.comitfy.crm.app.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.SettingsDTO;
import com.comitfy.crm.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SettingsMapper extends BaseMapper<SettingsDTO, SettingsRequestDTO, Settings> {

    /* @Override
     public SettingsDTO entityToDTO(Settings entity) {
         SettingsDTO settingsDTO = new SettingsDTO();
         settingsDTO.setKey(entity.getKey());
         settingsDTO.setValue(entity.getValue());
         settingsDTO.setIsCurrent(entity.isCurrent());
         settingsDTO.setUuid(entity.getUuid());
         return settingsDTO;
     }

     @Override
     public Settings dtoToEntity(SettingsDTO dto) {
         Settings settings = new Settings();
         settings.setKey(dto.getKey());
         settings.setValue(dto.getValue());
         settings.setCurrent(dto.getIsCurrent());
         return settings;
     }

     @Override
     public Settings requestDTOToEntity(SettingsRequestDTO dto) {
         Settings settings = new Settings();
         settings.setKey(dto.getKey());
         settings.setValue(dto.getValue());
         settings.setCurrent(dto.isCurrent());
         return settings;

     }

     @Override
     public Settings requestDTOToExistEntity(Settings entity, SettingsRequestDTO dto) {
         entity.setKey(dto.getKey());
         entity.setValue(dto.getValue());
         entity.setCurrent(dto.isCurrent());
         return entity;
     }


     @Override
     public List<Settings> dtoListToEntityList(List<SettingsDTO> settingsDTOS) {
         List<Settings> settingsList = new ArrayList<>();
         for (SettingsDTO settingsDTO : settingsDTOS) {
             Settings settings = dtoToEntity(settingsDTO);
             settingsList.add(settings);
         }
         return settingsList;
     }

     @Override
     public List<SettingsDTO> entityListToDTOList(List<Settings> settingsEntity) {
         List<SettingsDTO> settingsDTOList = new ArrayList<>();
         for (Settings settings : settingsEntity) {
             SettingsDTO settingsDTO = entityToDTO(settings);
             settingsDTOList.add(settingsDTO);
         }
         return settingsDTOList;
     }

     @Override
     public PageDTO<SettingsDTO> pageEntityToPageDTO(Page<Settings> pageEntity) {
         PageDTO<SettingsDTO> pageDTO = new PageDTO<SettingsDTO>();
         List<Settings> entityList = pageEntity.toList();
         List<SettingsDTO> settingsDTOList = entityListToDTOList(entityList);
         pageDTO.setStart(pageEntity, settingsDTOList);
         return pageDTO;
     }*/
    @Mappings({@Mapping(source = "uuid", target = "value"),
            @Mapping(source = "key", target = "label")

    })
    AutoCompleteDTO entityToAutoCompleteDTO(Settings entity);
}
