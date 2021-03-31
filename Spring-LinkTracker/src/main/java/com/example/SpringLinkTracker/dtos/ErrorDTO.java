package com.example.SpringLinkTracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String name;
    private String description;
}
