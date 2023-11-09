package com.sunnydevs.alengame.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

public final class Result extends GetConnection {
    private final int id;
    private final int groupId;
    private final Timestamp playedTime;
    private final int score;
    private final String quizType;

    public static void _new(int groupId, int score, String quizType) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO results (group_id, played_time, score, quiz_type) VALUES (%d, current_timestamp, %d, %s)".formatted(groupId, score, quizType));
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
            res.getInt("group_id"),
            res.getTimestamp("played_time"),
            res.getInt("score"),
            res.getString("quiz_type")
        );
    }

    private Result(int id, int groupId, Timestamp playedTime, int score, String quizType) {
        this.id = id;
        this.groupId = groupId;
        this.playedTime = playedTime;
        this.score = score;
        this.quizType = quizType;
    }

    public int id() {
        return id;
    }

    public int groupId() {
        return groupId;
    }

    public Timestamp playedTime() {
        return playedTime;
    }

    public int score() {
        return score;
    }

    public String quizType() {
        return quizType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Result) obj;
        return this.id == that.id &&
                this.groupId == that.groupId &&
                Objects.equals(this.playedTime, that.playedTime) &&
                this.score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, playedTime, score);
    }

    @Override
    public String toString() {
        return "Result[" +
                "id=" + id + ", " +
                "groupId=" + groupId + ", " +
                "playedTime=" + playedTime + ", " +
                "score=" + score + ']';
    }

}
