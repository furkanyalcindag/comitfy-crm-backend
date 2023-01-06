package com.comitfy.fair.app.controller;

import com.comitfy.fair.app.dto.FairDTO;
import com.comitfy.fair.app.dto.FairParticipantDTO;
import com.comitfy.fair.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.fair.app.dto.requestDTO.FairRequestDTO;
import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.app.entity.FairParticipant;
import com.comitfy.fair.app.mapper.FairMapper;
import com.comitfy.fair.app.mapper.FairParticipantMapper;
import com.comitfy.fair.app.repository.FairParticipantRepository;
import com.comitfy.fair.app.repository.FairRepository;
import com.comitfy.fair.app.service.FairParticipantService;
import com.comitfy.fair.app.service.FairService;
import com.comitfy.fair.app.specification.FairParticipantSpecification;
import com.comitfy.fair.app.specification.FairSpecification;
import com.comitfy.fair.util.PageDTO;
import com.comitfy.fair.util.common.BaseCrudController;
import com.comitfy.fair.util.common.BaseFilterRequestDTO;
import com.comitfy.fair.util.common.SearchCriteria;
import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
        PageDTO<FairParticipantDTO> dtoList =getService().findAll(filter);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PostMapping("add-participant-by-fair/{id}")
    public ResponseEntity<FairParticipantDTO> addParticipantToFair(@PathVariable UUID id, @RequestBody FairParticipantRequestDTO fairParticipantRequestDTO) {

        FairDTO fairDTO = fairService.findByUUID(id);
        fairParticipantRequestDTO.setFair(fairDTO);
        FairParticipantDTO dtoList =getService().save(fairParticipantRequestDTO);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


    @PostMapping("add-participant-by-fair")
    public ResponseEntity<byte[]> addParticipantToActiveFair(@RequestBody FairParticipantRequestDTO fairParticipantRequestDTO) {

        FairDTO fairDTO = fairService.findActiveFair();
        fairParticipantRequestDTO.setFair(fairDTO);
        FairParticipantDTO dtoList =getService().save(fairParticipantRequestDTO);

        byte [] response = fairParticipantService.generateTicketByParticipant(dtoList.getUuid());

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
    public ResponseEntity<byte[]> getEmployeeRecordReport(@PathVariable UUID id) {

        try {


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            List<FairParticipant> c = new ArrayList<>();
            FairParticipant fairParticipant1 = fairParticipantService.findEntityByUUID(id);

            //dynamic parameters required for report
            Map<String, Object> empParams = new HashMap<String, Object>();


            empParams.put("qr",fairParticipantService.qrGenerate(fairParticipant1));
            empParams.put("name",fairParticipant1.getFirstName());
            empParams.put("surname",fairParticipant1.getLastName());
            empParams.put("company_name",fairParticipant1.getCompanyName());
            empParams.put("fair_name",fairParticipant1.getFair().getName());
            empParams.put("fair_start_date",fairParticipant1.getFair().getStartDate().format(formatter));
            empParams.put("fair_end_date",fairParticipant1.getFair().getEndDate().format(formatter));
            empParams.put("fair_place",fairParticipant1.getFair().getPlace());
            c.add(fairParticipant1);

            empParams.put("employeeData", new JRBeanCollectionDataSource(c));

           /*

           JasperCompileManager.compileReport(
                                            ResourceUtils.getFile("classpath:qr_ticket.jrxml")
                                                    .getAbsolutePath()) // path of the jasper report
            */

            JasperReport jasperReport = JasperCompileManager.compileReport( getClass().getResourceAsStream("/qr_ticket.jrxml"));

            JasperPrint empReport =
                    JasperFillManager.fillReport
                            (
                                    jasperReport
                                    , empParams // dynamic parameters
                                    , new JREmptyDataSource()
                            );

            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "ticket.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

        } catch(Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




