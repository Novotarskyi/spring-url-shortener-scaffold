package Services;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ShortenerService {

    public String shortenLink(String originalLink){
        String id = Hashing.murmur3_32().hashString(originalLink, StandardCharsets.UTF_8).toString();

        return id;
    }
}
