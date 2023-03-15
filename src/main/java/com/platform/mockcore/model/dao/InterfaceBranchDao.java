package com.platform.mockcore.model.dao;

import com.platform.mockcore.model.request.HttpInterfaceBranchReq;

import java.util.List;

public interface InterfaceBranchDao {
    List<HttpInterfaceBranchReq> batchQuery(Long httpInterfaceId);
}
