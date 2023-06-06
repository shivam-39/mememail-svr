package com.nash.memail.controller;

import com.nash.memail.model.EmailRequest;
import com.nash.memail.model.EmailResponse;
import com.nash.memail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @RequestMapping("/welcome")
    public String welcom(){
        return "hello, this is memail-api";
    }

    //api to send email
    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest){
        System.out.println(emailRequest);
        boolean result = this.emailService.sendEmail(emailRequest.getSubject(), emailRequest.getMessage(), emailRequest.getTo());
        if(result){
            return ResponseEntity.ok(new EmailResponse("Email sent sucessfully......."));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmailResponse("Email not sent.........."));
        }
    }
}
