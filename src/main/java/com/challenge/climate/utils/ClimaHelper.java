package com.challenge.climate.utils;

import com.challenge.climate.enums.CuadranteEnum;
import com.challenge.climate.enums.OrientacionEnum;
import com.challenge.climate.model.Dia;
import com.challenge.climate.model.Posicion;

import static com.challenge.climate.utils.Constantes.*;

public class ClimaHelper {

    /**
     * Determina si el día dado tiene lluvia basado en las posiciones de los planetas y el sol.
     *
     * @param dia el día con las posiciones planetarias
     * @param posicionSol la posición del sol
     * @return true si es un día lluvioso, false en caso contrario
     */
    public static boolean esLluvia(Dia dia, Posicion posicionSol) {
        return !estanEnMismoCuadrante(dia) && !estanEnMismaMitad(dia) && formanTrianguloDentroDelSol(dia, posicionSol);
    }

    /**
     * Determina si el día dado tiene sequía basado en las posiciones de los planetas.
     *
     * @param dia el día con las posiciones planetarias
     * @return true si es un día de sequía, false en caso contrario
     */
    public static boolean esSequia(Dia dia) {
        return PosicionHelper.estanEnGradosSimetricosOpuestos(dia.getPosicionPlaneta1(), dia.getPosicionPlaneta2())
                && PosicionHelper.estanEnGradosSimetricosOpuestos(dia.getPosicionPlaneta2(), dia.getPosicionPlaneta3());
    }

    /**
     * Determina si el día dado tiene condiciones climáticas óptimas basado en las posiciones de los planetas y el sol.
     *
     * @param dia el día con las posiciones planetarias
     * @param posicionSol la posición del sol
     * @return true si es un día de clima óptimo, false en caso contrario
     */
    public static boolean esOptimo(Dia dia, Posicion posicionSol) {
        return estanAlineados (dia.getPosicionPlaneta1(), dia.getPosicionPlaneta2(), dia.getPosicionPlaneta3()) &&
                !estanAlineados (dia.getPosicionPlaneta1(), posicionSol, dia.getPosicionPlaneta2());
    }

    /**
     * Calcula el nuevo angulo de un planeta basado en su velocidad y orientación.
     *
     * @param velocidad la velocidad del planeta
     * @param orientacion la orientación del movimiento del planeta
     * @param grados el ángulo actual en grados
     * @return el nuevo ángulo en grados
     */
    public static int calcularGrados(int velocidad, OrientacionEnum orientacion, int grados) {
        int resultado = orientacion == OrientacionEnum.HORARIA ? grados - velocidad : grados + velocidad;

        if (resultado > GRADOS_180)	{
            resultado = GRADOS_MINUS_360 + Math.abs(resultado);
        }

        if (resultado < GRADOS_MINUS_180)	{
            resultado = GRADOS_360 - Math.abs(resultado);
        }

        return resultado;
    }

    /**
     * Verifica si los planetas están en el mismo cuadrante.
     *
     * @param dia el día con las posiciones planetarias
     * @return true si todos los planetas están en el mismo cuadrante, false en caso contrario
     */
    private static boolean estanEnMismoCuadrante(Dia dia) {
        CuadranteEnum cuadrantePlaneta1 = PosicionHelper.getCuadrante(dia.getPosicionPlaneta1());
        CuadranteEnum cuadrantePlaneta2 = PosicionHelper.getCuadrante(dia.getPosicionPlaneta2());
        CuadranteEnum cuadrantePlaneta3 = PosicionHelper.getCuadrante(dia.getPosicionPlaneta3());

        return cuadrantePlaneta1.equals(cuadrantePlaneta2) &&
                cuadrantePlaneta2.equals(cuadrantePlaneta3);
    }

