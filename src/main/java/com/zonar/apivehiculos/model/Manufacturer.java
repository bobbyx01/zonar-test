package com.zonar.apivehiculos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@Data
@Entity
@Table(name = "manufacturer")
@EntityListeners(AuditingEntityListener.class)
@XmlRootElement(name = "Manufacturer")
//@JacksonXmlRootElement(localName = "Manufacturer")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlElement(name = "id")
    private Long id;

    @Column(name = "mfr_id", nullable = false)
    @XmlElement(name = "mfrId")
    private Integer mfrId;

    @Column(name = "mfr_name", nullable = false)
    @XmlElement(name = "mfrName")
    private String mfrName;

    @Column(name = "country", nullable = false)
    @XmlElement(name = "country")
    private String country;

    @Column(name = "mfr_common_name")
    @XmlElement(name = "mfrCommonName")
    private String mfrCommonName;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "manufacturer")
    @XmlElement(name = "vehicleTypes")
    //@JacksonXmlElementWrapper(localName = "vehicleTypes")
    //@JacksonXmlProperty(localName = "VehicleType")
    private List<VehicleType> vehicleTypes;
}
