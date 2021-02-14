package com.goltsov.controller;


import com.goltsov.model.UserLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class FileUploadController {

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) {
        model.addAttribute("upload");
        return "greeting";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        UserLog userLog = new UserLog();
        return userLog.uploadFile(file);
    }

}