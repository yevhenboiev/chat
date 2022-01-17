package ru.simbirsoft.chat.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;

@Component
@Getter
@Setter
public class Videos {
    private String id;
    private String title;
    private Date published;
    private BigInteger viewCount;
    private BigInteger likeCount;
}
