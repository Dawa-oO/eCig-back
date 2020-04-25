package com.floyd.eCigManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "preparation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Preparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Arome.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "aromeId", nullable = false)
    private Arome arome;

    private Integer quantity;

    @NotNull
    private Integer capacity;
}
