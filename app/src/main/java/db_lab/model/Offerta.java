package db_lab.model;

import java.sql.Date;

public class Offerta {
    private int idOfferta;
    private double ralProposta;
    private String benefit;
    private Date dataScadenzaOfferta;
    private String statoOfferta;
    private int idCandidatura;

    public Offerta() {
    }

    public Offerta(int idOfferta, double ralProposta, String benefit, Date dataScadenzaOfferta, String statoOfferta, int idCandidatura) {
        this.idOfferta = idOfferta;
        this.ralProposta = ralProposta;
        this.benefit = benefit;
        this.dataScadenzaOfferta = dataScadenzaOfferta;
        this.statoOfferta = statoOfferta;
        this.idCandidatura = idCandidatura;
    }

    public int getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(int idOfferta) {
        this.idOfferta = idOfferta;
    }

    public double getRalProposta() {
        return ralProposta;
    }

    public void setRalProposta(double ralProposta) {
        this.ralProposta = ralProposta;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public Date getDataScadenzaOfferta() {
        return dataScadenzaOfferta;
    }

    public void setDataScadenzaOfferta(Date dataScadenzaOfferta) {
        this.dataScadenzaOfferta = dataScadenzaOfferta;
    }

    public String getStatoOfferta() {
        return statoOfferta;
    }

    public void setStatoOfferta(String statoOfferta) {
        this.statoOfferta = statoOfferta;
    }

    public int getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(int idCandidatura) {
        this.idCandidatura = idCandidatura;
    }
}
