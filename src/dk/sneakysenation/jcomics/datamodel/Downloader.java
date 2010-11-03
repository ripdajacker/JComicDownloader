package dk.sneakysenation.jcomics.datamodel;

import java.io.File;

/**
 * TODO[Jesenko] - someone remind me to document this class
 *
 * @author Jesenko Mehmedbasic
 * @since 11/3/10, 5:39 AM
 */
public interface Downloader {
   public void download(File output);

   public boolean isUpdated();
}
