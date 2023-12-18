package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Long id; // PK

    @ToString.Exclude
    private User user;  // 댓글 작성자 (FK)




    //  어느 글의 댓글()FK
    // 여러 방식으로 할수 있따
//    private Post post;
    @JsonIgnore  //  JSON 변환시 제외
    private Long post_id;


    private String content; //  댓글내용

    // java.time.* 객체 변환을 위한 annotation 들
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("regdate")
    private LocalDateTime regDate;
}
