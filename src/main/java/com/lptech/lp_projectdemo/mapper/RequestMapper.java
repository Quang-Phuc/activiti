package com.lptech.lp_projectdemo.mapper;

import com.lptech.lp_projectdemo.dto.RequestGetDto;
import com.lptech.lp_projectdemo.dto.RequestInsertDto;
import com.lptech.lp_projectdemo.dto.RequestMultipleInsertDto;
import com.lptech.lp_projectdemo.dto.RequestUpdateDto;
import com.lptech.lp_projectdemo.entities.File;
import com.lptech.lp_projectdemo.entities.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;

@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE =Mappers.getMapper(RequestMapper.class);

    Request fromRequestInsertDto(RequestInsertDto requestInsertDto);
    RequestGetDto toRequestGetDto(Request request);
    List<RequestGetDto> toRequestGetDto(List<Request> requests);

    void updateFromRequestUpdateDto(RequestUpdateDto requestUpdateDto, @MappingTarget Request request);

    @Mapping(target = "files", source = "fileDto")
    @Mapping(target = "requestDetail", source = "requestDetailDto")
    Request fromRequestMultipleInsertDto(RequestMultipleInsertDto requestMultipleInsertDto);

//    default File multipartFileToFile(MultipartFile multipartFile){
//        File file = new File();
//        file.setFileName(multipartFile.getOriginalFilename());
//        file.setFileType(multipartFile.getContentType());
//        return file;
//    }
}
