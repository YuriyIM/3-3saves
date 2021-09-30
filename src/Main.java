import javax.imageio.IIOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {

        GameProgress game1 = new GameProgress(40, 13, 80, 123.4);
        GameProgress game2 = new GameProgress(50, 35, 55, 12.3);
        GameProgress game3 = new GameProgress(48, 34, 23, 1234.4);

        saveGame("D://Games//savegames//save1.dat", game1);
        saveGame("D://Games//savegames//save2.dat", game2);
        saveGame("D://Games//savegames//save3.dat", game3);

        String[] files = {"D://Games//savegames//save1.dat",
                          "D://Games//savegames//save2.dat",
                          "D://Games//savegames//save3.dat"};

        zipFiles("D://Games//savegames//saveZip.zip", files);

        deletedFiles(files);
    }

    static void saveGame (String folderWay, GameProgress game) throws FileNotFoundException {
        try (FileOutputStream fos = new FileOutputStream(folderWay);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles (String zipWay, String[] fileWay) throws FileNotFoundException {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream(zipWay))) {
            for (int i = 0; i < fileWay.length; i++) {
                try (FileInputStream fis = new FileInputStream(fileWay[i])){
                    ZipEntry entry = new ZipEntry("save" + (i + 1));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[4096];
                    int n = 0;
                    while (-1 != (n = fis.read(buffer))) {
                        zout.write(buffer, 0, n);
                    }
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println((ex.getMessage()));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void deletedFiles (String[] files) throws IOException {
        for(int i = 0; i < files.length; i++){
            File file = new File(files[i]);
            if(file.delete()){
                try (FileWriter writer = new FileWriter("D://Games//temp//temp.txt", true)) {
                    writer.write("Файл " + files[i] + " удален.\n");
                    writer.flush();
                } catch (IIOException ex) {
                    System.out.println(ex.getMessage());
                }
            }else System.out.println("Файл не был найден в корневой папке проекта");        }
    }
}
