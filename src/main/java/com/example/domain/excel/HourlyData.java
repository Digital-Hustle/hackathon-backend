package com.example.domain.excel;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hourly_data", schema = "tns_energy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HourlyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "day")
    @NotNull
    int day;

    @Column(name = "hour")
    @NotNull
    int hour;

    @Column(name = "volume")
    @NotNull
    float volume;
}
