package org.example.translator.impl;

import org.example.configuration.Translator;
import org.example.translator.TranslationService;
import org.springframework.stereotype.Service;


@Service
public class TranslationServiceImpl implements TranslationService {

    private final Translator translator;

    public TranslationServiceImpl(Translator translator) {
        this.translator = translator;
    }

    @Override
    public String getTranslation(String string) {
        return translator.toLocale(string);
    }
}
