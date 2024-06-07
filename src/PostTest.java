import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

// contains all the unit tests for the Post class
public class PostTest {

    // verifies a valid long post is added successfully
    @Test
    public void testAddPost_LongPostValid_ReturnsTrue() throws IOException {
        String longPost = new String(Files.readAllBytes(Paths.get("src/LongPost.txt")));    // read the content, which is body
        String[] tags = {"security", "cyber"};      // tags
        // create an instance of post with test data that should be valid
        Post post = new Post(1, "Cybersecurity Importance", longPost, tags, "Very Difficult", "Highly Needed");
        assertTrue(post.addPost());     // asserts that addPost() should return true, indicating the post meets all validation rules and is added successfully
    }

    // verifies an invalid short post is not added
    @Test
    public void testAddPost_ShortPostInvalid_ReturnsFalse() throws IOException {
        String shortPost = new String(Files.readAllBytes(Paths.get("src/ShortPost.txt")));
        String[] tags = {"ai", "tech"};     // tags
        // create an instance of post with test data that should be invalid
        Post post = new Post(2, "AI Impact", shortPost, tags, "Easy", "Ordinary");
        assertFalse(post.addPost());    // asserts that addPost() should return false, indicating the post does not meet all validation rules
    }
}