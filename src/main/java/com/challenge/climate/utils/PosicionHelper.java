package com.challenge.climate.utils;

import com.challenge.climate.enums.CuadranteEnum;
import com.challenge.climate.model.Posicion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.challenge.climate.utils.Constantes.*;

public class PosicionHelper {

    /**
     * Calcula la coordenada X de una posición dada.
     *
     * @param posicion la posición del planeta
     * @return la coordenada X
     */
    public static double getX(Posicion posicion) {
        return BigDecimal.valueOf(Math.cos(Math.toRadians(posicion.getGrados())) * posicion.getPlaneta().getRadio())
                .setScale(0, RoundingMode.FLOOR).doubleValue();
    }

    /**
     * Calcula la coordenada  Yde una posición dada.
     *
     * @param posicion la posición del planeta
     * @return la coordenada Y
     */
    public static double getY(Posicion posicion) {
        return BigDecimal.valueOf(Math.sin(Math.toRadians(posicion.getGrados())) * posicion.getPlaneta().getRadio())
                .setScale(0, RoundingMode.FLOOR).doubleValue();
    }

    /**
     * Calcula la pendiente entre dos puntos.
     *
     * @param p1 la primera posición
     * @param p2 la segunda posición
     * @return la pendiente entre p1 y p2
     */
    public static double getPendienteAUnPunto(Posicion p1, Posicion p2)	{
        return BigDecimal.valueOf((getY(p1) - getY(p2))/(getX(p1) - getX(p2)))
                .setScale(1, RoundingMode.FLOOR).doubleValue();
    }

    /**
     * Determina el cuadrante de una posición dada.
     *
     * @param posicion la posición del planeta
     * @return el cuadrante de la posición
     */
    public static CuadranteEnum getCuadrante(Posicion posicion)	{
        Integer grados = posicion.getGrados();

        if (grados > GRADOS_0 && grados < GRADOS_90)	{
            return CuadranteEnum.PRIMERO;
        } else if (grados > GRADOS_90 && grados < GRADOS_180)	{
            return CuadranteEnum.SEGUNDO;
        } else if ((grados > GRADOS_MINUS_180 && grados < GRADOS_MINUS_90) || grados.equals(GRADOS_180)){
            return CuadranteEnum.TERCERO;
        } else if (grados > GRADOS_MINUS_90 && grados < GRADOS_0)	{
            return CuadranteEnum.CUARTO;
        } else	{
            return CuadranteEnum.SOBREEJES;
        }
    }

    /**
     * Verifica si dos posiciones están en grados simétricos opuestos.
     *
     * @param p1 la primera posición
     * @param p2 la segunda posición
     * @return true si están en grados simétricos opuestos, false en caso contrario
     */
    public static boolean estanEnGradosSimetricosOpuestos (Posicion p1, Posicion p2)	{
        Integer gradosP1 = p1.getGrados();
        Integer gradosP2 = p2.getGrados();

        return (Objects.equals(gradosP1, gradosP2)) ||
                (gradosP1 == GRADOS_0 && gradosP2 == GRADOS_180) ||
                (gradosP1 > GRADOS_0 && gradosP2 == GRADOS_MINUS_180 + gradosP1) ||
                (gradosP1 < GRADOS_0 && gradosP2 == GRADOS_180 + gradosP1);
    }

    /**
     * Verifica si una posición está en el hemisferio norte.
     *
     * @param posicion la posición del planeta
     * @return true si está en el hemisferio norte, false en caso contrario
     */
    public static boolean estaEnHemisferioNorte(Posicion posicion)	{
        CuadranteEnum cuadrantePosicion = getCuadrante(posicion);
        return cuadrantePosicion.equals(CuadranteEnum.PRIMERO) || cuadrantePosicion.equals(CuadranteEnum.SEGUNDO);
    }

    /**
     * Verifica si una posición está en el hemisferio sur.
     *
     * @param posicion la posición del planeta
     * @return true si está en el hemisferio sur, false en caso contrario
     */
    public static boolean estaEnHemisferioSur(Posicion posicion)	{
        CuadranteEnum cuadrantePosicion = getCuadrante(posicion);
        return cuadrantePosicion.equals(CuadranteEnum.TERCERO) || cuadrantePosicion.equals(CuadranteEnum.CUARTO);
    }

    /**
     * Verifica si una posición está en el hemisferio este.
     *
     * @param posicion la posición del planeta
     * @return true si está en el hemisferio este, false en caso contrario
     */
    public static boolean estaEnHemisferioEste(Posicion posicion)	{
        CuadranteEnum cuadrantePosicion = getCuadrante(posicion);
        return cuadrantePosicion.equals(CuadranteEnum.PRIMERO) || cuadrantePosicion.equals(CuadranteEnum.CUARTO);
    }

    /**
     * Verifica si una posición está en el hemisferio oeste.
     *
     * @param posicion la posición del planeta
     * @return true si está en el hemisferio oeste, false en caso contrario
     */
    public static boolean estaEnHemisferioOeste(Posicion posicion)	{
        CuadranteEnum cuadrantePosicion = getCuadrante(posicion);
        return cuadrantePosicion.equals(CuadranteEnum.SEGUNDO) || cuadrantePosicion.equals(CuadranteEnum.TERCERO);
    }

    /**
     * Verifica si dos posiciones están en cuadrantes opuestos.
     *
     * @param p1 la primera posición
     * @param p2 la segunda posición
     * @return true si están en cuadrantes opuestos, false en caso contrario
     */
    public static boolean estanEnCuadrantesOpuestos (Posicion p1, Posicion p2) {
        CuadranteEnum cuadranteP1 = getCuadrante(p1);
        CuadranteEnum cuadranteP2 = getCuadrante(p2);

        return cuadranteP1.equals(CuadranteEnum.PRIMERO) && cuadranteP2.equals(CuadranteEnum.TERCERO) ||
                cuadranteP1.equals(CuadranteEnum.SEGUNDO) && cuadranteP2.equals(CuadranteEnum.CUARTO) ||
                cuadranteP1.equals(CuadranteEnum.TERCERO) && cuadranteP2.equals(CuadranteEnum.PRIMERO) ||
                cuadranteP1.equals(CuadranteEnum.CUARTO) && cuadranteP2.equals(CuadranteEnum.SEGUNDO);
    }
}
