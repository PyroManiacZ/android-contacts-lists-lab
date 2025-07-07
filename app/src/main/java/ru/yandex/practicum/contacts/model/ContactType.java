package ru.yandex.practicum.contacts.model;

public class ContactType {

    public static final String TELEGRAM = "telegram";
    public static final String WHATS_APP = "whats_app";
    public static final String VIBER = "viber";
    public static final String SIGNAL = "signal";
    public static final String THREEMA = "threema";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    public static String[] getContactTypes() {
        return new String[]{TELEGRAM, WHATS_APP, VIBER, SIGNAL, THREEMA, PHONE, EMAIL};
    }
}
