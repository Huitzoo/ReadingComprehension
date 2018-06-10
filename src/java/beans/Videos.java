package beans;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author huitz
 */
@XmlRootElement(name="videos")
public class Videos {
    List<Video> videos;

    public Videos(List<Video> videos) {
        this.videos = videos;
    }

    public Videos() {
    }
    
    @XmlElement(name="video")
    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Videos{" + "videos=" + videos + '}';
    }
    
}
