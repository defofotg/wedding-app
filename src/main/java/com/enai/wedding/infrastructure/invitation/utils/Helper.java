package com.enai.wedding.infrastructure.invitation.utils;

import com.enai.wedding.domain.invitation.model.Guest;
import com.enai.wedding.domain.invitation.model.GuestStatus;
import com.enai.wedding.domain.invitation.model.Invitation;
import com.enai.wedding.domain.invitation.model.InvitationStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Helper {

    public static String TYPE = "text/csv";

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
}
