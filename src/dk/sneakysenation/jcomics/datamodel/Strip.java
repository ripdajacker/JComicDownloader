package dk.sneakysenation.jcomics.datamodel;

import java.net.URL;

/**
 * TODO[Jesenko] - someone remind me to document this class
 *
 * @author Jesenko Mehmedbasic
 * @since 11/3/10, 6:00 AM
 */
public class Strip {
   private String file;
   private int number;
   private URL url;

   public Strip(String file, int number, URL url) {
      this.file = file;
      this.number = number;
      this.url = url;
   }

   public String getFile() {
      return file;
   }

   public void setFile(String file) {
      this.file = file;
   }

   public int getNumber() {
      return number;
   }

   public void setNumber(int number) {
      this.number = number;
   }

   public URL getUrl() {
      return url;
   }

   public void setUrl(URL url) {
      this.url = url;
   }
}
