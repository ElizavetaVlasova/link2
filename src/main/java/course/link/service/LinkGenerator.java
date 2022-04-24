package course.link.service;

import org.springframework.stereotype.Service;

@Service
public class LinkGenerator {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String getRandomValue() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(ALPHABET.charAt((int) Math.floor(Math.random() * ALPHABET.length())));
        }
        return String.join("", "/l/", result.toString());
    }
}
