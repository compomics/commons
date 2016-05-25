package com.compomics.commons.model.standards.peff;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Davy Maddelein on 4/19/2016.
 */
public class Header {


    private Map<String, Object> additionalInformation = new HashMap<>();
    private final String accession;

    public Header(String accession) {
        this.accession = accession;
    }

    public Map<String,Object> getAdditionalInformation(){
    return additionalInformation;
    }
    public Header setAdditionalInformation(Map<String,Object> parsedHeader){
        additionalInformation = parsedHeader;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(">"+accession+" ");

        additionalInformation.entrySet().stream()
                .forEach(e -> builder.append("\\ ")
                        .append(e.getKey())
                        .append("=")
                        .append(e.getValue().toString()));
            return builder.toString();
    }
}
