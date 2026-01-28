import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import java.util.List;
import java.util.Scanner;
import model.Media;
import service.MediaService;
import service.PlaylistService;

public class Main {

    public static void main(String[] args) {

        try {

            Scanner scanner = new Scanner(System.in);

            int playlistId = 2; // выбери нужный плейлист для теста

            PlaylistService playlistService = new PlaylistService();
            MediaService mediaService = new MediaService();

            // проверка существования плейлиста
            String playlistName = playlistService.getPlaylistNameById(playlistId);
            if (playlistName == null) {
                throw new ResourceNotFoundException("Playlist not found.");
            }

            System.out.println("PLAYLIST = \"" + playlistName + "\"");
            printPlaylistMedia(playlistService, playlistId);

            // меню управления
            System.out.println();
            System.out.println("(input numbers for managing):");
            System.out.println("1 - add media");
            System.out.println("2 - delete media");
            System.out.println("3 - exit");

            System.out.print("User choice: ");
            String choice = scanner.nextLine();

            // проверка выбора
            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                throw new InvalidInputException(
                        "Invalid input. Please enter 1, 2 or 3."
                );
            }

            // выход
            if (choice.equals("3")) {
                System.out.println("Exit. No changes made.");
                return;
            }

            // добавить медиа
            if (choice.equals("1")) {

                System.out.println();
                System.out.println("AVAILABLE MEDIA:");

                List<Media> allMedia = mediaService.getAllMedia();
                boolean hasAvailable = false;

                for (Media media : allMedia) {
                    if (!playlistService.isMediaInPlaylist(playlistId, media.getId())) {
                        System.out.println(
                                media.getId() + " - \"" +
                                media.getTitle() + "\" (" +
                                media.getType() + ", " +
                                media.getDuration() + " secs)"
                        );
                        hasAvailable = true;
                    }
                }

                if (!hasAvailable) {
                    System.out.println("No available media to add.");
                    return;
                }

                System.out.print("Enter media id to add or type \"exit\": ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exit. No changes made.");
                    return;
                }

                int mediaId = parseIdOrThrow(input);

                if (!mediaService.mediaExists(mediaId)) {
                    throw new ResourceNotFoundException(
                            "Media with id " + mediaId + " not found."
                    );
                }

                if (playlistService.isMediaInPlaylist(playlistId, mediaId)) {
                    throw new InvalidInputException(
                            "Media already exists in this playlist."
                    );
                }

                playlistService.addMedia(playlistId, mediaId);
                System.out.println("Media added.");
            }

            // удалить медиа
            if (choice.equals("2")) {

                System.out.print("Enter media id to delete or type \"exit\": ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exit. No changes made.");
                    return;
                }

                int mediaId = parseIdOrThrow(input);

                if (!playlistService.isMediaInPlaylist(playlistId, mediaId)) {
                    throw new ResourceNotFoundException(
                            "Media with id " + mediaId + " is not in this playlist."
                    );
                }

                playlistService.removeMedia(playlistId, mediaId);
                System.out.println("Media removed.");
            }

            // обновлённый плейлист
            System.out.println();
            System.out.println("UPDATED PLAYLIST:");
            printPlaylistMedia(playlistService, playlistId);

        } catch (InvalidInputException | ResourceNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // 

    private static int parseIdOrThrow(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Input cannot be empty.");
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(
                    "Invalid input. Please enter a numeric id."
            );
        }
    }

    private static void printPlaylistMedia(PlaylistService playlistService, int playlistId) {

        List<Media> mediaList = playlistService.getPlaylistMedia(playlistId);

        if (mediaList.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        System.out.println("MEDIA:");
        for (Media media : mediaList) {
            System.out.println(
                    media.getId() + " - \"" +
                    media.getTitle() + "\" (" +
                    media.getType() + ", " +
                    media.getDuration() + " secs)"
            );
        }
    }
}
