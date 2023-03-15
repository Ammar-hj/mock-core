package com.platform.mockcore.model.dao;

import com.platform.mockcore.model.request.HttpInterfaceKeyReq;
import com.platform.mockcore.model.request.HttpInterfaceReq;

public interface HttpInterfaceDao {
    HttpInterfaceReq queryByKey(HttpInterfaceKeyReq request);
}
