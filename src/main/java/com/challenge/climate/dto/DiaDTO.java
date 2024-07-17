package com.challenge.climate.dto;

import com.challenge.climate.model.Dia;
import com.challenge.climate.model.Posicion;

public class DiaDTO {

    private long dia;
    private double perimetro;
    private Posicion posicionPlaneta1;
    private Posicion posicionPlaneta2;
    private Posicion posicionPlaneta3;

    public long getDia() {
        return dia;
    }

    public void setDia(long dia) {
        this.dia = dia;
    }

    public double getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(double perimetro) {
        this.perimetro = perimetro;
    }

    public Posicion getPosicionPlaneta1() {
        return posicionPlaneta1;
    }

    public void setPosicionPlaneta1(Posicion posicionPlaneta1) {
        this.posicionPlaneta1 = posicionPlaneta1;
    }

    public Posicion getPosicionPlaneta2() {
        return posicionPlaneta2;
    }

    public void setPosicionPlaneta2(Posicion posicionPlaneta2) {
        this.posicionPlaneta2 = posicionPlaneta2;
    }

    public Posicion getPosicionPlaneta3() {
        return posicionPlaneta3;
    }

    public void setPosicionPlaneta3(Posicion posicionPlaneta3) {
        this.posicionPlaneta3 = posicionPlaneta3;
    }

    public static DiaDTO convertirDiaADiaDTO(Dia dia) {
        DiaDTO diaDTO = new DiaDTO();
        diaDTO.setDia(dia.getDia());
        diaDTO.setPerimetro(dia.getPerimetro());
        diaDTO.setPosicionPlaneta1(dia.getPosicionPlaneta1());
        diaDTO.setPosicionPlaneta2(dia.getPosicionPlaneta2());
        diaDTO.setPosicionPlaneta3(dia.getPosicionPlaneta3());
        return diaDTO;
    }
}
