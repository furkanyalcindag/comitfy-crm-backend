package com.comitfy.fair.app.service;

import com.comitfy.fair.app.dto.ExportHeaderDTO;
import com.comitfy.fair.app.dto.FairParticipantDTO;
import com.comitfy.fair.app.dto.FairParticipantValidateDTO;
import com.comitfy.fair.app.dto.requestDTO.FairParticipantRequestDTO;
import com.comitfy.fair.app.entity.Fair;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Objects.nonNull;

@Service
public class FairParticipantService extends BaseService<FairParticipantDTO, FairParticipantRequestDTO, FairParticipant, FairParticipantRepository, FairParticipantMapper, FairParticipantSpecification> {

    @Autowired
    FairParticipantRepository fairRepository;

    @Autowired
    FairParticipantMapper fairMapper;

    @Autowired
    FairService fairService;

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

    public byte[] generateTicketByParticipant(UUID id) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            List<FairParticipant> c = new ArrayList<>();
            FairParticipant fairParticipant1 = findEntityByUUID(id);

            //dynamic parameters required for report
            Map<String, Object> empParams = new HashMap<String, Object>();
            empParams.put("qr", qrGenerate(fairParticipant1));
            empParams.put("name", fairParticipant1.getFirstName());
            empParams.put("surname", fairParticipant1.getLastName());
            empParams.put("company_name", fairParticipant1.getCompanyName());
            empParams.put("fair_name", fairParticipant1.getFair().getName());
            empParams.put("fair_start_date", fairParticipant1.getFair().getStartDate().format(formatter));
            empParams.put("fair_end_date", fairParticipant1.getFair().getEndDate().format(formatter));
            empParams.put("fair_place", fairParticipant1.getFair().getPlace());
            c.add(fairParticipant1);
            empParams.put("employeeData", new JRBeanCollectionDataSource(c));

