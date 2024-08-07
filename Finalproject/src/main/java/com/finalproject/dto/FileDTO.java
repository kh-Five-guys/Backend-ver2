package com.finalproject.dto;

import java.io.File;
import java.io.IOException;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Alias("boardFile")
public class FileDTO {
    private String path;
    private String fileName;
    private String type;
    private int bno;
    private int fno;

    public FileDTO() {
    }

    public FileDTO(File file, int bno, int fno) {
        this.bno = bno;
        this.fno = fno;
        this.path = file.getAbsolutePath();
        this.fileName = file.getName();
        setTypeFromFileName(file.getName());
    }

    public FileDTO(MultipartFile multipartFile, int bno, int fno) throws IOException {
        this.bno = bno;
        this.fno = fno;
        this.path = "c:\\fileupload\\" + multipartFile.getOriginalFilename();
        this.fileName = multipartFile.getOriginalFilename();
        setTypeFromFileName(multipartFile.getOriginalFilename());
    }

    private void setTypeFromFileName(String fileName) {
        String postfix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (postfix) {
            case "png":
            case "jpg":
            case "gif":
            case "bmp":
                this.type = "image";
                break;
            case "mp4":
                this.type = "video";
                break;
            default:
                this.type = "normal";
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        setTypeFromFileName(new File(path).getName());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public int getFno() {
        return fno;
    }

    public void setFno(int fno) {
        this.fno = fno;
    }

    @Override
    public String toString() {
        return "FileDTO [path=" + path + ", fileName=" + fileName + ", type=" + type + ", bno=" + bno + ", fno=" + fno + "]";
    }
}
