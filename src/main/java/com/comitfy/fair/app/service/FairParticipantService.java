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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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


}
