package db_lab.model;

import java.sql.Date;

public class PosizioneLavorativa {
    private int idPosizione;
    private String titolo;
    private String modalita;
    private String rangeRal;
    private Date dataScadenza;
    private int idAzienda;

    public PosizioneLavorativa() {
    }

    public PosizioneLavorativa(int idPosizione, String titolo, String modalita, String rangeRal, Date dataScadenza, int idAzienda) {
        this.idPosizione = idPosizione;
        this.titolo = titolo;
        this.modalita = modalita;
        this.rangeRal = rangeRal;
        this.dataScadenza = dataScadenza;
        this.idAzienda = idAzienda;
    }

    public int getIdPosizione() {
        return idPosizione;
    }

    public void setIdPosizione(int idPosizione) {
        this.idPosizione = idPosizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getModalita() {
        return modalita;
    }

    public void setModalita(String modalita) {
        this.modalita = modalita;
    }

    public String getRangeRal() {
        return rangeRal;
    }

    public void setRangeRal(String rangeRal) {
        this.rangeRal = rangeRal;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public int getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(int idAzienda) {
        this.idAzienda = idAzienda;
    }
}
