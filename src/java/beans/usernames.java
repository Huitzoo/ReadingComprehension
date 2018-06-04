package beans;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="usernames")
public class usernames {
    List<String> username;

    public usernames(List<String> username) {
        this.username = username;
    }

    public usernames() {
    }

    public List<String> getUsername() {
        return username;
    }
    
    @XmlElement
    public void setUsername(List<String> username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "usernames{" + "username=" + username + '}';
    }
    
}
