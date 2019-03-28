package fr.kybox.kypizzapp.utils.file;

import java.util.Optional;

public class FileExtension {

    public static Optional<String> get(String filename){

        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring((filename.lastIndexOf("."))));
    }
}
