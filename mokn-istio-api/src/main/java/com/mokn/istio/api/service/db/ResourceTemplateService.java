package com.mokn.istio.api.service.db;

import com.mokn.istio.api.model.db.ResourceTemplate;

public interface ResourceTemplateService {
    public ResourceTemplate getByResource(String resource);
}
