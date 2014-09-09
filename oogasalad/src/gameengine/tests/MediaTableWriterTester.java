package gameengine.tests;

import java.io.File;
import mastercode.MediaTableWriter;


public class MediaTableWriterTester {

    @org.junit.Test
    public void test_writer () {

        MediaTableWriter writer = new MediaTableWriter("test", "#", 12,
                                                       "gameengine"+File.separator+"tests"+File.separator+"grass4.png", "src"+File.separator+"gameplayer"+File.separator+"media.tbl");
        writer.write();

    }

}
