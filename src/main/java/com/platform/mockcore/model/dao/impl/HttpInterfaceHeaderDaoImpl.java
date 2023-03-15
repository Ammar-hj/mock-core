package com.platform.mockcore.model.dao.impl;

import com.platform.mockcore.enums.HttpHeaderType;
import com.platform.mockcore.model.dao.HttpInterfaceHeaderDao;
import com.platform.mockcore.model.entity.HttpInterfaceHeader;
import com.platform.mockcore.model.entity.HttpInterfaceHeaderExample;
import com.platform.mockcore.model.mapper.HttpInterfaceHeaderMapper;
import com.platform.mockcore.model.request.HttpInterfaceHeaderReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class HttpInterfaceHeaderDaoImpl implements HttpInterfaceHeaderDao {

    @Autowired
    HttpInterfaceHeaderMapper httpInterfaceHeaderMapper;

    @Override
    public List<HttpInterfaceHeaderReq> batchQuery(Long httpInterfaceId, HttpHeaderType type) {
        HttpInterfaceHeaderExample example = new HttpInterfaceHeaderExample();
        example.createCriteria()
                .andHttpInterfaceIdEqualTo(httpInterfaceId)
                .andTypeEqualTo(type.name());
        return convertToReqList(httpInterfaceHeaderMapper.selectByExample(example));
    }

    private HttpInterfaceHeaderReq convertToReq(HttpInterfaceHeader httpInterfaceHeader) {
        HttpInterfaceHeaderReq httpInterfaceHeaderReq = new HttpInterfaceHeaderReq();
        BeanUtils.copyProperties(httpInterfaceHeader, httpInterfaceHeaderReq);
        return httpInterfaceHeaderReq;
    }

    private List<HttpInterfaceHeaderReq> convertToReqList(List<HttpInterfaceHeader> httpInterfaceHeaderList) {
        List<HttpInterfaceHeaderReq> httpInterfaceHeaderReqs = new LinkedList<>();
        httpInterfaceHeaderList.forEach(httpInterfaceHeader -> httpInterfaceHeaderReqs.add(convertToReq(httpInterfaceHeader)));
        return httpInterfaceHeaderReqs;
    }

}
