package fr.kybox.kypizzapp.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class Image {

    @NotNull
    private String file;

    public Image() {
    }

    public Image(@NotNull String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
