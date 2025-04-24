package com.email.writer.controller;

import com.email.writer.model.EmailBody;
import com.email.writer.service.EmailGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/mail")
public class EmailController {
    @Autowired
    private EmailGeneratorService service;

    @PostMapping("/generate")
    public ResponseEntity<String> generateMail(@RequestBody EmailBody email){
        System.out.println(email);
        String respone = service.generateEmailReply(email);
        System.out.println("------------------");
        System.out.println(respone);
        return ResponseEntity.ok(respone);
    }
}
