package com.gguilhermelopes.movieSphere.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Genre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @EqualsAndHashCode.Exclude
    private String name;
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}
