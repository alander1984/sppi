package com.egartech.sppi.configuration;

import com.egartech.sppi.model.Process;
import com.egartech.sppi.model.Step;
import com.egartech.sppi.repo.ProcessRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = "transactionManager")
public class ProcessUtils {

    @Autowired
    ProcessRepository processRepository;
    
    @Autowired
    private KieContainer kieContainer;

    private static final String TEST_PASSED = "Тест пройден";
    private static final String TEST_NOT_PASSED = "Тест не пройден";
    private static final String TEST_PAUSED = "Тест не завершён";

    private static final int[] FIRST_SEQUENCE_OF_WEIGHTING_COEFFICIENTS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1 };
    private static final int[] SECOND_SEQUENCE_OF_WEIGHTING_COEFFICIENTS = { 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3 };
    private static final int ELEVEN = 11;
    private static final int DIGITS_COUNT_OF_UNIQUE_NUMBER = 4;
    private static final Set<Integer> ONE_DIGIT_NUMBERS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

    public File getTestReport(Long processId) throws IOException {
        Process process = processRepository.findOne(processId);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row headerRow = sheet.createRow(0);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue(process.getProductName());
        
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue(process.getUti());
        if (process.getPassed() != null) {
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-dd-MM"));
            Cell cell2 = headerRow.createCell(2);
            cell2.setCellValue(process.getDateEnd());
            cell2.setCellStyle(cellStyle);
        }
        sheet.autoSizeColumn(1);
        
        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue(process.getPassed() == null ? TEST_PAUSED : process.getPassed() ? TEST_PASSED : TEST_NOT_PASSED);

        for (int i = 0; i < process.getProcessSteps().size(); i++) {
            Row row = sheet.createRow(i + 2);
            Cell cellQuestion = row.createCell(0);
            String cleanedQuestionText = cleanMarkup(process.getProcessSteps().get(i).getQuestion().getContent());
            cellQuestion.setCellValue(cleanedQuestionText);
            Cell cellAnswer = row.createCell(1);
            String cleanedAnswerText = cleanMarkup(process.getProcessSteps().get(i).getAnswerText());
            cellAnswer.setCellValue(cleanedAnswerText);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        
        File file = new File ("testReport-" + process.getUti() + ".xlsx");
        FileOutputStream fos = new FileOutputStream(file);
        bos.writeTo(fos);
        bos.close();
        fos.close();
        
        return file;
    }

    private String cleanMarkup(String text) {
        return text.replaceAll("</?[a-z]+>|&[a-z]+;", "");
    }

    public String getUTI(boolean isPassed, Date dateEnd) {
        int[] utiDigitsWithoutControlDigit = new int[11];
        utiDigitsWithoutControlDigit[0] = isPassed ? 1 : 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        String[] dayMonthYear = simpleDateFormat.format(dateEnd).split("-");

        utiDigitsWithoutControlDigit[1] = Integer.parseInt(dayMonthYear[0].substring(0, 1));
        utiDigitsWithoutControlDigit[2] = Integer.parseInt(dayMonthYear[0].substring(1, 2));

        utiDigitsWithoutControlDigit[3] = Integer.parseInt(dayMonthYear[1].substring(0, 1));
        utiDigitsWithoutControlDigit[4] = Integer.parseInt(dayMonthYear[1].substring(1, 2));

        utiDigitsWithoutControlDigit[5] = Integer.parseInt(dayMonthYear[2].substring(0, 1));
        utiDigitsWithoutControlDigit[6] = Integer.parseInt(dayMonthYear[2].substring(1, 2));

        int[] uniqueNumberDuringDay = getUniqueNumberDuringDay(dateEnd);
        utiDigitsWithoutControlDigit[7] = uniqueNumberDuringDay[0];
        utiDigitsWithoutControlDigit[8] = uniqueNumberDuringDay[1];
        utiDigitsWithoutControlDigit[9] = uniqueNumberDuringDay[2];
        utiDigitsWithoutControlDigit[10] = uniqueNumberDuringDay[3];

        int controlDigit = getControlDigit(utiDigitsWithoutControlDigit);
        StringBuilder uti = new StringBuilder();

        for (int utiDigit : utiDigitsWithoutControlDigit) {
            uti.append(utiDigit);
        }
        uti.append(controlDigit);
        return uti.toString();
    }

    private int[] getUniqueNumberDuringDay(Date dateEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateEnd);
        int processCount = processRepository.findProcessCountForCurrentDay(calendar.get(Calendar.DAY_OF_MONTH));
        StringBuilder reversedProcessCountStr = new StringBuilder(String.valueOf(processCount)).reverse();
        int digitsNumberOfProcessCount = reversedProcessCountStr.length();
        int necessaryNumberOfZeros = digitsNumberOfProcessCount == DIGITS_COUNT_OF_UNIQUE_NUMBER ? 0 : DIGITS_COUNT_OF_UNIQUE_NUMBER - digitsNumberOfProcessCount;
        for (int i = 0; i < necessaryNumberOfZeros; i++) {
            reversedProcessCountStr.append("0");
        }
        String uniqueNumberStr = reversedProcessCountStr.reverse().toString();
        int[] uniqueNumberDuringDay = new int[DIGITS_COUNT_OF_UNIQUE_NUMBER];

        uniqueNumberDuringDay[0] = Integer.parseInt(uniqueNumberStr.substring(0, 1));
        uniqueNumberDuringDay[1] = Integer.parseInt(uniqueNumberStr.substring(1, 2));
        uniqueNumberDuringDay[2] = Integer.parseInt(uniqueNumberStr.substring(2, 3));
        uniqueNumberDuringDay[3] = Integer.parseInt(uniqueNumberStr.substring(3, 4));

        return uniqueNumberDuringDay;
    }

    private int getControlDigit(int[] utiDigitsWithoutControlDigit) {
        int sum = getSumOfMultiplications(utiDigitsWithoutControlDigit, FIRST_SEQUENCE_OF_WEIGHTING_COEFFICIENTS);
        int controlDigit = sum % ELEVEN;
        if (ONE_DIGIT_NUMBERS.contains(controlDigit)) {
            return controlDigit;
        } else {
            sum = getSumOfMultiplications(utiDigitsWithoutControlDigit, SECOND_SEQUENCE_OF_WEIGHTING_COEFFICIENTS);
            controlDigit = sum % ELEVEN;
            if (ONE_DIGIT_NUMBERS.contains(controlDigit)) {
                return controlDigit;
            } else {
                return 0;
            }
        }
    }

    private int getSumOfMultiplications(int[] utiDigitsWithoutControlDigit, int[] sequenceOfWeightingCoefficients) {
        int sum = 0;
        for (int i = 0; i < utiDigitsWithoutControlDigit.length; i++) {
            sum += utiDigitsWithoutControlDigit[i] * sequenceOfWeightingCoefficients[i];
        }
        return sum;
    }
    
    public String getProcessColorRuleResult(Process process) {
         StatelessKieSession kieSession = kieContainer.newStatelessKieSession();
         kieSession.execute(process);
         System.out.println(process);
         return process.getColor();
    }
}
