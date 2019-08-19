package com.mokn.istio.api.service.db;

import com.mokn.istio.api.mapper.ResourceTemplateMapper;
import com.mokn.istio.api.model.db.ResourceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceTemplateServiceImpl implements ResourceTemplateService {

    @Autowired
    private ResourceTemplateMapper resourceTemplateMapper;

    public ResourceTemplate getByResource(String resource){
        return resourceTemplateMapper.selectByResource(resource);
    }
}
