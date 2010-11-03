package dk.sneakysenation.jcomics.datamodel;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO[Jesenko] - someone remind me to document this class
 *
 * @author Jesenko Mehmedbasic
 * @since 11/3/10, 5:41 AM
 */
public class SexyLosersDownloader implements Downloader {
   private static final int CORE_POOL_SIZE = 15;
   private String baseUrl = "http://sexylosers.com/";

   public SexyLosersDownloader() {

   }

   private String parseStripUrl(URL url) throws IOException {
      String k = "<IMG SRC = \"comics/";
      Scanner scanner = new Scanner(url.openStream());
      while (scanner.hasNextLine()) {
         String line = scanner.nextLine();
         if (line.contains(k)) {
            int startIndex = line.indexOf(k) + k.length();
            return line.substring(startIndex, line.indexOf("\"", startIndex));
         }
      }
      return null;
   }

   @SuppressWarnings({"ResultOfMethodCallIgnored"})
   public void download(File output) {
      try {
         URL url = new URL(baseUrl + "strips.html");
         InputStream inputStream = url.openStream();
         Scanner scanner = new Scanner(inputStream);
         boolean inComic = false;
         Comic currentComic = null;
         List<Comic> comics = new ArrayList<Comic>();
         while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String k = "<A NAME = ";
            if (s.contains(k)) {
               int indexStart = s.indexOf(">", s.indexOf(k)) + 1;
               int indexEnd = s.indexOf("<", indexStart);
               String name = s.substring(indexStart, indexEnd);
               if (currentComic != null) {
                  comics.add(currentComic);
               }
               currentComic = new Comic(name);
               inComic = true;
            }
            if (currentComic != null && inComic) {
               if (s.trim().startsWith("<A HREF = \"")) {
                  String substring = s.substring(s.indexOf("\">") + 2, s.indexOf("</A>"));
                  String stripString = substring.replaceAll("&nbsp;", "").replaceAll("  ", " ");
                  int number = Integer.parseInt(stripString.split(" ")[0]);
                  NumberFormat formatter = new DecimalFormat("000");
                  String spec = baseUrl + formatter.format(number) + ".html";
                  URL stripUrl = new URL(spec);
                  Strip strip = new Strip(null, number, stripUrl);
                  currentComic.add(strip);
               }
            }
         }
         String basePath = output.getAbsolutePath() + "/SexyLosers";
         new File(basePath).mkdir();
         System.out.println("Downloading SexyLosers");
         ExecutorService service = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
         long start = System.currentTimeMillis();
         for (Comic comic : comics) {
            final String name = comic.getName();
            final String currentComicPath = basePath + "/" + name;
            File file = new File(currentComicPath);
            file.mkdir();
            final List<Strip> strips = comic.getStrips();
            for (int i = 0, stripsSize = strips.size(); i < stripsSize; i++) {
               final Strip strip = strips.get(i);
               final int finalI = i;
               Thread thread = new Thread() {
                  @Override
                  public void run() {
                     try {
                        System.out.println(" Downloading " + name + " " + (finalI + 1) + " of " + strips.size() + ": " + strip.getNumber());
                        String foo = parseStripUrl(strip.getUrl());
                        String pictureUrl = baseUrl + "comics/" + foo;
                        downloadPicture(new URL(pictureUrl), currentComicPath + "/" + foo);
                     } catch (IOException e) {
                        e.printStackTrace();
                     }
                  }
               };
               service.submit(thread);
            }
         }
         service.shutdown();
         while (!service.isTerminated()) {

         }
         long stop = System.currentTimeMillis();
         System.out.println("\nDownloaded everything in: " + ((stop - start) / 1000) + " seconds.");
      } catch (Exception ignored) {
      }
   }

   private void downloadPicture(URL url, String output) throws IOException {
      InputStream inputStream = url.openStream();
      OutputStream out = new BufferedOutputStream(new FileOutputStream(output));
      int i;
      while ((i = inputStream.read()) != -1) {
         out.write(i);
      }
      out.flush();
      inputStream.close();
      out.close();
   }

   public boolean isUpdated() {
      return false;
   }
}
