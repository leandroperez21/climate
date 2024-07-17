package com.challenge.climate.service;

import com.challenge.climate.dto.CondicionesClimaticasDTO;
import com.challenge.climate.dto.DiaDTO;
import com.challenge.climate.dto.PlanetaDTO;
import com.challenge.climate.dto.PronosticoDTO;
import com.challenge.climate.enums.ClimaEnum;
import com.challenge.climate.enums.OrientacionEnum;
import com.challenge.climate.enums.PlanetaEnum;
import com.challenge.climate.model.*;
import com.challenge.climate.utils.ClimaHelper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClimaService {

    private Map<PlanetaEnum, PlanetaDTO> planetaConfigMap;
    private Map<Integer, Dia> dias;
    private Map<ClimaEnum, Long> climaCountMap;
    private Posicion posicionSol;
    private Map<Integer, PronosticoDTO> pronosticos;

    /**
     * Calcula los pronósticos climáticos para un número de días dado.
     *
     * @param dias el número de días para calcular los pronósticos
     * @return un objeto CondicionesClimaticasDTO con el día de mayor lluvia y un mapa con el conteo de cada tipo de clima
     */
    public CondicionesClimaticasDTO calcularPronosticos(int dias) {
        setUp();
        calcularPosiciones(dias);
        calcularClimas();
        return new CondicionesClimaticasDTO(obtenerDiaMayorLluvia(), climaCountMap);
    }

    /**
     * Configura los datos iniciales necesarios para los cálculos.
     */
    private void setUp() {
        planetaConfigMap = new HashMap<>();
        pronosticos = new HashMap<>();
        planetaConfigMap.put(PlanetaEnum.FERENGI, new PlanetaDTO(PlanetaEnum.FERENGI, 500.0, OrientacionEnum.HORARIA, 1));
        planetaConfigMap.put(PlanetaEnum.BETASOIDE, new PlanetaDTO(PlanetaEnum.BETASOIDE, 2000.0, OrientacionEnum.HORARIA, 3));
        planetaConfigMap.put(PlanetaEnum.VULCANO, new PlanetaDTO(PlanetaEnum.VULCANO, 1000.0, OrientacionEnum.ANTIHORARIA, 5));

        dias = new HashMap<>();
        climaCountMap = new HashMap<>();
        posicionSol = new Posicion(new Planeta(PlanetaEnum.SOL, 0), 90);
    }

    /**
     * Obtiene el día con mayor cantidad de lluvia.
     * El dia con mayor lluvia es aquel que tiene el maximo valor de perimetro.
     *
     * @return el día con mayor cantidad de lluvia, o null si no hay días de lluvia
     */
    private Dia obtenerDiaMayorLluvia() {
        return dias.values().stream()
                .filter(dia -> dia.getTipoClima().equals(ClimaEnum.LLUVIA))
                .max(Comparator.comparing(Dia::getPerimetro))
                .orElse(null);
    }

    /**
     * Calcula las posiciones de los planetas para un número total de días.
     *
     * @param totalDias el número total de días para calcular las posiciones
     */
    private void calcularPosiciones(int totalDias) {
        for (int i = 0; i < totalDias; i++) {
            Dia diaAnterior = dias.get(i);
            dias.put(i + 1, calcularDia(i + 1, diaAnterior));
        }
    }

    /**
     * Calcula la información del clima para un día específico.
     *
     * @param diaNumero el número del día a calcular
     * @param diaAnterior la información del día anterior
     * @return la información del día calculado
     */
    private Dia calcularDia(int diaNumero, Dia diaAnterior) {
        Dia dia = new Dia(diaNumero, planetaConfigMap);
        planetaConfigMap.forEach((nombrePlaneta, config) -> calcularPosicion(dia, config, diaAnterior));
        return dia;
    }

    /**
     * Calcula la posición de un planeta en un día específico.
     *
     * @param dia la información del día
     * @param config la configuración del planeta
     * @param diaAnterior la información del día anterior
     */
    private void calcularPosicion(Dia dia, PlanetaDTO config, Dia diaAnterior) {
        int gradosPrevios = diaAnterior == null ? 0 : diaAnterior.getPosicion(config.getNombre()).getGrados();
        int nuevosGrados = ClimaHelper.calcularGrados(config.getVelocidad(), config.getOrientacion(), gradosPrevios);
        dia.getPosicion(config.getNombre()).setGrados(nuevosGrados);
    }

    /**
     * Calcula los tipos de climas para todos los días.
     */
    private void calcularClimas() {
        dias.forEach((dia, diaObj) -> {
            calcularClimaDia(diaObj);
            guardarClima(diaObj);
            climaCountMap.merge(diaObj.getTipoClima(), 1L, Long::sum);
        });
    }

    /**
     * Guarda la información del clima de un día especifico.
     *
     * @param dia {@link Dia}
     */
    private void guardarClima(Dia dia) {
        PronosticoDTO pronosticoDTO = new PronosticoDTO(dia.getTipoClima().name(), DiaDTO.convertirDiaADiaDTO(dia));
        pronosticos.put(dia.getDia(), pronosticoDTO);
    }

    /**
     * Calcula el tipo de clima para un día específico.
     *
     * @param dia la información del día
     */
    private void calcularClimaDia(Dia dia) {
        if (ClimaHelper.esSequia(dia)) {
            dia.setTipoClima(ClimaEnum.SEQUIA);
        } else if (ClimaHelper.esOptimo(dia, posicionSol)) {
            dia.setTipoClima(ClimaEnum.OPTIMO);
        } else if (ClimaHelper.esLluvia(dia, posicionSol)) {
            dia.setTipoClima(ClimaEnum.LLUVIA);
        }
    }

    /**
     * Obtiene los pronósticos calculados.
     *
     * @return un mapa de pronósticos con el número de día como clave y el pronóstico como valor
     */
    public Map<Integer , PronosticoDTO> getPronosticos() {
        return pronosticos;
    }

}
