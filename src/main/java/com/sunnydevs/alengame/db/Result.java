package com.sunnydevs.alengame.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

public final class Result extends GetConnection {
    private final int id;
    private final int quizId;
    private final Timestamp playedTime;
    private final int score;

    public static void _new(int quizId, int score) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO results (quiz_id, played_time, score) VALUES (%d, current_timestamp, %d)".formatted(quizId, score));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static Result get(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM results WHERE id=%d".formatted(id));
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    static Result _getFromResultSet(ResultSet res) throws SQLException {
        return new Result(
            res.getInt("id"),
            res.getInt("quiz_id"),
            res.getTimestamp("played_time"),
            res.getInt("score")
        );
    }

    private Result(int id, int quizId, Timestamp playedTime, int score) {
        this.id = id;
        this.quizId = quizId;
        this.playedTime = playedTime;
        this.score = score;
    }

    public int id() {
        return id;
    }

    public int quizId() {
        return quizId;
    }

    public Timestamp playedTime() {
        return playedTime;
    }

    public int score() {
        return score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Result) obj;
        return this.id == that.id &&
                this.quizId == that.quizId &&
                Objects.equals(this.playedTime, that.playedTime) &&
                this.score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quizId, playedTime, score);
    }

    @Override
    public String toString() {
        return "Result[" +
                "id=" + id + ", " +
                "quizId=" + quizId + ", " +
                "playedTime=" + playedTime + ", " +
                "score=" + score + ']';
    }

}
