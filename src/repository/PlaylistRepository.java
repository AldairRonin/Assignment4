package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Media;
import model.Podcast;
import model.Song;
import utils.DatabaseConnection;

public class PlaylistRepository {
    // создание нового плейлиста
    public void createPlaylist(String name) {
        String sql = "INSERT INTO playlist (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create playlist", e);
        }
    }


    // получение названия плейлиста по id
    public String getPlaylistNameById(int playlistId) {
        String sql = "SELECT name FROM playlist WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get playlist name", e);
        }
    }

    // добавление media в плейлист
    public void addMediaToPlaylist(int playlistId, int mediaId) {
        String sql = "INSERT INTO playlist_media (playlist_id, media_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);
            stmt.setInt(2, mediaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add media to playlist", e);
        }
    }

    // удаление media из плейлиста
    public void removeMediaFromPlaylist(int playlistId, int mediaId) {
        String sql = "DELETE FROM playlist_media WHERE playlist_id = ? AND media_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);
            stmt.setInt(2, mediaId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove media from playlist", e);
        }
    }

    // проверка, находится ли media в плейлисте
    public boolean isMediaInPlaylist(int playlistId, int mediaId) {
        String sql = """
            SELECT 1 FROM playlist_media
            WHERE playlist_id = ? AND media_id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);
            stmt.setInt(2, mediaId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check media in playlist", e);
        }
    }

    // получение всех media из плейлиста по id плейлиста
    public List<Media> getMediaByPlaylistId(int playlistId) {
        List<Media> result = new ArrayList<>();

        String sql = """
            SELECT m.id, m.title, m.duration, m.type
            FROM playlist_media pm
            JOIN media m ON pm.media_id = m.id
            WHERE pm.playlist_id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    int duration = rs.getInt("duration");
                    String type = rs.getString("type");

                    Media media;
                    if ("song".equals(type)) {
                        media = new Song(id, title, duration);
                    } else {
                        media = new Podcast(id, title, duration);
                    }

                    result.add(media);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get playlist media", e);
        }

        return result;
    }
}
