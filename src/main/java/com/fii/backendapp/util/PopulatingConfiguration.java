package com.fii.backendapp.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PopulatingConfiguration {
    private final Integer userCount;
    private final Integer adminCount;
    private final Integer maxTopicPlaces;
    private final Integer topicProb;
    private final Integer professorProb;
    private final Integer preferenceCount;
    private final Double propsToStudsRatio;
}
