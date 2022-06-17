package models;

import unsw.models.*;
import unsw.utils.*;





import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestFiles {
    @Test 
    public void testBasics(){
        Files file = new Files("text.txt", "abc");
        assertEquals("text.txt", file.getFileName());
        assertEquals("abc", file.getContent());
        assertEquals(3, file.getFileSize());
    }

}
