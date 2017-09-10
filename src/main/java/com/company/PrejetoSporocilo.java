package com.company;

public class PrejetoSporocilo {
    private static PrejetoSporocilo ourInstance = new PrejetoSporocilo();

    public static PrejetoSporocilo getInstance() {
        return ourInstance;
    }

    private PrejetoSporocilo() {
    }
}
