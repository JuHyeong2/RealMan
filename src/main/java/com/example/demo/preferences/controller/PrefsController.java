package com.example.demo.preferences.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/prefs")
public class PrefsController {


    @GetMapping({"","/", "/ui-theme"})
    public String preferences() {
        return "preferences/ui-theme";
    }
}
