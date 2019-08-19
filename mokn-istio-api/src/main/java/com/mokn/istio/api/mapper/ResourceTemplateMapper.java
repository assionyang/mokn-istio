package com.mokn.istio.api.mapper;

import com.mokn.istio.api.model.db.ResourceTemplate;

public interface ResourceTemplateMapper {
    public ResourceTemplate selectByResource(String resource);
}
