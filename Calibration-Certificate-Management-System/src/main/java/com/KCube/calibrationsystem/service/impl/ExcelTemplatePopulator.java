// Refactored to remove all .setCellFormula() and rely only on values; formulas are preserved in the Excel template
package com.KCube.calibrationsystem.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.KCube.calibrationsystem.enums.CalibrationType;
import com.KCube.calibrationsystem.model.*;
import com.KCube.calibrationsystem.model.helper.IndentationReading;
import com.KCube.calibrationsystem.model.helper.ObservedReading;

@Component
public class ExcelTemplatePopulator {

    private static final String CERTIFICATE_FOLDER = "certificates";

    public String populateExcelTemplate(
            CalibrationCertificate cert,
            ClientMachines cm,
            ServiceRequest sr,
            Company company,
            RawData rawData,
            List<Scales> selectedScales,
            Map<Long, List<TestBlocks>> scaleTestBlockMap,
            List<ObservedReading> readings,
            LocalDate nextCalibrationDate,
            CalibrationType calibrationType) throws IOException {

        String templateFileName = getTemplateName(calibrationType, cm);
        ClassPathResource templateResource = new ClassPathResource("templates/" + templateFileName);

        try (InputStream templateStream = templateResource.getInputStream();
             Workbook workbook = WorkbookFactory.create(templateStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Header Info
            getOrCreateCell(getOrCreateRow(sheet, 17), 5).setCellValue(cert.getCertificateNumber());
            getOrCreateCell(getOrCreateRow(sheet, 18), 5).setCellValue(cert.getIssueDate().toString());
            getOrCreateCell(getOrCreateRow(sheet, 18), 11).setCellValue(cert.getIssueDate().toString());
            getOrCreateCell(getOrCreateRow(sheet, 22), 14).setCellValue(cert.getIssueDate().toString());
            getOrCreateCell(getOrCreateRow(sheet, 23), 14).setCellValue(nextCalibrationDate.toString());

            getOrCreateCell(getOrCreateRow(sheet, 21), 2).setCellValue("M/s " + company.getName() + ", " + company.getAddress());
            getOrCreateCell(getOrCreateRow(sheet, 21), 8).setCellValue(cm.getMake());
            getOrCreateCell(getOrCreateRow(sheet, 22), 8).setCellValue(cm.getModel());
            getOrCreateCell(getOrCreateRow(sheet, 23), 8).setCellValue(cm.getSerialNumber());
            getOrCreateCell(getOrCreateRow(sheet, 24), 8).setCellValue(cm.getIdentificationNumber());
            getOrCreateCell(getOrCreateRow(sheet, 25), 8).setCellValue("AT SITE");
            getOrCreateCell(getOrCreateRow(sheet, 26), 8).setCellValue(rawData.getTemperatureDetail() + " °C");
            getOrCreateCell(getOrCreateRow(sheet, 25), 14).setCellValue(sr.getEngineer().getName());
            getOrCreateCell(getOrCreateRow(sheet, 26), 14).setCellValue("OK");

            // Replace static machine name in Sheet1!J14 with actual machine name
            Row titleRow = getOrCreateRow(sheet, 13);
            Cell titleCell = getOrCreateCell(titleRow, 9);
            String titleValue = titleCell.getStringCellValue();
            if (titleValue.contains("ROCKWELL SUPERFICIAL HARDNESS TESTER")) {
                titleCell.setCellValue(titleValue.replace("ROCKWELL SUPERFICIAL HARDNESS TESTER", cm.getMachines().getName()));
            }

            // Master Block Section
            int blockStartRow = 31, blockCounter = 1;
            Row blockTemplate = sheet.getRow(blockStartRow);
            for (Map.Entry<Long, List<TestBlocks>> entry : scaleTestBlockMap.entrySet()) {
                for (TestBlocks tb : entry.getValue()) {
                    Row blockRow = getOrCreateRow(sheet, blockStartRow++);
                    cloneRowStyle(blockTemplate, blockRow);
                    getOrCreateCell(blockRow, 2).setCellValue(blockCounter++);
                    getOrCreateCell(blockRow, 3).setCellValue(tb.getName() + " " + tb.getRangeValue() + " " + tb.getIdentificationNumber());
                    getOrCreateCell(blockRow, 7).setCellValue(cert.getCertificateNumber());
                    getOrCreateCell(blockRow, 12).setCellValue(tb.getCalibratedBy());
                    getOrCreateCell(blockRow, 15).setCellValue(tb.getValidUpto().toString());
                }
            }

            // Calibration Results (start from row index 55)
            int resultStartRow = 55;
            Row resultTemplate = sheet.getRow(resultStartRow - 1);
            for (int i = 0; i < readings.size(); i++) {
                ObservedReading r = readings.get(i);
                Row resultRow = getOrCreateRow(sheet, resultStartRow + i);
                cloneRowStyle(resultTemplate, resultRow);

                Scales scale = selectedScales.stream().filter(s -> s.getId().equals(r.getScaleId())).findFirst().orElse(null);
                TestBlocks block = scaleTestBlockMap.get(r.getScaleId()).stream().filter(b -> b.getId().equals(r.getTestBlockId())).findFirst().orElse(null);

                getOrCreateCell(resultRow, 2).setCellValue(i + 1);
                getOrCreateCell(resultRow, 3).setCellValue(scale.getLoadCapacity());
                getOrCreateCell(resultRow, 4).setCellValue(scale.getScaleName());
                getOrCreateCell(resultRow, 5).setCellValue(block.getRangeValue());

                for (int j = 0; j < 5; j++) {
                    if (j < r.getReadings().size() && r.getReadings().get(j) != null)
                        getOrCreateCell(resultRow, 6 + j).setCellValue(r.getReadings().get(j));
                    else
                        getOrCreateCell(resultRow, 6 + j).setBlank();
                }

                getOrCreateCell(resultRow, 12).setCellValue("Error");
                getOrCreateCell(resultRow, 13).setCellValue(block.getAllowedError());
                getOrCreateCell(resultRow, 14).setCellValue("Repeat.");
                getOrCreateCell(resultRow, 16).setCellValue(scale.getUncertainty());
            }

         // ✅ Update uncertainty block scale names in existing sheet
            Sheet uncertSheet = workbook.getSheet("uncert HR 15N");
            if (uncertSheet != null) {
                for (int i = 0; i < readings.size(); i++) {
                    ObservedReading r = readings.get(i);
                    Scales scale = selectedScales.stream()
                            .filter(s -> s.getId().equals(r.getScaleId()))
                            .findFirst().orElse(null);

                    if (scale != null) {
                        int colOffset = 2 + i * 10; // B=2, L=12, etc.
                        Row rowTop = uncertSheet.getRow(5); // Row index 5 = Excel row 6 (scale title)
                        Row rowMid = uncertSheet.getRow(10); // Excel row 11 (Reported Uncertainty)
                        Row rowFinal = uncertSheet.getRow(52); // Excel row 53 (Ucx section)

                        if (rowTop != null) {
                            Cell scaleCell = rowTop.getCell(colOffset);
                            if (scaleCell != null) scaleCell.setCellValue(scale.getScaleName());
                        }

                        if (rowMid != null) {
                            Cell scaleCell = rowMid.getCell(colOffset + 7); // Uncertainty label (e.g., HR15N)
                            if (scaleCell != null && scaleCell.getCellType() == CellType.STRING) {
                                scaleCell.setCellValue(scale.getScaleName());
                            }
                        }

                        if (rowFinal != null) {
                            Cell scaleCell = rowFinal.getCell(colOffset + 1);
                            if (scaleCell != null && scaleCell.getCellType() == CellType.STRING) {
                                scaleCell.setCellValue(scale.getScaleName());
                            }
                            Cell finalLabel = rowFinal.getCell(colOffset + 3);
                            if (finalLabel != null && finalLabel.getCellType() == CellType.STRING) {
                                finalLabel.setCellValue(scale.getScaleName());
                            }
                        }
                    }
                }
            }

            
            workbook.setForceFormulaRecalculation(true);
            File file = new File(CERTIFICATE_FOLDER, "CalibrationCert_" + cert.getId() + ".xlsx");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            try (FileOutputStream out = new FileOutputStream(file)) {
                workbook.write(out);
            }
            return file.getAbsolutePath();
        }
    }

    private Row getOrCreateRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        return (row != null) ? row : sheet.createRow(rowIndex);
    }

