package com.example.demo.preferences.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Theme {
    private int memberNo;
    private String themeColor;
    private String fontType;
    private String fontSize;
}
