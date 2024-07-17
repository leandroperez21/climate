package com.challenge.climate.dto;

import com.challenge.climate.enums.ClimaEnum;
import com.challenge.climate.model.Dia;

import java.util.Map;

public class CondicionesClimaticasDTO {

    private long sequia;
    private long lluvia;
    private long optimo;
    private long indefinido;
    private DiaDTO diaMasLluvioso;

    public CondicionesClimaticasDTO(Dia diaMayorPrecipitacion, Map<ClimaEnum,Long> totales) {
        this.setSequia(totales.get(ClimaEnum.SEQUIA));
        this.setLluvia(totales.get(ClimaEnum.LLUVIA));
        this.setOptimo(totales.get(ClimaEnum.OPTIMO));
        this.setIndefinido(totales.get(ClimaEnum.INDEFINIDO));
        this.setDiaMasLluvioso(DiaDTO.convertirDiaADiaDTO(diaMayorPrecipitacion));
    }

    public DiaDTO getDiaMasLluvioso() {
        return diaMasLluvioso;
    }

    public void setDiaMasLluvioso(DiaDTO diaMasLluvioso) {
        this.diaMasLluvioso = diaMasLluvioso;
    }

    public long getSequia() {
        return sequia;
    }

    public void setSequia(long sequia) {
        this.sequia = sequia;
    }

    public long getLluvia() {
        return lluvia;
    }

    public void setLluvia(long lluvia) {
        this.lluvia = lluvia;
    }

    public long getOptimo() {
        return optimo;
    }

    public void setOptimo(long optimo) {
        this.optimo = optimo;
    }

    public long getIndefinido() {
        return indefinido;
    }

    public void setIndefinido(long indefinido) {
        this.indefinido = indefinido;
    }
}
