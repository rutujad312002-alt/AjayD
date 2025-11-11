package com.arcitech.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String userId;
    private String name;
    private String email;
    private String role;
    private String profileImage;
    private String status;
}
