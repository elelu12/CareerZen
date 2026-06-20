package db_lab.data;

import db_lab.model.Candidato;
import db_lab.model.Amministratore;
import db_lab.model.Azienda;
import db_lab.model.PosizioneLavorativa;
import db_lab.model.Colloquio;
import db_lab.model.Offerta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CareerZenDAO {

    public Candidato authenticateCandidato(String username, String password) {
        String query = "SELECT * FROM Candidato WHERE NomeUtente = ? AND Password = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, username, password);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return new Candidato(
                    resultSet.getInt("ID_Candidato"),
                    resultSet.getString("NomeUtente"),
                    resultSet.getString("Password"),
                    resultSet.getString("Nome"),
                    resultSet.getString("Cognome"),
                    resultSet.getString("Email")
                );
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    public List<String> getDashboardCandidato(int idCandidato) {
        List<String> dashboard = new ArrayList<>();
        String query = "SELECT " +
                       "    c.ID_Candidatura, " +
                       "    pl.Titolo AS Posizione, " +
                       "    a.Nome_Azienda AS Azienda, " +
                       "    c.Data_Creazione AS Inviata_Il, " +
                       "    s.Stato AS Stato_Attuale, " +
                       "    s.Data_Cambio AS Ultimo_Aggiornamento " +
                       "FROM Candidatura c " +
                       "JOIN PosizioneLavorativa pl ON c.ID_Posizione = pl.ID_Posizione " +
                       "JOIN Azienda a ON pl.ID_Azienda = a.ID_Azienda " +
                       "JOIN StoricoStati s ON c.ID_Candidatura = s.ID_Candidatura " +
                       "WHERE c.ID_Candidato = ? " +
                       "  AND s.Data_Cambio = ( " +
                       "      SELECT MAX(Data_Cambio) " +
                       "      FROM StoricoStati " +
                       "      WHERE ID_Candidatura = c.ID_Candidatura " +
                       "  ) " +
                       "ORDER BY s.Data_Cambio DESC";

        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idCandidatura = resultSet.getInt("ID_Candidatura");
                String posizione = resultSet.getString("Posizione");
                String azienda = resultSet.getString("Azienda");
                String statoAttuale = resultSet.getString("Stato_Attuale");

                String formatted = "Candidatura #" + idCandidatura + " | " + posizione + " presso " + azienda + " | Stato: " + statoAttuale;
                dashboard.add(formatted);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return dashboard;
    }

    public List<String> getAnnunciCompatibili(int idCandidato) {
        String query = "SELECT DISTINCT\n" +
                       "    pl.ID_Posizione,\n" +
                       "    pl.Titolo,\n" +
                       "    a.Nome_Azienda,\n" +
                       "    pl.Modalita,\n" +
                       "    pl.Range_RAL\n" +
                       "FROM PosizioneLavorativa pl\n" +
                       "JOIN Azienda a ON pl.ID_Azienda = a.ID_Azienda\n" +
                       "JOIN Requisito r ON pl.ID_Posizione = r.ID_Posizione\n" +
                       "JOIN Competenza c ON r.ID_Skill = c.ID_Skill\n" +
                       "WHERE c.ID_Candidato = ?\n" +
                       "  AND c.Livello >= r.Livello_Minimo;";
        List<String> annunci = new ArrayList<>();

        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idPosizione = resultSet.getInt("ID_Posizione");
                String titolo = resultSet.getString("Titolo");
                String azienda = resultSet.getString("Nome_Azienda");
                String modalita = resultSet.getString("Modalita");
                String ral = resultSet.getString("Range_RAL");
                if (ral == null) {
                    ral = "Non specificata";
                }

                String formatted = "ID: " + idPosizione + " | Titolo: " + titolo + " | Azienda: " + azienda + " | Modalità: " + modalita + " | RAL: " + ral;
                annunci.add(formatted);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return annunci;
    }

    public boolean iscriviCandidato(Candidato c) {
        String query = "INSERT INTO Candidato (NomeUtente, Password, Nome, Cognome, Email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, c.getNomeUtente(), c.getPassword(), c.getNome(), c.getCognome(), c.getEmail())) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean registraAzienda(Azienda a) {
        String query = "INSERT INTO Azienda (Nome_Azienda, Settore, Sede_Principale) VALUES (?, ?, ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, a.getNomeAzienda(), a.getSettore(), a.getSedePrincipale())) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean associaSkill(int idCandidato, int idSkill, int livello) {
        String query = "INSERT INTO Competenza (ID_Candidato, ID_Skill, Livello) VALUES (?, ?, ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato, idSkill, livello)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean pubblicaPosizioneConRequisiti(PosizioneLavorativa p, java.util.Map<Integer, Integer> requisiti) {
        String queryPos = "INSERT INTO PosizioneLavorativa (Titolo, Modalita, Range_RAL, Data_Scadenza_Annuncio, ID_Azienda) VALUES (?, ?, ?, ?, ?)";
        String queryId = "SELECT LAST_INSERT_ID()";
        String queryReq = "INSERT INTO Requisito (ID_Posizione, ID_Skill, Livello_Minimo) VALUES (?, ?, ?)";

        try (Connection connection = DAOUtils.localMySQLConnection("", "", "")) {
            try {
                connection.setAutoCommit(false);

                try (PreparedStatement stmtPos = DAOUtils.prepare(connection, queryPos, p.getTitolo(), p.getModalita(), p.getRangeRal(), p.getDataScadenza(), p.getIdAzienda())) {
                    stmtPos.executeUpdate();
                }

                int idPosizione = 0;
                try (PreparedStatement stmtId = DAOUtils.prepare(connection, queryId);
                     ResultSet rs = stmtId.executeQuery()) {
                    if (rs.next()) {
                        idPosizione = rs.getInt(1);
                    }
                }

                try (PreparedStatement stmtReq = connection.prepareStatement(queryReq)) {
                    for (java.util.Map.Entry<Integer, Integer> entry : requisiti.entrySet()) {
                        stmtReq.setInt(1, idPosizione);
                        stmtReq.setInt(2, entry.getKey());
                        stmtReq.setInt(3, entry.getValue());
                        stmtReq.executeUpdate();
                    }
                }

                connection.commit();
                return true;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    
                }
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean inviaCandidatura(int idCandidato, int idPosizione) {
        String queryCand = "INSERT INTO Candidatura (Data_Creazione, ID_Candidato, ID_Posizione) VALUES (CURRENT_DATE(), ?, ?)";
        String queryId = "SELECT LAST_INSERT_ID()";
        String queryStato = "INSERT INTO StoricoStati (ID_Candidatura, Data_Cambio, Stato) VALUES (?, CURRENT_TIMESTAMP(), 'Inviata')";

        try (Connection connection = DAOUtils.localMySQLConnection("", "", "")) {
            try {
                connection.setAutoCommit(false);

                try (PreparedStatement stmtCand = DAOUtils.prepare(connection, queryCand, idCandidato, idPosizione)) {
                    stmtCand.executeUpdate();
                }

                int idCandidatura = 0;
                try (PreparedStatement stmtId = DAOUtils.prepare(connection, queryId);
                     ResultSet rs = stmtId.executeQuery()) {
                    if (rs.next()) {
                        idCandidatura = rs.getInt(1);
                    }
                }

                try (PreparedStatement stmtStato = DAOUtils.prepare(connection, queryStato, idCandidatura)) {
                    stmtStato.executeUpdate();
                }

                connection.commit();
                return true;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    
                }
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean inserisciColloquio(Colloquio c) {
        String query = "INSERT INTO Colloquio (Data_Ora, Tipo_Colloquio, Esito, Recruiter, Note, ID_Candidatura) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, c.getDataOra(), c.getTipoColloquio(), c.getEsito(), c.getRecruiter(), c.getNote(), c.getIdCandidatura())) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean avanzaStatoCandidatura(int idCandidatura, String stato) {
        String query = "INSERT INTO StoricoStati (ID_Candidatura, Data_Cambio, Stato) VALUES (?, CURRENT_TIMESTAMP(), ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidatura, stato)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean inserisciOfferta(Offerta o) {
        String query = "INSERT INTO Offerta (RAL_Proposta, Benefit, Data_Scadenza_Offerta, Stato_Offerta, ID_Candidatura) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, o.getRalProposta(), o.getBenefit(), o.getDataScadenzaOfferta(), o.getStatoOfferta(), o.getIdCandidatura())) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> getStoricoCompleto(int idCandidatura) {
        List<String> storico = new ArrayList<>();
        String queryStati = "SELECT Data_Cambio, Stato FROM StoricoStati WHERE ID_Candidatura = ? ORDER BY Data_Cambio DESC";
        String queryColloqui = "SELECT Data_Ora, Tipo_Colloquio, Esito, Recruiter, Note FROM Colloquio WHERE ID_Candidatura = ? ORDER BY Data_Ora ASC";
        String queryOfferte = "SELECT RAL_Proposta, Benefit, Data_Scadenza_Offerta, Stato_Offerta FROM Offerta WHERE ID_Candidatura = ?";

        try (Connection connection = DAOUtils.localMySQLConnection("", "", "")) {
            
            try (PreparedStatement stmt1 = DAOUtils.prepare(connection, queryStati, idCandidatura);
                 ResultSet rs1 = stmt1.executeQuery()) {
                while (rs1.next()) {
                    var dataCambio = rs1.getTimestamp("Data_Cambio");
                    String stato = rs1.getString("Stato");
                    storico.add("[STATO] " + dataCambio + " -> " + stato);
                }
            }

            try (PreparedStatement stmt2 = DAOUtils.prepare(connection, queryColloqui, idCandidatura);
                 ResultSet rs2 = stmt2.executeQuery()) {
                while (rs2.next()) {
                    var dataOra = rs2.getTimestamp("Data_Ora");
                    String tipo = rs2.getString("Tipo_Colloquio");
                    String recruiter = rs2.getString("Recruiter");
                    String esito = rs2.getString("Esito");
                    storico.add("[COLLOQUIO] " + dataOra + " | " + tipo + " con " + recruiter + " | Esito: " + esito);
                }
            }

            try (PreparedStatement stmt3 = DAOUtils.prepare(connection, queryOfferte, idCandidatura);
                 ResultSet rs3 = stmt3.executeQuery()) {
                while (rs3.next()) {
                    double ral = rs3.getDouble("RAL_Proposta");
                    var scadenza = rs3.getDate("Data_Scadenza_Offerta");
                    String statoOfferta = rs3.getString("Stato_Offerta");
                    storico.add("[OFFERTA] RAL: " + ral + " | Scadenza: " + scadenza + " | Stato: " + statoOfferta);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return storico;
    }

    public boolean eliminaAzienda(int idAzienda) {
        String query = "DELETE FROM Azienda WHERE ID_Azienda = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idAzienda)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean aggiornaEsitoColloquio(int idColloquio, String esito, String note) {
        String query = "UPDATE Colloquio SET Esito = ?, Note = ? WHERE ID_Colloquio = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, esito, note, idColloquio)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean aggiornaStatoOfferta(int idOfferta, String statoOfferta) {
        String query = "UPDATE Offerta SET Stato_Offerta = ? WHERE ID_Offerta = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, statoOfferta, idOfferta)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> generaReportAziendale(int idAzienda) {
        List<String> report = new ArrayList<>();
        String query = "SELECT\n" +
                       "    a.Nome_Azienda,\n" +
                       "    COUNT(DISTINCT pl.ID_Posizione) AS Posizioni_Aperte,\n" +
                       "    COUNT(DISTINCT c.ID_Candidatura) AS Candidature_Ricevute,\n" +
                       "    COUNT(DISTINCT col.ID_Colloquio) AS Colloqui_Effettuati,\n" +
                       "    COUNT(DISTINCT o.ID_Offerta) AS Offerte_Emesse\n" +
                       "FROM Azienda a\n" +
                       "LEFT JOIN PosizioneLavorativa pl ON a.ID_Azienda = pl.ID_Azienda\n" +
                       "LEFT JOIN Candidatura c ON pl.ID_Posizione = c.ID_Posizione\n" +
                       "LEFT JOIN Colloquio col ON c.ID_Candidatura = col.ID_Candidatura\n" +
                       "LEFT JOIN Offerta o ON c.ID_Candidatura = o.ID_Candidatura\n" +
                       "WHERE a.ID_Azienda = ?\n" +
                       "GROUP BY a.ID_Azienda;";

        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idAzienda);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nomeAzienda = resultSet.getString("Nome_Azienda");
                int posizioni = resultSet.getInt("Posizioni_Aperte");
                int candidature = resultSet.getInt("Candidature_Ricevute");
                int colloqui = resultSet.getInt("Colloqui_Effettuati");
                int offerte = resultSet.getInt("Offerte_Emesse");

                String line = "Report per " + nomeAzienda + ": " + posizioni + " posizioni aperte, " + candidature + " candidature, " + colloqui + " colloqui, " + offerte + " offerte.";
                report.add(line);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return report;
    }

    public boolean aggiornaLivelloSkill(int idCandidato, int idSkill, int nuovoLivello) {
        String query = "UPDATE Competenza SET Livello = ? WHERE ID_Candidato = ? AND ID_Skill = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, nuovoLivello, idCandidato, idSkill)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Amministratore authenticateAmministratore(String username, String password) {
        String query = "SELECT * FROM Amministratore WHERE NomeUtente = ? AND Password = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, username, password);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return new Amministratore(
                    resultSet.getInt("ID_Amministratore"),
                    resultSet.getString("NomeUtente"),
                    resultSet.getString("Password"),
                    resultSet.getString("Nome"),
                    resultSet.getString("Cognome"),
                    resultSet.getString("Email")
                );
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    public List<String> getElencoSkill() {
        List<String> skills = new ArrayList<>();
        String query = "SELECT ID_Skill, Nome_Skill FROM Skill";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idSkill = resultSet.getInt("ID_Skill");
                String nomeSkill = resultSet.getString("Nome_Skill");
                skills.add(idSkill + " - " + nomeSkill);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return skills;
    }

    public List<String> getSkillCandidato(int idCandidato) {
        List<String> skills = new ArrayList<>();
        String query = "SELECT s.Nome_Skill, c.Livello FROM Competenza c JOIN Skill s ON c.ID_Skill = s.ID_Skill WHERE c.ID_Candidato = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nomeSkill = resultSet.getString("Nome_Skill");
                int livello = resultSet.getInt("Livello");
                skills.add(" - " + nomeSkill + " (Livello " + livello + ")");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return skills;
    }

    public List<String> getAllAziende() {
        List<String> aziende = new ArrayList<>();
        String query = "SELECT ID_Azienda, Nome_Azienda, Settore FROM Azienda";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Azienda");
                String nome = resultSet.getString("Nome_Azienda");
                String settore = resultSet.getString("Settore");
                aziende.add("ID: " + id + " | " + nome + " (" + settore + ")");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return aziende;
    }

    public List<String> getElencoAziende() {
        List<String> aziende = new ArrayList<>();
        String query = "SELECT ID_Azienda, Nome_Azienda FROM Azienda";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Azienda");
                String nome = resultSet.getString("Nome_Azienda");
                aziende.add(id + " - " + nome);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return aziende;
    }

    public List<String> getAllPosizioniLavorativeFormatted() {
        List<String> posizioni = new ArrayList<>();
        String query = "SELECT pl.ID_Posizione, pl.Titolo, a.Nome_Azienda " +
                       "FROM PosizioneLavorativa pl " +
                       "JOIN Azienda a ON pl.ID_Azienda = a.ID_Azienda";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Posizione");
                String titolo = resultSet.getString("Titolo");
                String nomeAzienda = resultSet.getString("Nome_Azienda");
                posizioni.add(id + " - " + titolo + " (" + nomeAzienda + ")");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return posizioni;
    }

    public List<String> getAnnuncioById(int idPosizione) {
        List<String> annunci = new ArrayList<>();
        String query = "SELECT pl.ID_Posizione, pl.Titolo, pl.Modalita, pl.Range_RAL, pl.Data_Scadenza_Annuncio, a.Nome_Azienda " +
                       "FROM PosizioneLavorativa pl " +
                       "JOIN Azienda a ON pl.ID_Azienda = a.ID_Azienda " +
                       "WHERE pl.ID_Posizione = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idPosizione);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("ID_Posizione");
                String titolo = resultSet.getString("Titolo");
                String modalita = resultSet.getString("Modalita");
                String ral = resultSet.getString("Range_RAL");
                var data = resultSet.getDate("Data_Scadenza_Annuncio");
                String nomeAzienda = resultSet.getString("Nome_Azienda");
                annunci.add("ID Annuncio: " + id + " | " + titolo + " (" + nomeAzienda + ") | " + modalita + " | RAL: " + ral + " | Scadenza: " + data);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return annunci;
    }

    public List<String> getAnnunciByAzienda(int idAzienda) {
        List<String> annunci = new ArrayList<>();
        String query = "SELECT ID_Posizione, Titolo, Modalita, Range_RAL, Data_Scadenza_Annuncio FROM PosizioneLavorativa WHERE ID_Azienda = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idAzienda);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Posizione");
                String titolo = resultSet.getString("Titolo");
                String modalita = resultSet.getString("Modalita");
                String ral = resultSet.getString("Range_RAL");
                var data = resultSet.getDate("Data_Scadenza_Annuncio");
                annunci.add("ID Annuncio: " + id + " | " + titolo + " (" + modalita + ") | RAL: " + ral + " | Scadenza: " + data);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return annunci;
    }

    public List<String> getAllCandidature() {
        List<String> list = new ArrayList<>();
        String query = "SELECT c.ID_Candidatura, u.Nome, u.Cognome, p.Titolo " +
                       "FROM Candidatura c " +
                       "JOIN Candidato u ON c.ID_Candidato = u.ID_Candidato " +
                       "JOIN PosizioneLavorativa p ON c.ID_Posizione = p.ID_Posizione";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Candidatura");
                String nome = resultSet.getString("Nome");
                String cognome = resultSet.getString("Cognome");
                String titolo = resultSet.getString("Titolo");
                list.add(id + " - " + nome + " " + cognome + " per " + titolo);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    public List<String> getCandidatureByCandidato(int idCandidato) {
        List<String> list = new ArrayList<>();
        String query = "SELECT c.ID_Candidatura, p.Titolo, a.Nome_Azienda " +
                       "FROM Candidatura c " +
                       "JOIN PosizioneLavorativa p ON c.ID_Posizione = p.ID_Posizione " +
                       "JOIN Azienda a ON p.ID_Azienda = a.ID_Azienda " +
                       "WHERE c.ID_Candidato = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Candidatura");
                String titolo = resultSet.getString("Titolo");
                String azienda = resultSet.getString("Nome_Azienda");
                list.add(id + " - " + titolo + " presso " + azienda);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }


    public List<String> getAllColloqui() {
        List<String> list = new ArrayList<>();
        String query = "SELECT c.ID_Colloquio, c.Data_Ora, u.Nome, u.Cognome " +
                       "FROM Colloquio c " +
                       "JOIN Candidatura cand ON c.ID_Candidatura = cand.ID_Candidatura " +
                       "JOIN Candidato u ON cand.ID_Candidato = u.ID_Candidato";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Colloquio");
                var data = resultSet.getTimestamp("Data_Ora");
                String nome = resultSet.getString("Nome");
                String cognome = resultSet.getString("Cognome");
                list.add(id + " - " + nome + " " + cognome + " (" + data + ")");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    public List<String> getColloquiByCandidato(int idCandidato) {
        List<String> list = new ArrayList<>();
        String query = "SELECT c.ID_Colloquio, c.Data_Ora, p.Titolo, a.Nome_Azienda " +
                       "FROM Colloquio c " +
                       "JOIN Candidatura cand ON c.ID_Candidatura = cand.ID_Candidatura " +
                       "JOIN PosizioneLavorativa p ON cand.ID_Posizione = p.ID_Posizione " +
                       "JOIN Azienda a ON p.ID_Azienda = a.ID_Azienda " +
                       "WHERE cand.ID_Candidato = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Colloquio");
                var data = resultSet.getTimestamp("Data_Ora");
                String titolo = resultSet.getString("Titolo");
                String azienda = resultSet.getString("Nome_Azienda");
                list.add(id + " - Colloquio per " + titolo + " (" + azienda + ") in data " + data);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    public List<String> getOfferteRicevute(int idCandidato) {
        List<String> list = new ArrayList<>();
        String query = "SELECT o.ID_Offerta, pl.Titolo, a.Nome_Azienda, o.RAL_Proposta " +
                       "FROM Offerta o " +
                       "JOIN Candidatura c ON o.ID_Candidatura = c.ID_Candidatura " +
                       "JOIN PosizioneLavorativa pl ON c.ID_Posizione = pl.ID_Posizione " +
                       "JOIN Azienda a ON pl.ID_Azienda = a.ID_Azienda " +
                       "WHERE c.ID_Candidato = ? AND (o.Stato_Offerta NOT IN ('Accettata', 'Rifiutata') OR o.Stato_Offerta IS NULL)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idCandidato);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID_Offerta");
                String titolo = resultSet.getString("Titolo");
                String azienda = resultSet.getString("Nome_Azienda");
                double ral = resultSet.getDouble("RAL_Proposta");
                list.add(id + " - " + titolo + " presso " + azienda + " (RAL: " + ral + ")");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return list;
    }

    public boolean inserisciSkill(String nome, String area) {
        String query = "INSERT INTO Skill (Nome, Area) VALUES (?, ?)";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, nome, area)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminaPosizione(int idPosizione) {
        String query = "DELETE FROM PosizioneLavorativa WHERE ID_Posizione = ?";
        try (Connection connection = DAOUtils.localMySQLConnection("", "", "");
             PreparedStatement statement = DAOUtils.prepare(connection, query, idPosizione)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
