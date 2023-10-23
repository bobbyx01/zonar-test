package com.zonar.apivehiculos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Entity
@Table(name = "vehicle_type")
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement(name = "VehicleType")
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement(name = "id")
    private Long id;

    @Column(name = "is_primary", nullable = false)
    @XmlElement(name = "isPrimary")
    private Boolean isPrimary;

    @Column(name = "name", nullable = false)
    @XmlElement(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    @XmlElement(name = "manufacturer")
    private Manufacturer manufacturer;
}
