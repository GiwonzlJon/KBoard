package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("suppports(" + clazz.getName() + ")");
        Boolean result = Post.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("validate()  호출");
        Post post = (Post) target;

        // 이젠 필요없당 로그인 할때
//        String  user = post.getUser();
//        if (user == null || user.trim().isEmpty()){
//            //  rejectValue(field, errorCode) <- 유효성 검증 에러 추가
//            errors.rejectValue("user", "작성자 은 필수다 닝겐");
//        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "글 제목은 필수다 닝겐!");
    }
}
