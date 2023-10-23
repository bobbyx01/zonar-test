package com.zonar.apivehiculos.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

//@XmlRootElement(name = "manufacturers")
@JacksonXmlRootElement(localName = "Manufacturers")
public class ManufacturerListWrapper {
    //@JacksonXmlElementWrapper(useWrapping = false)
    //@JacksonXmlProperty(localName = "Manufacturer")

    private List<Manufacturer> manufacturers;

    //@JacksonXmlElementWrapper(localName = "Manufacturers")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Manufacturer")
    //@XmlElement(name = "manufacturer")
    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }
}

