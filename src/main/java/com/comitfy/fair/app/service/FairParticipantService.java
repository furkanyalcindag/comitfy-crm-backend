package com.comitfy.fair.app.service;

import com.comitfy.fair.app.dto.FairParticipantDTO;
import com.comitfy.fair.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.fair.app.entity.FairParticipant;
import com.comitfy.fair.app.mapper.FairParticipantMapper;
import com.comitfy.fair.app.repository.FairParticipantRepository;
import com.comitfy.fair.app.specification.FairParticipantSpecification;
import com.comitfy.fair.app.specification.FairSpecification;
import com.comitfy.fair.util.common.BaseService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FairParticipantService extends BaseService<FairParticipantDTO, FairParticipantRequestDTO, FairParticipant, FairParticipantRepository, FairParticipantMapper, FairParticipantSpecification> {

    @Autowired
    FairParticipantRepository fairRepository;




    @Autowired
    FairParticipantMapper fairMapper;

    @Autowired
    FairParticipantSpecification fairSpecification;

    @Override
    public FairParticipantRepository getRepository() {
        return fairRepository;
    }

    @Override
    public FairParticipantMapper getMapper() {
        return fairMapper;
    }

    @Override
    public FairParticipantSpecification getSpecification() {
        return fairSpecification;
    }




    public String qrGenerate(FairParticipant fairParticipant) throws IOException, WriterException, JRException {
        String url = fairParticipant.getUuid().toString();

        int imageSize = 200;
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,
                imageSize, imageSize);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", bos);
        String image = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode

        // return QrInfo
        System.out.println(image);





        return image;
    }





    public byte[] generateTicketByParticipant(UUID id){

        try {


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            List<FairParticipant> c = new ArrayList<>();
            FairParticipant fairParticipant1 = findEntityByUUID(id);

            //dynamic parameters required for report
            Map<String, Object> empParams = new HashMap<String, Object>();


            empParams.put("qr",qrGenerate(fairParticipant1));
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
            return
                    JasperExportManager.exportReportToPdf(empReport);

        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }




    }


}
