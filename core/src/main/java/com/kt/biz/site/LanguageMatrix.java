package com.kt.biz.site;



import com.kt.biz.types.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class LanguageMatrix {
    public static List<Language> ALL_LANGUAGES = new ArrayList<>();

    static {
        ALL_LANGUAGES.add(Language.SIMPLIFIED_CHINESE);
        ALL_LANGUAGES.add(Language.TRADITIONAL_CHINESE);
        ALL_LANGUAGES.add(Language.ENGLISH);
        ALL_LANGUAGES.add(Language.JAPANESE);
        ALL_LANGUAGES.add(Language.KOREAN);
        ALL_LANGUAGES.add(Language.FRENCH);
        ALL_LANGUAGES.add(Language.GERMAN);
        ALL_LANGUAGES.add(Language.ITALIAN);
        ALL_LANGUAGES.add(Language.SPANISH);
        ALL_LANGUAGES.add(Language.SPANISH_CASTILLA);
        ALL_LANGUAGES.add(Language.SWEDISH);
        ALL_LANGUAGES.add(Language.HOLLAND);
        ALL_LANGUAGES.add(Language.PORTUGUESE);
        ALL_LANGUAGES.add(Language.RUSSIAN);
        ALL_LANGUAGES.add(Language.TURKEY);
        ALL_LANGUAGES.add(Language.DANISH);
    }


    private int status = 0;

    private Language primaryLanguage = Language.SIMPLIFIED_CHINESE;


    public void enable(Language language) {
        if (language != null) {
            status = status | language.getBit();
        }
    }

    public void enable(Language... languages) {
        for (Language language : languages) {
            enable(language);
        }
    }

    public boolean isEnabled(Language language) {
        return (status & language.getBit()) != 0;
    }

    public List<Language> getEnabledLanguages() {
        List<Language> results = new ArrayList<>();
        for (Language language : ALL_LANGUAGES) {
            if (isEnabled(language)) {
                results.add(language);
            }
        }
        return results;
    }

    public List<Language> getEnabledAdditionalLanguages() {
        List<Language> results = new ArrayList<>();
        for (Language language : ALL_LANGUAGES) {
            if (isEnabled(language) && getPrimaryLanguage() != language) {
                results.add(language);
            }
        }
        return results;
    }

    public Language getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(Language primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public LanguageMatrix deepClone() {
        LanguageMatrix clone = new LanguageMatrix();
        clone.status = this.status;
        clone.primaryLanguage = this.primaryLanguage;
        return clone;
    }

    public static void main(String args[]) {
        LanguageMatrix matrix = new LanguageMatrix();
        matrix.enable(Language.DANISH);
        matrix.enable(Language.ENGLISH);
        matrix.setPrimaryLanguage(Language.ENGLISH);


        for (Language language : ALL_LANGUAGES) {
            System.out.print(language.getValue() + " ");
            System.out.println(matrix.isEnabled(language));
        }

        List<Language> l = matrix.getEnabledAdditionalLanguages();
        List<Language> a = matrix.getEnabledLanguages();

        return;
    }
}
