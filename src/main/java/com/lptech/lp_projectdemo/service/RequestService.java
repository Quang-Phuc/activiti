package com.lptech.lp_projectdemo.service;

import com.lptech.lp_projectdemo.dto.RequestGetDto;
import com.lptech.lp_projectdemo.dto.RequestInsertDto;
import com.lptech.lp_projectdemo.dto.RequestMultipleInsertDto;
import com.lptech.lp_projectdemo.dto.RequestUpdateDto;
import com.lptech.lp_projectdemo.entities.Request;
import com.lptech.lp_projectdemo.exception.customexception.RequestNotFoundException;
import com.lptech.lp_projectdemo.globalenum.RequestStatusEnum;
import com.lptech.lp_projectdemo.mapper.RequestMapper;
import com.lptech.lp_projectdemo.repository.RequestRepository;
import com.lptech.lp_projectdemo.spectification.RequestSpecification;
import com.lptech.lp_projectdemo.spectification.searchQueryCondition.RequestQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequestService {
    private final FileStorageService fileStorageService;
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(FileStorageService fileStorageService, RequestRepository requestRepository) {
        this.fileStorageService = fileStorageService;
        this.requestRepository = requestRepository;
    }


    public RequestInsertDto createRequest(RequestInsertDto requestInsertDto){
        Request request = RequestMapper.INSTANCE.fromRequestInsertDto(requestInsertDto);
        requestRepository.save(request);
        return requestInsertDto;
    }

    public RequestGetDto getRequest(Long id){
        Optional<Request> requestOptional = requestRepository.findByIdAndStatus(id, RequestStatusEnum.ACTIVATE);
        if(requestOptional.isPresent()){
            return RequestMapper.INSTANCE.toRequestGetDto(requestOptional.get());
        }
        throw new RequestNotFoundException("Không tìm thấy request");
    }

    @Transactional(readOnly = true)
    public Page<RequestGetDto> getPageRequest(Pageable pageable, RequestQueryCondition condition){
        RequestSpecification requestSpecification = new RequestSpecification(condition);
        Page<Request> page = requestRepository.findAll(requestSpecification,pageable);
        List<RequestGetDto> requestGetDto = RequestMapper.INSTANCE.toRequestGetDto(page.getContent());
        return new PageImpl<>(requestGetDto,pageable,page.getTotalElements());
    }

    public RequestUpdateDto updateRequest(RequestUpdateDto requestUpdateDto,Long id){
        Optional<Request> requestOptional = requestRepository.findById(id);
        if(requestOptional.isPresent()){
           RequestMapper.INSTANCE.updateFromRequestUpdateDto(requestUpdateDto,requestOptional.get());
           return requestUpdateDto;
        }
        throw new RequestNotFoundException("Không tìm thấy request");
    }

    public void deleteRequest(Long id){
        Optional<Request> requestOptional = requestRepository.findById(id);
        if(requestOptional.isPresent()){
            requestOptional.get().setStatus(RequestStatusEnum.DEACTIVATE);
            return;
        }
        throw new RuntimeException("Xóa request thất bại");
    }

    public RequestMultipleInsertDto createRequestAndMore(RequestMultipleInsertDto requestMultipleInsertDto) throws IOException {

        if (requestMultipleInsertDto.getFiles() != null) {
            try {
                for (MultipartFile m : requestMultipleInsertDto.getFiles()) {
                    fileStorageService.save(m);
                }
            } catch (IOException e) {
                throw new IOException("Xảy ra lỗi khi upload file");
            }
            requestMultipleInsertDto.setFileDtoFromFiles();
        }

        Request request = RequestMapper.INSTANCE.fromRequestMultipleInsertDto(requestMultipleInsertDto);
        request.setFileReferent();
        request.getRequestDetail().setRequest(request);
        requestRepository.save(request);
        return requestMultipleInsertDto;
    }



}
