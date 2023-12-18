package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    @JsonIgnore  //  JSON 변환시 해당하는건 뺀당
    private String password;
    @ToString.Exclude  //  toString() 결과에서 뺀다
    @JsonIgnore
    private String re_password;  //  비번 확인용
    private String name;
    private String email;

    @JsonIgnore
    private LocalDateTime regDate;

    //  OAuth2
    private String provider;    //  어떤 OAuth2 제공자 인지
    private String providerId;  //  provider 내에서의 고유 id 값

}
