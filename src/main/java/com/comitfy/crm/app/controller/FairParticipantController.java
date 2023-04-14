package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.FairDTO;
import com.comitfy.crm.app.dto.FairParticipantDTO;
import com.comitfy.crm.app.dto.FairParticipantValidateDTO;
import com.comitfy.crm.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.crm.app.entity.FairParticipant;
import com.comitfy.crm.app.mapper.FairParticipantMapper;
import com.comitfy.crm.app.repository.FairParticipantRepository;
import com.comitfy.crm.app.service.FairParticipantService;
import com.comitfy.crm.app.service.FairService;
import com.comitfy.crm.app.specification.FairParticipantSpecification;
import com.comitfy.crm.util.PageDTO;
import com.comitfy.crm.util.common.BaseCrudController;
import com.comitfy.crm.util.common.BaseFilterRequestDTO;
import com.comitfy.crm.util.common.SearchCriteria;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("fair-participant")
public class FairParticipantController extends BaseCrudController<FairParticipantDTO, FairParticipantRequestDTO, FairParticipant, FairParticipantRepository, FairParticipantMapper, FairParticipantSpecification, FairParticipantService> {

    @Autowired
    FairParticipantService fairParticipantService;

    @Autowired
    FairParticipantMapper fairMapper;

    @Autowired
    FairService fairService;

    @Override
    protected FairParticipantService getService() {
        return fairParticipantService;
    }

    @Override
    protected FairParticipantMapper getMapper() {
        return fairMapper;
    }


    @PostMapping("get-participants-by-fair/{id}")
    public ResponseEntity<PageDTO<FairParticipantDTO>> getAllByFairUUID(@PathVariable UUID id, @RequestBody BaseFilterRequestDTO filter) {

        FairDTO fairDTO = fairService.findByUUID(id);

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("fair_id");
        searchCriteria.setOperation("=");
        searchCriteria.setValue(fairDTO.getId());
        filter.getFilters().add(searchCriteria);
        PageDTO<FairParticipantDTO> dtoList = getService().findAll(filter);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("get-participateds-by-fair/{id}")
    public ResponseEntity<PageDTO<FairParticipantDTO>> getParticipatedByFairUUID(@PathVariable UUID id, @RequestBody BaseFilterRequestDTO filter) {

        FairDTO fairDTO = fairService.findByUUID(id);

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("fair_id");
        searchCriteria.setOperation("=");
        searchCriteria.setValue(fairDTO.getId());

        SearchCriteria searchCriteria2 = new SearchCriteria();
        searchCriteria2.setKey("isParticipated");
        searchCriteria2.setOperation("=");
        searchCriteria2.setValue(Boolean.TRUE);


        filter.getFilters().add(searchCriteria);
        filter.getFilters().add(searchCriteria2);
        PageDTO<FairParticipantDTO> dtoList = getService().findAll(filter);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PostMapping("add-participant-by-fair/{id}")
    public ResponseEntity<FairParticipantDTO> addParticipantToFair(@PathVariable UUID id, @RequestBody FairParticipantRequestDTO fairParticipantRequestDTO) {

        FairDTO fairDTO = fairService.findByUUID(id);
        fairParticipantRequestDTO.setFair(fairDTO);
        FairParticipantDTO dtoList = getService().save(fairParticipantRequestDTO);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PostMapping("add-participant-by-active-fair")
    public ResponseEntity<byte[]> addParticipantToActiveFair(@RequestBody FairParticipantRequestDTO fairParticipantRequestDTO) {

        FairDTO fairDTO = fairService.findActiveFair();
        fairParticipantRequestDTO.setFair(fairDTO);
        FairParticipantDTO dtoList = getService().save(fairParticipantRequestDTO);

        byte[] response = fairParticipantService.generateTicketByParticipantNewVersion(dtoList.getUuid());

        HttpHeaders headers = new HttpHeaders();
        //set the PDF format
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "ticket.pdf");
        return new ResponseEntity<byte[]>
                (response, headers, HttpStatus.OK);
    }

   /* @PostMapping("getqr")
    public ResponseEntity<String> generateQR() {
        FairParticipant fairParticipant = new FairParticipant();
        fairParticipant.setUuid(java.util.UUID.randomUUID());
        String x="";
        try{
            x=getService().qrGenerate(fairParticipant);
        }
        catch (Exception e){
            System.out.println("sdmks");
        }

        return new ResponseEntity<>(x, HttpStatus.OK);
    }*/


   /* @GetMapping("generate")
    public void getDocument(HttpServletResponse response) throws IOException, JRException, WriterException {

        String sourceFileName = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "qr_ticket.jasper").getAbsolutePath();
// creating our list of beans

// creating datasource from bean list
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(null);
        Map parameters = new HashMap<>();
        FairParticipant fairParticipant = new FairParticipant();
        fairParticipant.setUuid(java.util.UUID.randomUUID());
        parameters.put("qr",fairParticipantService.qrGenerate(fairParticipant));

        JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, beanColDataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
    }*/


    @GetMapping("/generate-ticket/{id}")
    public ResponseEntity<byte[]> getGenerateTicket(@PathVariable UUID id) {

        try {


            byte[] response = fairParticipantService.generateTicketByParticipantNewVersion(id);
            if(response==null)
                throw new Exception("response is null");
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "ticket.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (response, headers, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/validate-participant/{id}")
    public ResponseEntity<FairParticipantValidateDTO> validateParticipant(@PathVariable UUID id) {
        try {
            return new ResponseEntity<FairParticipantValidateDTO>
                    (fairParticipantService.validateParticipant(id), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<FairParticipantValidateDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/generate-excel-participant-by-fair/{id}")
    void getExportExcelGenerateProforma(HttpServletResponse response, @PathVariable UUID id) throws IOException, NoSuchFieldException{

        ByteArrayInputStream byteArrayInputStream = fairParticipantService.getExportExcelParticipant(id);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=fuar_katilimci_listesi.xlsx");

        IOUtils.copy(byteArrayInputStream, response.getOutputStream());

    }


    @PostMapping("/generate-excel-participateds-by-fair/{id}")
    void getExportExcelGenerateParticipateds(HttpServletResponse response, @PathVariable UUID id) throws IOException, NoSuchFieldException{

        ByteArrayInputStream byteArrayInputStream = fairParticipantService.getExportExcelParticipateds(id);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=fuar_katilimci_listesi.xlsx");

        IOUtils.copy(byteArrayInputStream, response.getOutputStream());

    }

}




