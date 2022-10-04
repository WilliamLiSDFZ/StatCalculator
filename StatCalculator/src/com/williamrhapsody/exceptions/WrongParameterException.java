package com.williamrhapsody.exceptions;

/**
 * @author WilliamLi
 * @version 1.0
 * @date 2021/12/15 19:01
 */
public class WrongParameterException extends RuntimeException{

    public WrongParameterException(){}

    public WrongParameterException(String message){
        super(message);
    }
}
