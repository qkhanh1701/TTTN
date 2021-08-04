package com.quockhanh.commercemanager.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderDTO {
    private Long id;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date order_date;
    private Double amount;
    private String receiver;
    private String address;
    private String phone_number;
    private Long user_id;
    private Integer status;
}

