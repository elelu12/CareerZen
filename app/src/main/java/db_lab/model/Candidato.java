package db_lab.model;

public class Candidato {
    private int idCandidato;
    private String nomeUtente;
    private String password;
    private String nome;
    private String cognome;
    private String email;

    // No-arguments constructor
    public Candidato() {
    }

    // All-arguments constructor
    public Candidato(int idCandidato, String nomeUtente, String password, String nome, String cognome, String email) {
        this.idCandidato = idCandidato;
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    // Getters and Setters
    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
