package dk.sneakysenation.jcomics;

import dk.sneakysenation.jcomics.datamodel.SexyLosersDownloader;

import java.io.File;

/**
 * TODO[Jesenko] - someone remind me to document this class
 *
 * @author Jesenko Mehmedbasic
 * @since 11/3/10, 5:39 AM
 */
public class Main {
   public static void main(String[] args) {
      Comics comics = new Comics(new File("c:/users/Jesenko/Desktop/comics"));
      comics.registerDownloader(new SexyLosersDownloader());
      comics.updateAll();
   }
}
