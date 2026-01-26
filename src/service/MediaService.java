package service;

import java.util.List;
import model.Media;
import repository.MediaRepository;

public class MediaService {

    private final MediaRepository mediaRepository = new MediaRepository();

    public void addMedia(Media media) {
        mediaRepository.create(media);
    }

    public List<Media> getAllMedia() {
        return mediaRepository.getAll();
    }

    // ✅ ВОТ ЭТО НУЖНО
    public boolean mediaExists(int mediaId) {
        return mediaRepository.existsById(mediaId);
    }
}
