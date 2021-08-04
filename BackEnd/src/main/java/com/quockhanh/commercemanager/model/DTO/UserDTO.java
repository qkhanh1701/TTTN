package com.quockhanh.commercemanager.model.DTO;

import com.quockhanh.commercemanager.model.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    private Set<Role> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String resetToken;

    private Integer deletestatus;

    private List<OrderDTO> orders;
}