           /*

           JasperCompileManager.compileReport(
                                            ResourceUtils.getFile("classpath:qr_ticket.jrxml")
                                                    .getAbsolutePath()) // path of the jasper report
            */

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/qr_ticket.jrxml"));

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

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }




    public byte[] generateTicketByParticipantNewVersion(UUID id) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            List<FairParticipant> c = new ArrayList<>();
            FairParticipant fairParticipant1 = findEntityByUUID(id);

            //dynamic parameters required for report
            Map<String, Object> empParams = new HashMap<String, Object>();
            empParams.put("qr", qrGenerate(fairParticipant1));
            empParams.put("name", fairParticipant1.getFirstName().toUpperCase(Locale.ENGLISH) + " " +fairParticipant1.getLastName().toUpperCase(Locale.ENGLISH));
            empParams.put("surname", fairParticipant1.getLastName());
            empParams.put("company_name", fairParticipant1.getCompanyName().toUpperCase(Locale.ENGLISH) + "-" + fairParticipant1.getCity().toUpperCase(Locale.ENGLISH) );
            empParams.put("fair_name", fairParticipant1.getFair().getName());
            empParams.put("fair_start_date", fairParticipant1.getFair().getStartDate().format(formatter));
            empParams.put("fair_end_date", fairParticipant1.getFair().getEndDate().format(formatter));
            empParams.put("fair_place", fairParticipant1.getFair().getPlace());
            c.add(fairParticipant1);
            empParams.put("employeeData", new JRBeanCollectionDataSource(c));

           /*

           JasperCompileManager.compileReport(
                                            ResourceUtils.getFile("classpath:qr_ticket.jrxml")
                                                    .getAbsolutePath()) // path of the jasper report
            */

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/qr_ticket_a5.jrxml"));

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

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public FairParticipantValidateDTO validateParticipant(UUID participantUUID) {

        FairParticipantValidateDTO fairParticipantValidateDTO = new FairParticipantValidateDTO();

        FairParticipantDTO fairParticipantDTO = findByUUID(participantUUID);

        if (fairParticipantDTO.getFairDTO().isActive()) {
            fairParticipantValidateDTO.setFairParticipantDTO(fairParticipantDTO);
            fairParticipantValidateDTO.setValid(Boolean.TRUE);
        } else {
            fairParticipantValidateDTO.setFairParticipantDTO(null);
            fairParticipantValidateDTO.setValid(Boolean.FALSE);
        }

        return fairParticipantValidateDTO;
    }


    public List<ExportHeaderDTO> fillHeaderForProforma() {

        Map<String,String> translate = new HashMap<>();
        translate.put("firstName","Adı");
        translate.put("lastName","Soyadı");
        translate.put("companyName","Firma");
        translate.put("mobilePhone","Telefon");
        translate.put("email","Email");
        translate.put("city","Şehir");


        List<ExportHeaderDTO> exportHeaderDTOS = new ArrayList<>();
        Field[] allFields = new Field[200];
        allFields = FairParticipantDTO.class.getDeclaredFields();
        for (Field field : allFields) {

            if(!field.getName().equals("fairDTO")){
                ExportHeaderDTO exportHeaderDTO = new ExportHeaderDTO();
                exportHeaderDTO.setLabel(translate.get(field.getName()));
                exportHeaderDTO.setName(field.getName());
                exportHeaderDTO.setSelected(true);

                exportHeaderDTOS.add(exportHeaderDTO);
            }





        }
        return exportHeaderDTOS;
    }

    public Cell createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style, boolean isLocal, Integer utc) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDateTime) {
            LocalDateTime dt = (LocalDateTime) value;
            if (isLocal && utc != null) {
                dt = dt.plusHours(utc / 60);
            }
            //LocalDateTime dt = (LocalDateTime) value;
            java.util.Date date = java.util.Date
                    .from(dt.atZone(ZoneId.systemDefault())
                            .toInstant());
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        } else {
            cell.setCellValue(value.toString()!=null?value.toString():"-");
        }
        if (style != null) {
            cell.setCellStyle(style);
        }
        return cell;
    }

    public ByteArrayInputStream getExportExcelParticipant(UUID uuid) {

        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            List<ExportHeaderDTO> exportHeaderDTOList = new ArrayList<>();

            SXSSFWorkbook streamWorkbook = new SXSSFWorkbook(wb, 100);
            streamWorkbook.setCompressTempFiles(true);

            XSSFSheet sheet = streamWorkbook.getXSSFWorkbook().createSheet("fuar_katilimci_listesi");

            Row row = sheet.createRow(0);

            int rowCount = 1;
            long starttime = System.currentTimeMillis();


            Fair fair = fairService.findEntityByUUID(uuid);
            List<FairParticipantDTO> fairParticipantDTOList = getMapper().entityListToDTOList(getRepository().findAllByFairOrderByIdDesc(fair));


            Field[] allFields = FairParticipantDTO.class.getDeclaredFields();

            // Header attributes
            Map<String, String> hmHeaderAttributeNames = new HashMap<>();
            exportHeaderDTOList = fillHeaderForProforma();
            for (ExportHeaderDTO exportHeaderDTO : exportHeaderDTOList) {
                hmHeaderAttributeNames.put(exportHeaderDTO.getName(), exportHeaderDTO.getName());
            }


            Map<FairParticipantDTO, Map<String, Object>> hmObjectValues = new HashMap<>();
            Map<String, Integer> hmSizeOfColumnsByAttribute = new HashMap<>();
            Class<?> clazz = null;
            if (CollectionUtils.isNotEmpty(fairParticipantDTOList)) {
                clazz = fairParticipantDTOList.get(0).getClass();
                Map<String, Field> hmFields = new HashMap<>();
                for (int i = 0; i < allFields.length; i++) {
                    Field field = clazz.getDeclaredField(allFields[i].getName()); //Note, this can throw an exception if the field doesn't exist.
                    field.setAccessible(true);
                    hmFields.put(field.getName(), field);
                }


                for (FairParticipantDTO cmsServiceUsageExportDTO : fairParticipantDTOList) {
                    Map<String, Object> hmAttributeValues = new HashMap<String, Object>();
                    hmObjectValues.put(cmsServiceUsageExportDTO, hmAttributeValues);

                    for (int i = 0; i < allFields.length; i++) {

                        if (!hmHeaderAttributeNames.containsKey(allFields[i].getName())) {
                            continue;
                        }
                        Field field = hmFields.get(allFields[i].getName());
                        Object fieldValue = "-";
                        if (field.get(cmsServiceUsageExportDTO) != null) {
                            fieldValue = field.get(cmsServiceUsageExportDTO);
                        }
                        hmAttributeValues.put(field.getName(), fieldValue);
                        if (!hmSizeOfColumnsByAttribute.containsKey(field.getName())) {
                            hmSizeOfColumnsByAttribute.put(field.getName(), 0);
                        }
                        Integer sizeOfField = hmSizeOfColumnsByAttribute.get(field.getName());
                        if (fieldValue != null && String.valueOf(fieldValue).length() > sizeOfField) {
                            hmSizeOfColumnsByAttribute.put(field.getName(), String.valueOf(fieldValue).length());
                        }
                    }
                }

            }

            XSSFFont defaultFontHeader = streamWorkbook.getXSSFWorkbook().createFont();
            defaultFontHeader.setFontHeightInPoints((short) 10);
            defaultFontHeader.setFontName("Arial");
            defaultFontHeader.setColor(HSSFColor.WHITE.index);
            defaultFontHeader.setBold(true);
            defaultFontHeader.setItalic(false);


            XSSFFont defaultFont = streamWorkbook.getXSSFWorkbook().createFont();
            defaultFont.setFontHeightInPoints((short) 10);
            defaultFont.setFontName("Arial");
            defaultFont.setColor(IndexedColors.BLACK.getIndex());
            defaultFont.setBold(true);
            defaultFont.setItalic(false);

            CellStyle style = streamWorkbook.getXSSFWorkbook().createCellStyle();
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style.setAlignment(CellStyle.ALIGN_CENTER);
            // Setting font to style
            style.setFont(defaultFontHeader);


            int k = 0;
            for (int i = 0; i < allFields.length; i++) {
                for (ExportHeaderDTO exportHeaderDTO : exportHeaderDTOList) {
                    if (allFields[i].getName().equals(exportHeaderDTO.getName())) {
                        createCell(sheet, row, k, exportHeaderDTO.getLabel(), style, true, null);
                        int columnWidth = exportHeaderDTO.getName().length() > hmSizeOfColumnsByAttribute
                                .get(allFields[i].getName()) ? exportHeaderDTO.getName().length()
                                : hmSizeOfColumnsByAttribute.get(allFields[i].getName());
                        sheet.setColumnWidth(k, (columnWidth + 3) * 256);
                        k++;
                    }
                }


            }


            for (FairParticipantDTO proformaExportDTO : fairParticipantDTOList) {

                row = sheet.createRow(rowCount++);
                int y = 0;
                Map<String, Object> hmAttributeValues = hmObjectValues.get(proformaExportDTO);
                for (int i = 0; i < allFields.length; i++) {
                    if (!hmHeaderAttributeNames.containsKey(allFields[i].getName())) {
                        continue;
                    }
                    Object fieldValue = hmAttributeValues.get(allFields[i].getName());
                    if (nonNull(fieldValue))
                        createCell(sheet, row, y, fieldValue, null,true,
                                null);
                    else
                        createCell(sheet, row, y, "", null, true,
                                null);
                    y++;
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            streamWorkbook.write(outputStream);



            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
