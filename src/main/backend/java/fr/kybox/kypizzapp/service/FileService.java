package fr.kybox.kypizzapp.service;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    void sendFile(String filename, HttpServletResponse response);
}