    /**
     * Verifica si los planetas están en la misma mitad de la esfera celeste.
     *
     * @param dia el día con las posiciones planetarias
     * @return true si todos los planetas están en la misma mitad, false en caso contrario
     */
    private static boolean estanEnMismaMitad(Dia dia) {
        boolean estaElPlaneta1EnElNorte = PosicionHelper.estaEnHemisferioNorte(dia.getPosicionPlaneta1());
        boolean estaElPlaneta1EnElSur = PosicionHelper.estaEnHemisferioSur(dia.getPosicionPlaneta1());
        boolean estaElPlaneta1EnElEste = PosicionHelper.estaEnHemisferioEste(dia.getPosicionPlaneta1());
        boolean estaElPlaneta1EnElOeste = PosicionHelper.estaEnHemisferioOeste(dia.getPosicionPlaneta1());

        boolean estaElPlaneta2EnElNorte = PosicionHelper.estaEnHemisferioNorte(dia.getPosicionPlaneta2());
        boolean estaElPlaneta2EnElSur = PosicionHelper.estaEnHemisferioSur(dia.getPosicionPlaneta2());
        boolean estaElPlaneta2EnElEste = PosicionHelper.estaEnHemisferioEste(dia.getPosicionPlaneta2());
        boolean estaElPlaneta2EnElOeste = PosicionHelper.estaEnHemisferioOeste(dia.getPosicionPlaneta2());

        boolean estaElPlaneta3EnElNorte = PosicionHelper.estaEnHemisferioNorte(dia.getPosicionPlaneta3());
        boolean estaElPlaneta3EnElSur = PosicionHelper.estaEnHemisferioSur(dia.getPosicionPlaneta3());
        boolean estaElPlaneta3EnElEste = PosicionHelper.estaEnHemisferioEste(dia.getPosicionPlaneta3());
        boolean estaElPlaneta3EnElOeste = PosicionHelper.estaEnHemisferioOeste(dia.getPosicionPlaneta3());

        return (estaElPlaneta1EnElNorte && estaElPlaneta2EnElNorte && estaElPlaneta3EnElNorte) ||
                (estaElPlaneta1EnElSur && estaElPlaneta2EnElSur && estaElPlaneta3EnElSur) ||
                (estaElPlaneta1EnElEste && estaElPlaneta2EnElEste && estaElPlaneta3EnElEste) ||
                (estaElPlaneta1EnElOeste && estaElPlaneta2EnElOeste && estaElPlaneta3EnElOeste);
    }

    /**
     * Verifica si los planetas forman un triángulo que contiene al sol.
     *
     * @param dia el día con las posiciones planetarias
     * @param posicionSol la posición del sol
     * @return true si los planetas forman un triángulo que contiene al sol, false en caso contrario
     */
    private static boolean formanTrianguloDentroDelSol (Dia dia, Posicion posicionSol) {
        Posicion planeta1 = dia.getPosicionPlaneta1();
        Posicion planeta2 = dia.getPosicionPlaneta2();
        Posicion planeta3 = dia.getPosicionPlaneta3();

        boolean planeta1a2 = PosicionHelper.estanEnCuadrantesOpuestos(planeta1, planeta2);
        boolean planeta1a3 = PosicionHelper.estanEnCuadrantesOpuestos(planeta1, planeta3);
        boolean planeta2a1 = PosicionHelper.estanEnCuadrantesOpuestos(planeta2, planeta1);
        boolean planeta2a3 = PosicionHelper.estanEnCuadrantesOpuestos(planeta2, planeta3);
        boolean planeta3a1 = PosicionHelper.estanEnCuadrantesOpuestos(planeta3, planeta1);
        boolean planeta3a2 = PosicionHelper.estanEnCuadrantesOpuestos(planeta3, planeta2);

        if (planeta1a2 || planeta1a3) {
            return estaElSolEntrePendientes(planeta1, planeta2, planeta3, posicionSol);
        }
        if (planeta2a1 || planeta2a3) {
            return estaElSolEntrePendientes(planeta2, planeta1, planeta3, posicionSol);
        }
        if (planeta3a1 || planeta3a2) {
            return estaElSolEntrePendientes(planeta3, planeta1, planeta2, posicionSol);
        }
        return false;
    }

    /**
     * Verifica si el sol está entre las pendientes formadas por las posiciones dadas.
     *
     * @param p1 la primera posición
     * @param p2 la segunda posición
     * @param p3 la tercera posición
     * @param posicionSol la posición del sol
     * @return true si el sol está entre las pendientes, false en caso contrario
     */
    private static boolean estaElSolEntrePendientes(Posicion p1, Posicion p2, Posicion p3, Posicion posicionSol) {
        double pendienteSol = PosicionHelper.getPendienteAUnPunto(p1,posicionSol);

        return (PosicionHelper.getPendienteAUnPunto(p1,p2) > pendienteSol) &&
                (PosicionHelper.getPendienteAUnPunto(p1,p3) < pendienteSol);
    }

    /**
     * Verifica si las posiciones dadas están alineadas.
     *
     * @param p1 la primera posición
     * @param p2 la segunda posición
     * @param p3 la tercera posición
     * @return true si todas las posiciones están alineadas, false en caso contrario
     */
    private static  boolean estanAlineados(Posicion p1, Posicion p2, Posicion p3) {
        return PosicionHelper.getPendienteAUnPunto(p1,p2) == PosicionHelper.getPendienteAUnPunto(p1,p3);
    }
}
