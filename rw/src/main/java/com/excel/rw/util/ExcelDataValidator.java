package com.excel.rw.util;

public class ExcelDataValidator {
    public static boolean stringOnlyAlphabet(String str)
    {
        return ((!str.equals(""))
                && (str != null)
                && (str.matches("^[a-zA-Z ]*$")));
    }

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("^[0-9!@#$&()`.+,\\\"]*$");
    }


    public static void main(String[] args) {

        String str="ASDFVGVG   jsjhsh"; boolean p=stringOnlyAlphabet(str);
        System.out.println("sjhj " +p);

        String str3="ashASDFh234"; boolean d=isAlphaNumeric(str3);
        System.out.println("sjhj " +d);

        String no="123!4567"; boolean b=isNumeric(no); System.out.println(b);

    }
}
