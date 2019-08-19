package com.mokn.istio.api.model.k8s.destinationrule;

public class DestinationRule_Spec_Subset {
    public DestinationRule_Spec_Subset_Label getLabels() {
        return labels;
    }

    public void setLabels(DestinationRule_Spec_Subset_Label labels) {
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private DestinationRule_Spec_Subset_Label labels;
    private String name;
}
