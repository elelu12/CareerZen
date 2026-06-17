package db_lab.data;

import db_lab.model.Candidato;
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
}
