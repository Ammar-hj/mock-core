package com.platform.mockcore.model.dao;


import com.platform.mockcore.enums.HttpHeaderType;
import com.platform.mockcore.model.request.HttpInterfaceHeaderReq;

import java.util.List;

public interface HttpInterfaceHeaderDao {
    List<HttpInterfaceHeaderReq> batchQuery(Long httpInterfaceId, HttpHeaderType type);
}
