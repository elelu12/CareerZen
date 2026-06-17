package db_lab.model;

import java.sql.Date;

public class Candidatura {
    private int idCandidatura;
    private Date dataCreazione;
    private int idCandidato;
    private int idPosizione;
    private String statoAttuale;

    // No-arguments constructor
    public Candidatura() {
    }

    // All-arguments constructor
    public Candidatura(int idCandidatura, Date dataCreazione, int idCandidato, int idPosizione, String statoAttuale) {
        this.idCandidatura = idCandidatura;
        this.dataCreazione = dataCreazione;
        this.idCandidato = idCandidato;
        this.idPosizione = idPosizione;
        this.statoAttuale = statoAttuale;
    }

    // Getters and Setters
    public int getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(int idCandidatura) {
        this.idCandidatura = idCandidatura;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public int getIdPosizione() {
        return idPosizione;
    }

    public void setIdPosizione(int idPosizione) {
        this.idPosizione = idPosizione;
    }

    public String getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(String statoAttuale) {
        this.statoAttuale = statoAttuale;
    }
}
