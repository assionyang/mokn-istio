package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRuleList_Item {
    private DestinationRule_Metadata metadata;
    private DestinationRule_Spec spec;

    public DestinationRule_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(DestinationRule_Metadata metadata) {
        this.metadata = metadata;
    }

    public DestinationRule_Spec getSpec() {
        return spec;
    }

    public void setSpec(DestinationRule_Spec spec) {
        this.spec = spec;
    }
}
