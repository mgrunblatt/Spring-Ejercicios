package com.example.SpringLinkTracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.concurrent.atomic.AtomicLong;

@Data
@AllArgsConstructor
public class LinkDTO {

    private Long id;
    private String url;

}
