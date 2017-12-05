package com.kt.util;


import org.apache.commons.lang.StringUtils;

/**
 * Created by Vega Zhou on 2015/11/11.
 */
public class NameUtil {

    private static char[] ENGLISH_ALPHA_BETS = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X',
            'Y','Z'};

    public static class FirstNameAndLastName {
        private String firstName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public static FirstNameAndLastName parseFirstLastName(String fullName) {
        if (isEnglishName(fullName)) {
            return parseEnglishFirstLastName(fullName);
        } else {
            return parseChineseFirstLastName(fullName);
        }
    }

    private static boolean isEnglishName(String fullName) {
        char firstChar = fullName.charAt(0);
        for (char c : ENGLISH_ALPHA_BETS) {
            if (c == firstChar) {
                return true;
            }
        }
        return false;
    }

    private static FirstNameAndLastName parseEnglishFirstLastName(String fullName) {
        assert fullName != null;

        fullName = fullName.trim();
        FirstNameAndLastName result = new FirstNameAndLastName();
        int firstSpace = StringUtils.indexOf(fullName, " ");
        if ( firstSpace < 0) {
            result.setFirstName(fullName);
            result.setLastName(fullName);
        } else {
            result.setFirstName(fullName.substring(0, firstSpace));
            result.setLastName(fullName.substring(firstSpace + 1));
        }
        return result;
    }


    private static FirstNameAndLastName parseChineseFirstLastName(String fullName) {
        assert  fullName != null;

        fullName = fullName.trim();
        FirstNameAndLastName result = new FirstNameAndLastName();
        result.setLastName(fullName.substring(0, 1));
        result.setFirstName(fullName.substring(1));
        return result;
    }
}
