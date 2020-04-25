package com.floyd.eCigManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "booster")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Booster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @NotNull
    private Integer capacity;

    @NotNull
    private String pgvg;
}
