package com.example.demo.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class RegexPattern {

    
    // 패스워드 match
    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}])[A-Za-z[0-9]!@#$%^&*?,./\\\\<>|_-[+]=\\`~\\(\\)\\[\\]\\{\\}]{8,20}$"; // 영문, 숫자, 특수문자

    public static final String pattern2 = "^[A-Za-z[0-9]]{10,20}$"; // 영문, 숫자
   
    public static final String pattern3 = "^(?=.*[0-9])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]='\\`\";:~\\(\\)\\[\\]\\{\\}])[[0-9]!@#$%^&*?,./\\\\<>|_-[+]='\";:\\`~\\(\\)\\[\\]\\{\\}]{10,20}$"; // 숫자, 특수문자
   
    public static final String pattern4 = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*?,./\\\\<>|_-[+]='\\`\";:~\\(\\)\\[\\]\\{\\}])[[A-Za-z]!@#$%^&*?,./\\\\<>|_-[+]='\";:\\`~\\(\\)\\[\\]\\{\\}]{10,20}$"; // 문자, 특수문자
   
    public static final String pattern5 = "(\\w)\\1\\1\\1"; 

    public static final String pEmail = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";



    
    public Matcher m;
    public boolean passwordChk(String newPassword, String oldPassword, String userId) {

        boolean check = false;
        m = Pattern.compile(pattern1).matcher(newPassword);
        if(m.find()) {
            check = true;
        }
        
        m = Pattern.compile(pattern2).matcher(newPassword);
        if(m.find()) {
            check = true;
        }
        
        m = Pattern.compile(pattern3).matcher(newPassword);
        if(m.find()) {
            check = true;
        }
        
        m = Pattern.compile(pattern4).matcher(newPassword);
        if(m.find()) {
            check = true;
        }
        
        if(check) {
            if(samePwd(newPassword)) {
                return false;
            }
         
            if(continuousPwd(newPassword)) {
                return false;
            }
         
            if(newPassword.equals(oldPassword)) {
                return false;
            }
         
            if(sameId(newPassword, userId)) {
                return false;
            }
        }
        
        return true;
    }

    public boolean samePwd(String pwd) {
        Matcher match = Pattern.compile(pattern5).matcher(pwd);
        
        return match.find() ? true : false;
    }

    public boolean continuousPwd(String pwd) {
        int o = 0;
        int d = 0;
        int p = 0;
        int n = 0;  
        int limit = 3;
        
        for(int i=0; i<pwd.length(); i++) {
         char tempVal = pwd.charAt(i);
         if(i > 0 && (p = o - tempVal) > -2 && (n = p == d ? n + 1 :0) > limit -3) {
          return true;
         }
         d = p;
         o = tempVal;
        }
        return false;
    }

    public boolean sameId(String pwd, String id) {
        for(int i=0; i<pwd.length()-3; i++) {
            if(id.contains(pwd.substring(i, i+3))) {
                return true;
            }
        }
        
        return false;
    }

    public boolean isValidEmail(String email) {
        Matcher m = Pattern.compile(pEmail).matcher(email);
        if(m.matches()) {
            return true;
        }

        return false;
    }
}