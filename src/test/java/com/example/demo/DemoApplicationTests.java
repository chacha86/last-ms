package com.example.demo;

import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    SpotRepository spotRepository;

    @Test
    void contextLoads() {
        try {
            String excelFilePath = "data.xlsx";
            ZipSecureFile.setMinInflateRatio(0.0001);
            ClassPathResource resource = new ClassPathResource(excelFilePath);
            Workbook workbook = WorkbookFactory.create(resource.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            List<Spot> spotList = new ArrayList<>();
            int count = 0;
            for (Row row : sheet) {

                if(count == 0) {
                    count++;
                    continue;
                }

                Spot spot = makeSpot(row);
                if(spot == null) {
                    continue;
                }

                spotList.add(spot);
                count++;
            }

            spotRepository.saveAll(spotList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Spot makeSpot(Row row) {
        Spot spot = new Spot();
        for(int i = 2; i < 7; i++) {
            Cell cell = row.getCell(i);
            if(cell == null) {
                return null;
            }

            if(cell.getCellType() == CellType.NUMERIC) {
                cell.setCellType(CellType.STRING);
            }
            spot.setData(i, cell.getStringCellValue());
        }

        return spot;
    }
}
