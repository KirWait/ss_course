package org.example.resource;

import java.util.ListResourceBundle;

public class Resources_ru extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"test", "тест3"}

            };
    public Object[][] getContents(){
        return contents;
    }
}
