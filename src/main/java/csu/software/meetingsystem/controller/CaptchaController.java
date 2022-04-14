package csu.software.meetingsystem.controller;


import csu.software.meetingsystem.emailCode.EmailCode;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import static csu.software.meetingsystem.phoneCode.PhoneCode.getPhonemsg;
import static csu.software.meetingsystem.phoneCode.PhoneCode.vcode;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @PostMapping("/{tel}")
    public String getCaptchaByTel(@PathVariable(value = "tel") String tel){
        String code = vcode();
        try {
            getPhonemsg(tel,code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getPhonemsg(tel,code);
        return code;
    }
    @GetMapping("/email/")
    public String getCaptchaByEmail(@RequestParam String name, @RequestParam String email){
        EmailCode sendEmail=new EmailCode();
        sendEmail.setReceiveMailAccount(email);
        String code = vcode();
        sendEmail.setInfo(code);
        try {
            sendEmail.Send(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }


}
