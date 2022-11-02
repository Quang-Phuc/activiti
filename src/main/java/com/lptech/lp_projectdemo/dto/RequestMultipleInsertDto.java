package com.lptech.lp_projectdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class RequestMultipleInsertDto {
    @NotNull
    @NotBlank
    @Size(min = 2,max = 30)
    private String name;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

    private String description;

    private String status;

    @JsonProperty("request_detail")
    private RequestDetailDto requestDetailDto;

    @JsonProperty("files")
    private MultipartFile[] files;

    @JsonProperty("files")
    private List<FileDto> fileDto = new ArrayList<>();

    public void setFileDtoFromFiles(){
        for (MultipartFile m : files){
            FileDto fDto = new FileDto();
            fDto.setFileName(m.getOriginalFilename());
            fDto.setFileType(m.getContentType());
            this.fileDto.add(fDto);
        }


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RequestDetailDto getRequestDetailDto() {
        return requestDetailDto;
    }

    public void setRequestDetailDto(RequestDetailDto requestDetailDto) {
        this.requestDetailDto = requestDetailDto;
    }

    @JsonIgnore
    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }


    public List<FileDto> getFileDto() {
        return fileDto;
    }

    public void setFileDto(List<FileDto> fileDto) {
        this.fileDto = fileDto;
    }
}
