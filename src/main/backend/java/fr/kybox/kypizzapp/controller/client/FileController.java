package fr.kybox.kypizzapp.controller.client;

import fr.kybox.kypizzapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/file/{filename}")
    public void sendFile(@PathVariable String filename, HttpServletResponse response){

        fileService.sendFile(filename, response);
    }
}
