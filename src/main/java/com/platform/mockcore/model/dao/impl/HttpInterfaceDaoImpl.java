package com.platform.mockcore.model.dao.impl;

import com.platform.mockcore.enums.ConfigMode;
import com.platform.mockcore.enums.HttpHeaderType;
import com.platform.mockcore.enums.RespCodeEnum;
import com.platform.mockcore.enums.SpaceEnum;
import com.platform.mockcore.exception.MockException;
import com.platform.mockcore.model.dao.HttpInterfaceDao;
import com.platform.mockcore.model.dao.HttpInterfaceHeaderDao;
import com.platform.mockcore.model.dao.InterfaceBranchDao;
import com.platform.mockcore.model.entity.HttpInterface;
import com.platform.mockcore.model.entity.HttpInterfaceExample;
import com.platform.mockcore.model.entity.HttpInterfaceWithBLOBs;
import com.platform.mockcore.model.mapper.HttpInterfaceMapper;
import com.platform.mockcore.model.request.HttpInterfaceKeyReq;
import com.platform.mockcore.model.request.HttpInterfaceReq;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HttpInterfaceDaoImpl implements HttpInterfaceDao {

    @Autowired
    HttpInterfaceMapper httpInterfaceMapper;
    @Autowired
    InterfaceBranchDao interfaceBranchDao;
    @Autowired
    HttpInterfaceHeaderDao httpInterfaceHeaderDao;

    @Override
    public HttpInterfaceReq queryByKey(HttpInterfaceKeyReq request) {
        HttpInterfaceExample example = new HttpInterfaceExample();
        example.createCriteria().andRequestUriEqualTo(request.getRequestUri()).andRequestMethodEqualTo(request.getRequestMethod());
        List<HttpInterfaceWithBLOBs> httpInterfaceList = httpInterfaceMapper.selectByExampleWithBLOBs(example);
        int resultSize = httpInterfaceList.size();
        if (resultSize == 0) {
            return null;
        } else if (resultSize == 1) {
            return convertToReq(httpInterfaceList.get(0));
        } else {
            throw new MockException(RespCodeEnum.NOT_FOUND_HTTP_INTERFACE_DATA);
        }
    }

    private HttpInterfaceReq convertToReq(HttpInterface httpInterface) {
        HttpInterfaceReq httpInterfaceReq = new HttpInterfaceReq();
        BeanUtils.copyProperties(httpInterface, httpInterfaceReq);
        httpInterfaceReq.setConfigMode(EnumUtils.getEnum(ConfigMode.class, httpInterface.getConfigMode()));
        httpInterfaceReq.setSpaceEnum(EnumUtils.getEnum(SpaceEnum.class, httpInterface.getAccessAuthority()));
        httpInterfaceReq.setStart(httpInterface.getIsStart());

        Long id = httpInterface.getId();
        httpInterfaceReq.setResponseHeaderList(httpInterfaceHeaderDao.batchQuery(id, HttpHeaderType.RESPONSE));
        httpInterfaceReq.setCallbackRequestHeaderList(httpInterfaceHeaderDao.batchQuery(id, HttpHeaderType.CALLBACK_REQUEST));
        httpInterfaceReq.setBranchScriptList(interfaceBranchDao.batchQuery(id));
        return httpInterfaceReq;
    }
}
