import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// Define a class to manage posts
public class Post {
    private int postID;     // unique identifier
    private String postTitle;   // title
    private String postBody;    // text content
    private String[] postTags;  // tags
    private String postType;    // difficulty levels
    private String postEmergency;   // urgency leevel
    private ArrayList<Comment> postComments;    // list of comments on post

    // Constructor to initialize a new post with specified attributes
    public Post(int postID, String postTitle, String postBody, String[] postTags, String postType, String postEmergency) {
        this.postID = postID;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postTags = postTags;
        this.postType = postType;
        this.postEmergency = postEmergency;
        this.postComments = new ArrayList<>();
    }

    // method to add post after validating properties
    public boolean addPost() {
        if (!validateTitle() || !validateBody() || !validateTags() || !validateTypeAndEmergency()) {
            return false;   // return false if validation fails
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ValidPosts.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;    // return true if the post is successfully added
    }

    // method to validate title of the post
    private boolean validateTitle() {
        return postTitle.length() >= 10 && postTitle.length() <= 250 && Character.isLetter(postTitle.charAt(0));
    }

    // method to validate body length of the post
    private boolean validateBody() {
        return postBody.length() >= 250;
    }

    // method to validate the tags of the post
    private boolean validateTags() {
        if (postTags.length < 2 || postTags.length > 5) {
            return false;
        }
        for (String tag : postTags) {
            if (tag.length() < 2 || tag.length() > 10 || !tag.equals(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    // method to validate the type and emergency status of the post
    private boolean validateTypeAndEmergency() {
        if (postType.equals("Easy") && postTags.length > 3) {
            return false;
        }
        if ((postType.equals("Very Difficult") || postType.equals("Difficult")) && postBody.length() < 300) {
            return false;
        }
        if (postType.equals("Easy") && (postEmergency.equals("Immediately Needed") || postEmergency.equals("Highly Needed"))) {
            return false;
        }
        return !(postType.equals("Very Difficult") || postType.equals("Difficult")) || !postEmergency.equals("Ordinary");
    }

    // convert post details into string format
    @Override
    public String toString() {
        return "Post ID: " + postID + "\nTitle: " + postTitle + "\nBody: " + postBody + "\nTags: " + String.join(", ", postTags) + "\nType: " + postType + "\nEmergency: " + postEmergency;
    }

    // method to add comment to post if it passes validation
    public void addComment(Comment comment) {
        if (comment.addComment() && this.postComments.size() < 5) {
            this.postComments.add(comment);
        }
    }

    // main method
    public static void main(String[] args) throws IOException {
        String longPost = new String(Files.readAllBytes(Paths.get("src/LongPost.txt")));
        String shortPost = new String(Files.readAllBytes(Paths.get("src/ShortPost.txt")));
        Post post1 = new Post(1, "A Long Post", longPost, new String[]{"java", "programming"}, "Difficult", "Highly Needed");
        Post post2 = new Post(2, "A Short Post", shortPost, new String[]{"tech", "info"}, "Easy", "Ordinary");

        post1.addPost();
        post2.addPost();

        Comment comment1 = new Comment("This is a valid comment!");
        Comment comment2 = new Comment("Too short");

        post1.addComment(comment1);
        post1.addComment(comment2);
    }
}

// Define a class to manage comments on posts
class Comment {
    private String commentText;     // text of the comment

    public Comment(String commentText) {
        this.commentText = commentText;
    }

    // validates the comment text
    public boolean addComment() {
        return validateCommentText();
    }

    // validates the length of comment
    private boolean validateCommentText() {
        String[] words = commentText.split(" ");
        if (words.length < 4 || words.length > 10) {
            return false;
        }
        return Character.isUpperCase(commentText.charAt(0));
    }

    // convert comment details to string format
    @Override
    public String toString() {
        return "Comment: " + commentText;
    }
}
