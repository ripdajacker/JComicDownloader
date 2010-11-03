package dk.sneakysenation.jcomics;

import dk.sneakysenation.jcomics.datamodel.Downloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO[Jesenko] - someone remind me to document this class
 *
 * @author Jesenko Mehmedbasic
 * @since 11/3/10, 5:42 AM
 */
public class Comics {
   private List<Downloader> downloaders = new ArrayList<Downloader>();
             private File outputDirectory;

   public Comics(File outputDirectory) {
      this.outputDirectory = outputDirectory;
   }

   void registerDownloader(Downloader downloader) {
      downloaders.add(downloader);
   }

   void updateAll() {
      for (Downloader downloader : downloaders) {
         if (!downloader.isUpdated()) {
            downloader.download(outputDirectory);
         }
      }
   }
}
