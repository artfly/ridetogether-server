package io.github.artfly.ridetogether.server.utils;


import io.github.artfly.ridetogether.server.repository.BaseRepository;
import io.github.artfly.ridetogether.server.service.exceptions.NotFoundException;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

public class Utils {
    private static final Random random = new Random();
    private static final int MAX_LEN = 9;
    private static final int MIN_LEN = 4;

    public static File generateFile(String dir) {
        StringBuilder tmp = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            tmp.append(c);
        }

        int length;
        char[] chars = tmp.toString().toCharArray();
        StringBuilder filename = new StringBuilder();
        File file;
        do {
            filename.setLength(0);
            length = random.nextInt(MAX_LEN - MIN_LEN) + MIN_LEN;
            for (int i = 1; i <= length; i++) {
                filename.append(chars[random.nextInt(chars.length - 1)]);
            }
            file = new File(dir, filename.toString());
        } while (file.exists());
        return file;
    }

    public static <T, V extends Serializable> T validate(V id, BaseRepository<T, V> repository) throws RuntimeException {
        return repository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(id.toString()));
    }
}
