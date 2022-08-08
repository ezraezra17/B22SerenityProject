package b22.spartan.editor;

import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

public class ConfigDemoTest {

    @Test
    public void test1(){
        ConfigReader.getProperty("spartan.editor.username");
        ConfigReader.getProperty("spartan.editor.password");
    }
}
