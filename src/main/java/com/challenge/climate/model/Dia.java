package com.challenge.climate.model;

import com.challenge.climate.dto.DiaDTO;
import com.challenge.climate.dto.PlanetaDTO;
import com.challenge.climate.enums.ClimaEnum;
import com.challenge.climate.enums.PlanetaEnum;
import com.challenge.climate.utils.PosicionHelper;

import java.util.Map;

public class Dia {

    private int dia;
    private ClimaEnum tipoClima;
    private final Posicion posicionPlaneta1;
    private final Posicion posicionPlaneta2;
    private final Posicion posicionPlaneta3;

    public Dia(int numeroDia, Map<PlanetaEnum, PlanetaDTO> mapaConfig) {
        super();
        this.setDia(numeroDia);
        this.setTipoClima(ClimaEnum.INDEFINIDO);
        this.posicionPlaneta1 = new Posicion(new Planeta(PlanetaEnum.BETASOIDE, mapaConfig.get(PlanetaEnum.BETASOIDE).getRadio()), 90);
        this.posicionPlaneta2 = new Posicion(new Planeta(PlanetaEnum.FERENGI, mapaConfig.get(PlanetaEnum.FERENGI).getRadio()), 90);
        this.posicionPlaneta3 = new Posicion(new Planeta(PlanetaEnum.VULCANO, mapaConfig.get(PlanetaEnum.VULCANO).getRadio()), 90);
    }

    public double getPerimetro() {
        double xPosicion1 = PosicionHelper.getX(this.getPosicionPlaneta1());
        double xPosicion2 = PosicionHelper.getX(this.getPosicionPlaneta2());
        double xPosicion3 = PosicionHelper.getX(this.getPosicionPlaneta3());
        double yPosicion1 = PosicionHelper.getY(this.getPosicionPlaneta1());
        double yPosicion2 = PosicionHelper.getY(this.getPosicionPlaneta2());
        double yPosicion3 = PosicionHelper.getY(this.getPosicionPlaneta3());

        return Math.hypot(xPosicion1 - xPosicion2, yPosicion1 - yPosicion2) +
                Math.hypot(xPosicion1 - xPosicion3, yPosicion1 - yPosicion3) +
                Math.hypot(xPosicion2 - xPosicion3, yPosicion2 - yPosicion3);
    }

    public Posicion getPosicionPlaneta1() {
        return posicionPlaneta1;
    }

    public Posicion getPosicionPlaneta2() {
        return posicionPlaneta2;
    }

    public Posicion getPosicionPlaneta3() {
        return posicionPlaneta3;
    }

    public ClimaEnum getTipoClima() {
        return tipoClima;
    }

    public void setTipoClima(ClimaEnum tipoClima) {
        this.tipoClima = tipoClima;
    }

    public Posicion getPosicion (PlanetaEnum planeta)	{
        return switch (planeta) {
            case BETASOIDE -> posicionPlaneta1;
            case FERENGI -> this.posicionPlaneta2;
            case VULCANO -> this.posicionPlaneta3;
            default -> null;
        };
    }

    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }

    public int compareTo(DiaDTO dia1) {
        return (this.getPerimetro() < dia1.getPerimetro()) ? -1 : 1;
    }

}
