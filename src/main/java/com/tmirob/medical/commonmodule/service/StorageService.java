/*
 * Copyright (c) 2018. All rights reserved by Taimi Robot.
 * Created by yaocui on 18-10-23 下午5:56.
 */

package com.tmirob.medical.commonmodule.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author seraph(yao.cui@tmirob.com)
 * @date 2018/10/23
 */
@Service
public abstract class StorageService {
    protected final Path rootLocation = Paths.get(".").resolve("resources");

    public Path location;

    public String store(MultipartFile file, String fileName){
        try {
            if(!Files.exists(location)){
                Files.createDirectories(location);
            }
            Path filePath = this.location.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return rootLocation.relativize(filePath).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String filename){
        try {
            Files.delete(this.location.resolve(filename));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            Files.createDirectory(location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

