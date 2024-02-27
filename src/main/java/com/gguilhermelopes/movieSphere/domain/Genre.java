package com.gguilhermelopes.movieSphere.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Genre implements Serializable {
    private String id;
    @EqualsAndHashCode.Exclude
    private String name;
}
