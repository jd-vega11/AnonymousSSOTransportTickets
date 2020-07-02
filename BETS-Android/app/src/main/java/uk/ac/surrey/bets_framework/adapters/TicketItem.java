package uk.ac.surrey.bets_framework.adapters;

import java.io.Serializable;

public class TicketItem implements Serializable {

    public static final String SERVICIOS = "SERVICIOS";

    public static final String TRANSMILENIO = "TRANSMILENIO";
    public static final String METRO = "METRO";
    public static final String SITP = "SITP";
    public static final String TREN = "TREN";

    public static final int TRANSMILENIO_TARIFA = 2500;
    public static final int METRO_TARIFA = 2800;
    public static final int SITP_TARIFA = 2300;

    public String medioTransporte;
    public int tarifa;
    public String detalleTren;
    public boolean validado;

    public TicketItem(String medioTransporte, int tarifa, String detalleTren) {
        this.medioTransporte = medioTransporte;
        this.tarifa = tarifa;
        this.detalleTren = detalleTren;
        this.validado = false;
    }

}