    private Cell getOrCreateCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        return (cell != null) ? cell : row.createCell(colIndex);
    }

    private void cloneRowStyle(Row template, Row target) {
        for (int i = 0; i < template.getLastCellNum(); i++) {
            Cell tCell = template.getCell(i);
            if (tCell == null) continue;
            Cell cell = getOrCreateCell(target, i);
            cell.setCellStyle(tCell.getCellStyle());
        }
    }

    private String getTemplateName(CalibrationType type, ClientMachines cm) {
        String name = cm.getMachines().getName().toLowerCase();
        if (name.contains("rockwell")) return "RHT HARD MULTIPLE SCALES.xlsx";
        if (name.contains("brinell")) return "BHT HARD.xlsx";
        if (name.contains("microvickers")) return "MVHT.xlsx";
        if (name.contains("vickers")) return "VHT.xlsx";
        return switch (type) {
            case HARDNESS -> "RHT HARD.xlsx";
            case FORCE -> "FCT FORCE.xlsx";
            case HARDNESS_FORCE -> "COMBO.xlsx";
        };
    }

    private TestBlocks getTestBlockById(Long id, Map<Long, List<TestBlocks>> scaleTestBlockMap) {
        return scaleTestBlockMap.values().stream()
                .flatMap(List::stream)
                .filter(tb -> tb.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}