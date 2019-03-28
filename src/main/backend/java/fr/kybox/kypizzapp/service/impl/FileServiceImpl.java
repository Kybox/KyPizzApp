package fr.kybox.kypizzapp.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import fr.kybox.kypizzapp.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class FileServiceImpl implements FileService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final GridFsOperations gridFsOperations;

    @Autowired
    public FileServiceImpl(GridFsOperations gridFsOperations) {
        this.gridFsOperations = gridFsOperations;
    }

    @Override
    public void sendFile(String filename, HttpServletResponse response) {

        log.info("b64 filename = " + filename);

        byte[] decodedBytes = Base64.getDecoder().decode(filename);
        String decodedString = new String(decodedBytes);

        log.info("filename = " + decodedString);

        GridFSFile gridFSFile = gridFsOperations
                .findOne(new Query().addCriteria(Criteria.where("filename").is(decodedString)));

        log.info("grid = " + gridFSFile);

        GridFsResource gridFsResource = gridFsOperations.getResource(gridFSFile);


        try {
            response.setContentType(gridFsResource.getContentType());
            response.setContentLength((int) gridFsResource.contentLength());
            response.setHeader("content-Disposition", "attachement; filename=" + gridFsResource.getFilename());

            IOUtils.copyLarge(gridFsResource.getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
