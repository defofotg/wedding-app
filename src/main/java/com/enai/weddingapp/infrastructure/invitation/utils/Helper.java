package com.enai.weddingapp.infrastructure.invitation.utils;

import com.enai.weddingapp.domain.invitation.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Helper {

    public static String TYPE = "text/csv";

    static String[] HEADERs = { "INVITE", "PRESENCE", "ACCOMPAGNANT(E)", "MAIRIE", "EGLISE", "VIN", "SOIREE" };
    static String SHEET = "Invitations";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Invitation> toInvitations(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader()
                             .withIgnoreHeaderCase()
                             .withTrim())
        ) {

            List<Invitation> invitations = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Invitation invitation = new Invitation(
                        UUID.randomUUID(),
                        csvRecord.get("Email"),
                        csvRecord.get("Token"),
                        new Guest(
                                UUID.randomUUID(),
                                csvRecord.get("Firstname"),
                                csvRecord.get("Lastname"),
                                GuestStatus.MAIN
                        ),
                        InvitationStatus.PENDING);

                invitations.add(invitation);
            }

            return invitations;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream invitationsToFile(List<Invitation> invitations) {
        if (invitations.isEmpty()) {
            return null;
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Invitation invitation : invitations) {
                Row row = sheet.createRow(rowIdx++);

                Guest mainGuest = invitation.getGuest(invitation, GuestStatus.MAIN);
                Guest attendant = invitation.getGuest(invitation, GuestStatus.ATTENDANT);

                row.createCell(0).setCellValue(mainGuest.getFullname());
                row.createCell(1).setCellValue(invitation.getStatus().name());
                row.createCell(2).setCellValue(Objects.nonNull(attendant) ? attendant.getFullname() : "NON ACCOMPAGNE");
                row.createCell(3).setCellValue(eventConfirmed(invitation, Event.MAIRIE)? "OUI": "NON");
                row.createCell(4).setCellValue(eventConfirmed(invitation, Event.EGLISE)? "OUI": "NON");
                row.createCell(5).setCellValue(eventConfirmed(invitation, Event.VIN)? "OUI": "NON");
                row.createCell(6).setCellValue(eventConfirmed(invitation, Event.SOIREE)? "OUI": "NON");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    private static boolean eventConfirmed(Invitation invitation, Event event) {
        return invitation.getEvents()
                .stream()
                .anyMatch(confirmedEvent -> event == confirmedEvent);
    }
}
