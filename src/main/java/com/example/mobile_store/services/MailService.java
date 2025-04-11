//package com.example.mobile_store.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailService {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendMail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("vinhdaumoi2805.com");
////        if (user.getEmail() == null) {
////            user.setEmail("khanhlhfw@gmail.com");
////        }
//        message.setTo(user.getEmail());
//        message.setSubject("Thông báo đặt hàng thành công");
//        message.setText(emailMessage.toString());
//        mailSender.send(message);
//    }
//}
