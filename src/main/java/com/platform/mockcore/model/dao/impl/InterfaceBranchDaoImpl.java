package com.platform.mockcore.model.dao.impl;

import com.platform.mockcore.model.dao.InterfaceBranchDao;
import com.platform.mockcore.model.entity.InterfaceBranch;
import com.platform.mockcore.model.entity.InterfaceBranchExample;
import com.platform.mockcore.model.mapper.InterfaceBranchMapper;
import com.platform.mockcore.model.request.HttpInterfaceBranchReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class InterfaceBranchDaoImpl implements InterfaceBranchDao {

    @Autowired
    InterfaceBranchMapper interfaceBranchMapper;

    @Override
    public List<HttpInterfaceBranchReq> batchQuery(Long httpInterfaceId) {
        InterfaceBranchExample example = new InterfaceBranchExample();
        example.createCriteria()
                .andHttpInterfaceIdEqualTo(httpInterfaceId);
        return convertToBOList(interfaceBranchMapper.selectByExample(example));
    }

    private HttpInterfaceBranchReq convertToReq(InterfaceBranch interfaceBranch) {
        HttpInterfaceBranchReq httpInterfaceBranchReq = new HttpInterfaceBranchReq();
        BeanUtils.copyProperties(interfaceBranch, httpInterfaceBranchReq);
        return httpInterfaceBranchReq;
    }

    private List<HttpInterfaceBranchReq> convertToBOList(List<InterfaceBranch> interfaceBranchList) {
        List<HttpInterfaceBranchReq> httpInterfaceBranchReqs = new LinkedList<>();
        interfaceBranchList.forEach(httpInterfaceBranch
                -> httpInterfaceBranchReqs.add(convertToReq(httpInterfaceBranch)));
        return httpInterfaceBranchReqs;
    }
}
