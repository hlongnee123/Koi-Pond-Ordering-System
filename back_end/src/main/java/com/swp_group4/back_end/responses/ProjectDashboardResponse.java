package com.swp_group4.back_end.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProjectDashboardResponse {

    long totalProjects;
    double successPercentage;
    double failedPercentage;
    double inProgressPercentage;
    List<ProjectInfoBaseTimeResponse> projectInfoBaseTimeResponses;
    List<PackageDashboardResponse> PackageDashboardResponses;

}
