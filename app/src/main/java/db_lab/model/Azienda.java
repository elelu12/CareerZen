package db_lab.model;

public class Azienda {
    private int idAzienda;
    private String nomeAzienda;
    private String settore;
    private String sedePrincipale;

    public Azienda() {
    }

    public Azienda(int idAzienda, String nomeAzienda, String settore, String sedePrincipale) {
        this.idAzienda = idAzienda;
        this.nomeAzienda = nomeAzienda;
        this.settore = settore;
        this.sedePrincipale = sedePrincipale;
    }

    public int getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(int idAzienda) {
        this.idAzienda = idAzienda;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }

    public String getSettore() {
        return settore;
    }

    public void setSettore(String settore) {
        this.settore = settore;
    }

    public String getSedePrincipale() {
        return sedePrincipale;
    }

    public void setSedePrincipale(String sedePrincipale) {
        this.sedePrincipale = sedePrincipale;
    }
}
