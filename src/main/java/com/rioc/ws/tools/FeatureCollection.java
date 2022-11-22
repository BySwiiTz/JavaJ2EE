package com.rioc.ws.tools;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class FeatureCollection {

    private List<Feature> features;

}
