package dk.sneakysenation.jcomics.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO[Jesenko] - someone remind me to document this class
 *
 * @author Jesenko Mehmedbasic
 * @since 11/3/10, 6:01 AM
 */
public class Comic {
   private List<Strip> strips = new ArrayList<Strip>();
   private String name;

   public Comic(String name) {
      this.name = name;
   }

   public boolean add(Strip strip) {
      return strips.add(strip);
   }

   public List<Strip> getStrips() {
      return strips;
   }

   public String getName() {
      return name;
   }
}
