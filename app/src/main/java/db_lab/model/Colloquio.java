package db_lab.model;

import java.sql.Timestamp;

public class Colloquio {
    private int idColloquio;
    private Timestamp dataOra;
    private String tipoColloquio;
    private String esito;
    private String recruiter;
    private String note;
    private int idCandidatura;

    public Colloquio() {
    }

    public Colloquio(int idColloquio, Timestamp dataOra, String tipoColloquio, String esito, String recruiter, String note, int idCandidatura) {
        this.idColloquio = idColloquio;
        this.dataOra = dataOra;
        this.tipoColloquio = tipoColloquio;
        this.esito = esito;
        this.recruiter = recruiter;
        this.note = note;
        this.idCandidatura = idCandidatura;
    }

    public int getIdColloquio() {
        return idColloquio;
    }

    public void setIdColloquio(int idColloquio) {
        this.idColloquio = idColloquio;
    }

    public Timestamp getDataOra() {
        return dataOra;
    }

    public void setDataOra(Timestamp dataOra) {
        this.dataOra = dataOra;
    }

    public String getTipoColloquio() {
        return tipoColloquio;
    }

    public void setTipoColloquio(String tipoColloquio) {
        this.tipoColloquio = tipoColloquio;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(String recruiter) {
        this.recruiter = recruiter;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdCandidatura() {
        return idCandidatura;
    }

    public void setIdCandidatura(int idCandidatura) {
        this.idCandidatura = idCandidatura;
    }
}
