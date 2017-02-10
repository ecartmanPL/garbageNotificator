package net.siekiera.garbageNotifier.utils;

import com.fasterxml.jackson.core.util.BufferRecycler;
import net.siekiera.garbageNotifier.service.DataGeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * plain text to {"java", "array", "style"} converter
 */
public class DateConverter {
    private static final String FILENAME = "C:\\temp\\dates.txt";
    static Logger log = Logger.getLogger(DateConverter.class.getName());

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String singleLine;
            StringBuilder sb = new StringBuilder();
            int monthNumber = 1;
            sb.append("{");
            while ((singleLine = br.readLine()) != null) {
                singleLine = StringUtils.remove(singleLine, ',');
                singleLine = StringUtils.remove(singleLine, '*');
                String[] strings = StringUtils.split(singleLine);
                strings = Arrays.copyOf(strings, strings.length - 1);
                for (String singleString : strings) {
                    sb.append("\""+singleString+"."+monthNumber+".2017"+"\", ");
                }
                monthNumber++;
            }
            sb.append("}");
            System.out.println(sb.toString());
        } catch (Exception e) {
            log.error("Złapałem wyjątek czytania z pliku: " + e.getMessage());
        }
    }
}
