import java.util.List;
import java.util.Scanner;
import model.Media;
import service.MediaService;
import service.PlaylistService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int playlistId = 2; // выбери нужный плейлист для теста

        PlaylistService playlistService = new PlaylistService();
        MediaService mediaService = new MediaService();

        //выбрать плейлист
        String playlistName = playlistService.getPlaylistNameById(playlistId);
        if (playlistName == null) {
            System.out.println("Playlist not found.");
            return;
        }

        System.out.println("PLAYLIST = \"" + playlistName + "\"");
        printPlaylistMedia(playlistService, playlistId);

        //меню управления плейлистом
        System.out.println();
        System.out.println("(input numbers for managing):");
        System.out.println("1 - add media");
        System.out.println("2 - delete media");
        System.out.println("3 - exit");

        System.out.print("Your choice: ");
        String choice = scanner.nextLine();

        //выход
        if (choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("exit")) {
            System.out.println("Exit. No changes made.");
            return;
        }

        // добавить media 
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

            int mediaId = Integer.parseInt(input);
            playlistService.addMedia(playlistId, mediaId);
            System.out.println("Media added.");
        }

        // удалить media
        else if (choice.equals("2")) {

            System.out.print("Enter media id to delete or type \"exit\": ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exit. No changes made.");
                return;
            }

            int mediaId = Integer.parseInt(input);
            playlistService.removeMedia(playlistId, mediaId);
            System.out.println("Media removed.");
        }

        // обновленный плейлист
        System.out.println();
        System.out.println("UPDATED PLAYLIST:");
        printPlaylistMedia(playlistService, playlistId);
    }

    // вспомогательный метод для печати media плейлиста
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
