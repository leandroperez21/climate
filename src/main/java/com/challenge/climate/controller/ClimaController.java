package com.challenge.climate.controller;

import com.challenge.climate.enums.ClimaEnum;
import com.challenge.climate.dto.PronosticoDTO;
import com.challenge.climate.dto.CondicionesClimaticasDTO;
import com.challenge.climate.exceptions.DiaIncorrectoException;
import com.challenge.climate.exceptions.TipoClimaException;
import com.challenge.climate.service.ClimaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.challenge.climate.utils.Constantes.CLIMA_INEXISTENTE;
import static com.challenge.climate.utils.Constantes.DIA_INCORRECTO;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final ClimaService climateCalculationJob;
    private CondicionesClimaticasDTO conditions;
    private Map<Integer, PronosticoDTO> pronosticos;

    public ClimaController(ClimaService climateCalculationJob) {
        this.climateCalculationJob = climateCalculationJob;
    }

    @PostConstruct
    public void init() {
        int TOTAL_DAYS = 365 * 10;
        conditions = climateCalculationJob.calcularPronosticos(TOTAL_DAYS);
        pronosticos = climateCalculationJob.getPronosticos();
    }

    /**
     * Obtiene la condición climática para un día específico.
     *
     * @param dia el número del día para el cual se desea obtener la condición climática
     * @return la condición climática del día especificado
     */
    @GetMapping("/dia/{dia}")
    public ResponseEntity<PronosticoDTO> getCondicionClimaticaDia(@PathVariable int dia) {
        if (!pronosticos.containsKey(dia)) {
            throw new DiaIncorrectoException(DIA_INCORRECTO);
        }

        return ResponseEntity.ok(pronosticos.get(dia));
    }

    /**
     * Obtiene los resultados de las condiciones climáticas calculadas.
     * Cantidad de dias de SEQUIA, LLUVIA, OPTIMO o INDEFINIDO y el dia mas lluvioso
     *
     * @return {@link  CondicionesClimaticasDTO} con los resultados de las condiciones climáticas
     */
    @GetMapping("/resultados")
    public CondicionesClimaticasDTO getResultados() {
        return conditions;
    }

    /**
     * Obtiene una lista de pronósticos para un tipo de clima específico.
     *
     * @param clima el tipo de clima para el cual se desean obtener los pronósticos
     * @return una lista de {@link PronosticoDTO} para el tipo de clima especificado
     * @throws TipoClimaException si el tipo de clima especificado no es válido
     */
    @GetMapping("/{clima}")
    public ResponseEntity<List<PronosticoDTO>> getAll(@PathVariable String clima) {

        List<String> climas = Arrays.stream(ClimaEnum.values()).map(Enum::toString).toList();

        if (!climas.contains(clima.toUpperCase())){
            throw new TipoClimaException(String.format(CLIMA_INEXISTENTE, clima));
        }

        return ResponseEntity.ok(pronosticos.values().stream()
                .filter(pronosticoDTO -> pronosticoDTO.clima().equalsIgnoreCase(clima))
                .toList());
    }

}
