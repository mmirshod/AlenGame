package com.sunnydevs.alengame.db;

import java.sql.*;
import java.util.Objects;
import java.util.Arrays;

/**
 * Represents a result entry in the AlenGame application, stored in the database.
 */
public final class Result extends GetConnection {

    private final int id;
    private final int groupId;
    private final Timestamp playedTime;
    private final int score;
    private final String quizType;
    private final int userId;

    private Result(int id, int groupId, Timestamp playedTime, int score, String quizType, int userId) {
        this.id = id;
        this.groupId = groupId;
        this.playedTime = playedTime;
        this.score = score;
        this.quizType = quizType;
        this.userId = userId;
    }

    /**
     * Creates a new Result entry in the database.
     *
     * @param groupId  The group ID associated with the result.
     * @param userId   The user ID associated with the result.
     * @param score    The score achieved in the quiz.
     * @param quizType The type of quiz.
     * @throws SQLException If a database access error occurs.
     */
    public static void _new(int groupId, int userId, int score, String quizType) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO results (group_id, user_id, played_time, score, quiz_type) VALUES (?, ?, current_timestamp, ?, ?)"
        );
        preparedStatement.setInt(1, groupId);
        preparedStatement.setInt(2, userId);
        preparedStatement.setInt(3, score);
        preparedStatement.setString(4, quizType);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Retrieves a Result from the database based on the provided ID.
     *
     * @param id The ID of the result to be retrieved.
     * @return The retrieved Result object.
     * @throws SQLException If a database access error occurs.
     */
    public static Result get(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM results WHERE id=%d".formatted(id));
        ResultSet res = preparedStatement.executeQuery();
        preparedStatement.close();
        return _getFromResultSet(res);
    }

    /**
     * Creates a Result object from a ResultSet.
     *
     * @param res The ResultSet containing the Result data.
     * @return The created Result object.
     * @throws SQLException If a database access error occurs.
     */
    static Result _getFromResultSet(ResultSet res) throws SQLException {
        if (res.next()) {
            return new Result(
                    res.getInt("id"),
                    res.getInt("group_id"),
                    res.getTimestamp("played_time"),
                    res.getInt("score"),
                    res.getString("quiz_type"),
                    res.getInt("user_id")
            );
        }
        throw new RuntimeException();
    }

    /**
     * Gets the ID of the Result.
     *
     * @return The ID of the Result.
     */
    public int id() {
        return id;
    }

    /**
     * Gets the group ID associated with the Result.
     *
     * @return The group ID associated with the Result.
     */
    public int groupId() {
        return groupId;
    }

    /**
     * Gets the played time of the Result.
     *
     * @return The played time of the Result.
     */
    public Timestamp playedTime() {
        return playedTime;
    }

    /**
     * Gets the score achieved in the quiz.
     *
     * @return The score achieved in the quiz.
     */
    public int score() {
        return score;
    }

    /**
     * Gets the type of quiz.
     *
     * @return The type of quiz.
     */
    public String quizType() {
        return quizType;
    }

    /**
     * Gets the user ID associated with the Result.
     *
     * @return The user ID associated with the Result.
     */
    public int userId() {
        return userId;
    }

    /**
     * Checks if two Result objects are equal.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
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

    /**
     * Generates a hash code for the Result object.
     *
     * @return The hash code of the Result object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, playedTime, score);
    }

    /**
     * Generates a string representation of the Result object.
     *
     * @return The string representation of the Result object.
     */
    @Override
    public String toString() {
        return "Result[" +
                "id=" + id + ", " +
                "groupId=" + groupId + ", " +
                "playedTime=" + playedTime + ", " +
                "score=" + score + ']';
    }
}
