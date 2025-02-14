package vn.intech.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.intech.config.AppInfor;

@Controller
@RequestMapping(value = "document-page")
public class DocumentController {
	
	@Autowired
	AppInfor appinfor;
	
	@GetMapping(value="/hdvh")
	public ResponseEntity<InputStreamResource> getTutorials() throws IOException {
		String pathName = appinfor.getTutorialPath();
        String fileName = "\\HDVH.pdf";
        File file = new File(pathName + fileName);
        HttpHeaders headers = new HttpHeaders();      
        headers.add("content-disposition", "inline;filename=" + fileName);
        
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
	}
}
