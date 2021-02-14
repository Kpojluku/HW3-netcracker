package com.goltsov.exeptions;

import java.io.IOException;

public class FileBadFormatException extends IOException {
    public FileBadFormatException(String a) {
        super(a);
    }
}
