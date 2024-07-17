package com.challenge.climate.model;

import com.challenge.climate.enums.PlanetaEnum;

public class Planeta {

    private PlanetaEnum nombre;
    private double radio;

    public Planeta(PlanetaEnum nombre, double radio)	{
        this.setNombre(nombre);
        this.setRadio(radio);
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public PlanetaEnum getNombre() {
        return nombre;
    }

    public void setNombre(PlanetaEnum nombre) {
        this.nombre = nombre;
    }

}
