package com.floyd.eCigManagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arome")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Arome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @NotNull
    private Integer capacity;

    @NotNull
    private String taste;

    @NotNull
    private String brand;

    private Double note;

}
