package com.marcomarchionni.ibportfolio.controllers.util;

import org.springframework.web.multipart.MultipartFile;

public class Util {

    public static boolean fileIsNotXML(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName == null || !fileName.toLowerCase().endsWith(".xml");
    }
}
