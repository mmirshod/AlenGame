package com.sunnydevs.alengame.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public final class Quiz extends GetConnection {
    private final int id;
    private final int maxScore;
    private final Timestamp lastPlayed;
    private final int timesPlayed;

    private Quiz(int id, int maxScore, Timestamp lastPlayed, int timesPlayed) {
        this.id = id;
        this.maxScore = maxScore;
        this.lastPlayed = lastPlayed;
        this.timesPlayed = timesPlayed;
    }

    static void _new(int timesPlayed, int userId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO quiz (last_played, times_played, user_id) VALUES (current_timestamp, %d, %d)".formatted(timesPlayed, userId));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    static void _delete(int integer) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM quiz WHERE id=%d", integer);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static Quiz get(int integer) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM quiz where id=%d", integer);
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    public static Quiz first() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM quiz ORDER BY id LIMIT 1");
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }
    public static ArrayList<Person> getPeople(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT p.* FROM person p INNER JOIN people_quiz_relation pqr on p.id = pqr.person_id WHERE quiz_id=%d".formatted(id)
        );
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();

        ArrayList<Person> ppl = new ArrayList<>();
        while (res.next()) {
            ppl.add(Person._getFromResultSet(res));
        }

        return ppl;
    }

    public static ArrayList<Result> getResults(int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
            "SELECT * FROM results WHERE quiz_id=%d".formatted(id)
        );
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();

        ArrayList<Result> ppl = new ArrayList<>();
        while (res.next()) {
            ppl.add(Result._getFromResultSet(res));
        }

        return ppl;
    }

    static Quiz _getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Quiz(
            resultSet.getInt("id"),
            resultSet.getInt("max_score"),
            resultSet.getTimestamp("last_played"),
            resultSet.getInt("times_played")
        );
    }

    public int id() {
        return id;
    }

    public int maxScore() {
        return maxScore;
    }

    public Timestamp lastPlayed() {
        return lastPlayed;
    }

    public int timesPlayed() {
        return timesPlayed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Quiz) obj;
        return this.id == that.id &&
                this.maxScore == that.maxScore &&
                Objects.equals(this.lastPlayed, that.lastPlayed) &&
                this.timesPlayed == that.timesPlayed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maxScore, lastPlayed, timesPlayed);
    }

    @Override
    public String toString() {
        return "Quiz[" +
                "id=" + id + ", " +
                "maxScore=" + maxScore + ", " +
                "lastPlayed=" + lastPlayed + ", " +
                "timesPlayed=" + timesPlayed + ']';
    }

}
