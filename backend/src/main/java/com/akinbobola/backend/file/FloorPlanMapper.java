package com.akinbobola.backend.file;

import com.akinbobola.backend.floorPlan.FloorPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorPlanMapper {
    public List<String> toFloorPlanResponseList (List<FloorPlan> floorPlans) {
        return floorPlans.stream()
                .map(floorPlan -> String.format(
                        "http://localhost:8080/api/v1/listings/%d/floor-plans/%d",
                        floorPlan.getListing().getId(),
                        floorPlan.getId()
                )).toList();
    }
}
