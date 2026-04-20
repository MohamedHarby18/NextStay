package com.nextstay.nextstaymain.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Builder
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
