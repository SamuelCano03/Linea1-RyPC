package com.tren.linea1_service.controller;

import com.tren.linea1_service.model.dto.SignupFormDTO;
import com.tren.linea1_service.model.dto.UserProfileDTO;
import com.tren.linea1_service.repository.UserRepository;
import com.tren.linea1_service.service.StorageService;
import com.tren.linea1_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final StorageService storageService;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<UserProfileDTO> signup(@RequestBody @Validated SignupFormDTO signupFormDTO, @RequestParam("file")MultipartFile dni){
        String path = storageService.store(dni);
        UserProfileDTO sign = userService.signup(signupFormDTO, path);
        return ResponseEntity.ok(sign);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getResource(@PathVariable String filename) throws IOException {
        Resource resource = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("file") MultipartFile dni) {
        String path = storageService.store(dni);
        String response = userService.verify(path);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/personalinfo")
    public ResponseEntity<UserProfileDTO> updatePersonalInfo(@RequestBody UserProfileDTO dto) {
        UserProfileDTO responseDTO = userService.updatePersonalInfo(dto);
        return ResponseEntity.ok(responseDTO);
    }

}
