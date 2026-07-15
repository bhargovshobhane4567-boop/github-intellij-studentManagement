package com.dsoft.dto.coursedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class CourseRequest {


    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotNull(message = "Duration is required")
    private Integer duration;

    @NotNull(message = "Fees are required")
    private Double fees;


}
