package com.enai.weddingapp.application.fileFeature.rest;

import com.enai.weddingapp.application.fileFeature.dto.FileUploadResponse;
import com.enai.weddingapp.application.global.exception.EmptyFileException;
import com.enai.weddingapp.infrastructure.invitation.utils.Helper;
import com.enai.weddingapp.domain.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    InvitationService invitationService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (Helper.hasCSVFormat(file)) {
            try {
                invitationService.createInvitations(file.getInputStream());

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(reponse(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(reponse(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(reponse(message));

    }

    @GetMapping("/export")
    public ResponseEntity<Resource> downloadFile() {
        String filename = "Invitations.xlsx";
        ByteArrayInputStream fileContent = Helper.invitationsToFile(invitationService.retrieveInvitations());

        if (Objects.isNull(fileContent)) {
            throw new EmptyFileException("No invitations to export! The file is empty.");
        }

        InputStreamResource file = new InputStreamResource(fileContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    private FileUploadResponse reponse(String message) {
        return FileUploadResponse.builder()
                .message(message)
                .build();
    }
}
