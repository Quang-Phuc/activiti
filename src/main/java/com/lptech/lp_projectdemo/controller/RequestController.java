package com.lptech.lp_projectdemo.controller;

import com.lptech.lp_projectdemo.dto.RequestDetailDto;
import com.lptech.lp_projectdemo.dto.RequestGetDto;
import com.lptech.lp_projectdemo.dto.RequestInsertDto;
import com.lptech.lp_projectdemo.dto.RequestMultipleInsertDto;
import com.lptech.lp_projectdemo.dto.RequestUpdateDto;
import com.lptech.lp_projectdemo.entities.Request;
import com.lptech.lp_projectdemo.service.RequestService;
import com.lptech.lp_projectdemo.spectification.searchQueryCondition.RequestQueryCondition;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping()
    public RequestInsertDto createRequest(@RequestBody @Valid RequestInsertDto requestInsertDto){
        return requestService.createRequest(requestInsertDto);

    }

    @GetMapping("/{id}")
    public RequestGetDto getRequest(@PathVariable Long id){
        return requestService.getRequest(id);
    }

    @GetMapping()
    public Page<RequestGetDto> getPageRequest(Pageable pageable, RequestQueryCondition queryCondition){
        return requestService.getPageRequest(pageable,queryCondition);
    }

    @PutMapping("/{id}")
    public RequestUpdateDto updateRequest(@PathVariable Long id, @RequestBody @Valid RequestUpdateDto requestUpdateDto){
        return requestService.updateRequest(requestUpdateDto,id);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable Long id){
        requestService.deleteRequest(id);
    }


    @PostMapping("/multipleInsert")
    public RequestMultipleInsertDto createRequestAndMore(@RequestPart(name = "request_detail") RequestDetailDto requestDetailDto, RequestMultipleInsertDto requestMultipleInsertDto) throws IOException {
        requestMultipleInsertDto.setRequestDetailDto(requestDetailDto);
        return requestService.createRequestAndMore(requestMultipleInsertDto);
    }
}
