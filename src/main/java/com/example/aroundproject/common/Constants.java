package com.example.aroundproject.common;
public class Constants {

    public enum ExceptionClass {

        PRODUCT("Product"), SIGN("Sign");

        private String exceptionClass;

        ExceptionClass(String exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public String getExceptionClass() {
            return exceptionClass;
        }

        @Override
        public String toString() {
            return getExceptionClass() + " Exception. ";
            //Product Exception으로 출력됨
        }

    }

}