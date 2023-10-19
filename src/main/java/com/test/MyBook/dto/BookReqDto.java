package com.test.MyBook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor //기본생성자
@AllArgsConstructor //오버로딩된 생성자
@Getter  //getter
@Setter  //setter
@ToString //toString
@Builder
public class BookReqDto {
    private Long id;
    @NotEmpty(message = "title은 필수 입력항목입니다.")
    private String title;
    @NotEmpty(message = "필수 입력항목입니다.")
    private String author;
    @NotBlank(message = "필수 입력항목입니다.")
    private String isbn;
    private String genre;
}
