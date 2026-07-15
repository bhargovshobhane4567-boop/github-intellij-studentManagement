package com.dsoft.dto.coursedto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class CourseResponse {

    private Long courseId;

    private String courseName;

    private Integer duration;

    private Double fees;

    private boolean deleted ;




}
