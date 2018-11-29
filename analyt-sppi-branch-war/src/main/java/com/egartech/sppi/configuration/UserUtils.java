package com.egartech.sppi.configuration;

import com.egartech.sppi.model.User;
import com.egartech.sppi.repo.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "transactionManager")
public class UserUtils {

    @Autowired
    UserRepository userRepository;

    public byte[] getSelfRegisteredUsers() throws IOException {
        List<User> users = userRepository.findByIsSelfRegistered(true);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        Cell cell0 = titleRow.createCell(0);
        cell0.setCellValue("Самостоятельно зарегистрировавшиеся пользователи");

        Row headerRow = sheet.createRow(2);
        Cell cell1 = headerRow.createCell(0);
        cell1.setCellValue("ФИО");
        Cell cell2 = headerRow.createCell(1);
        cell2.setCellValue("Табельный номер");
        Cell cell3 = headerRow.createCell(2);
        cell3.setCellValue("Логин");

        if (users.size() == 0) {
            Row row = sheet.createRow(3);
            row.createCell(0).setCellValue("Пользователи не регистрировались.");
        } else {
            for (int i = 0; i < users.size(); i++) {
                Row row = sheet.createRow(i + 3);
                Cell cellUserFIO = row.createCell(0);
                cellUserFIO.setCellValue(users.get(i).getUserFIO());
                Cell cellUserPersonnelNum = row.createCell(1);
                cellUserPersonnelNum.setCellValue(users.get(i).getUserPersonnelNum());
                Cell cellUsername = row.createCell(2);
                cellUsername.setCellValue(users.get(i).getUsername());
            }
        }
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();

        byte[] selfRegisteredUsersBytes = bos.toByteArray();
        bos.close();

        return selfRegisteredUsersBytes;
    }
}
