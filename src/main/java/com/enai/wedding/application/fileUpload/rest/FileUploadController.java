package com.enai.wedding.application.fileUpload.rest;

import com.enai.wedding.application.fileUpload.dto.FileUploadResponse;
import com.enai.wedding.infrastructure.invitation.utils.Helper;
import com.enai.wedding.domain.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    InvitationService invitationService;

    @PostMapping
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

    private FileUploadResponse reponse(String message) {
        return FileUploadResponse.builder()
                .message(message)
                .build();
    }
}
