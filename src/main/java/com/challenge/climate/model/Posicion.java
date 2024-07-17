package com.challenge.climate.model;

public class Posicion {

    private Integer grados;
    private Planeta planeta;

    public Posicion(Planeta planeta, int grados) {
        this.setGrados(grados);
        this.setPlaneta(planeta);
    }

    public Planeta getPlaneta() {
        return planeta;
    }

    public void setPlaneta(Planeta planeta) {
        this.planeta = planeta;
    }

    public Integer getGrados() {
        if (grados.equals(-180))	{
            return 180;
        } else	{
            return grados;
        }
    }
    public void setGrados(Integer grados) {
        this.grados = grados;
    }
}
