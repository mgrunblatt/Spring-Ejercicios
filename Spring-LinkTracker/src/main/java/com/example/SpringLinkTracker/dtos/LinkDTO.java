package com.example.SpringLinkTracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkDTO {

    private Long id;
    private String url;
    private int stats;

}
