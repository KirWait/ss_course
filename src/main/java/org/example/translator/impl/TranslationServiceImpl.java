package org.example.translator.impl;

import org.example.translator.TranslationService;
import org.springframework.stereotype.Service;

import static org.example.configuration.Translator.toLocale;

@Service
public class TranslationServiceImpl implements TranslationService {
    @Override
    public String getTranslation(String string) {
        return toLocale(string);
    }
}
