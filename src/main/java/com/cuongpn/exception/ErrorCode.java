package com.cuongpn.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_NOT_EXISTED(1000,"Email is not existed"),
    EMAIL_ALREADY_EXISTED(1001,"Email is already existed"),
    USERNAME_ALREADY_EXISTED(1002,"Username is already existed"),
    USERNAME_NOT_EXISTED(1003,"Username is not existed"),
    USER_NOT_EXISTED(1004,"User is not existed"),
    ARTICLE_NOT_EXISTED(1005,"Article is not existed"),
    TAG_ALREADY_EXISTED(1006,"Tag is already existed"),
    ROLE_NOT_EXISTED(1007,"Role is not existed"),
    OLD_PASSWORD_NOT_MATCH(1008,"Old password not match"),
    VERIFICATION_TOKEN_INVALID(1009,"Verification token is invalid"),
    COMMENT_NOT_EXISTED(1011,"Comment is not existed"),
    POST_NOT_EXISTED(1010,"Post is not existed"),
    QUESTION_NOT_EXISTED(1011,"Question is not existed"),
    USER_NOT_QUESTION_OWNER(1012,"User is not own question"),
    ANSWER_NOT_EXISTED(1013,"Answer is not existed"),
    ANSWER_NOT_BELONG_QUESTION(1014,"Answer is not belong to question");



    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
    private final int code;
    private final String message;
}
