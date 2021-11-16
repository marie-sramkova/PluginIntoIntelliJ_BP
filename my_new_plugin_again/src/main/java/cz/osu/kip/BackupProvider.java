package cz.osu.kip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class BackupProvider {
    public void doBackup(String src, String trg) {
        backupFolderFiles(src, trg);
        backupFolderFolders(src, trg);
    }

    private void backupFolderFolders(String src, String trg) {
        File srcFile = new File(src);


        if (!srcFile.exists() || !srcFile.isDirectory()){
            throw new IllegalArgumentException("Path " + src + " does not exists.");
        }

        File[] srcFiles = new File(src).listFiles(f -> f.isDirectory());
        if (srcFile == null)
            throw new RuntimeException("Unable to read content of " + src);


        for (File srcSubFile : srcFiles) {
            Path sourceSubPath = srcSubFile.toPath();
            Path targetSubPath = Paths.get(trg).resolve(srcSubFile.getName());

            backupFolderFiles(sourceSubPath.toString(), targetSubPath.toString());
        }
    }

    private void backupFolderFiles(String src, String trg) {
        File srcFile = new File(src);
        File trgFile = new File(trg);

        if (!srcFile.exists() || !srcFile.isDirectory()){
            throw new IllegalArgumentException("Path " + src + " does not exists.");
        }
        if (!trgFile.exists()){
            trgFile.mkdirs();
        }
        else if (!trgFile.isDirectory()){
            throw new IllegalArgumentException("Path " + src + " is not a folder.");
        }

        File[] srcFiles = new File(src).listFiles(f -> f.isFile());
        File[] trgFiles = new File(trg).listFiles(f -> f.isFile());
        if (srcFile == null)
            throw new RuntimeException("Unable to read content of " + src);
        if (trgFiles == null)
            throw new RuntimeException("Unable to read content of " + trg);

//        for (File srcSubFile : srcFiles){
//            Path sourceSubPath = srcSubFile.toPath();
//            Path targetSubPath = Paths.get(trg).resolve(srcSubFile.getName());
//
//            backupFolderFiles(sourceSubPath.toString(), targetSubPath.toString());
//        }

        for (int srcIndex = 0; srcIndex < srcFiles.length; srcIndex++) {
            System.out.print("Checking file " + srcFiles[srcIndex]);
            boolean isBackupNeeded;
            int trgIndex = tryGetTargetFileIndex(trgFiles, srcFiles[srcIndex]);
            isBackupNeeded = trgIndex < 0;
            if (!isBackupNeeded){
                isBackupNeeded = srcFiles[srcIndex].length() != trgFiles[trgIndex].length();
                if (!isBackupNeeded){
                    isBackupNeeded = !hasBothFilesTheSameContent(srcFiles[srcIndex], trgFiles[trgIndex]);
                }
            }

            System.out.println("\t" + (isBackupNeeded ? "will be backed-up." : "no backup necessary."));

            if (isBackupNeeded){
                Path srcFilePath = srcFiles[srcIndex].toPath();
                Path trgFilePath = Paths.get(trg).resolve(srcFilePath.getFileName()); //Paths.get(trg, srcFilePath.getFileName().toString());
                try {java.nio.file.Files.copy(
                        srcFilePath,
                        trgFilePath,
                        StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException(String.format("Failed to copy '%s' to '%s'.", srcFilePath, trgFilePath), e);
                }
            }
        }


    }

    private boolean hasBothFilesTheSameContent(File a, File b) {

        assert a.length() == b.length();

        Boolean ret = null;

        try(BufferedInputStream bisa = new BufferedInputStream(new FileInputStream(a));
            BufferedInputStream bisb = new BufferedInputStream(new FileInputStream(b))) {

            byte[] dataA = new byte[1024];
            byte[] dataB = new byte[1024];


            while (ret == null){

                int dataLenA = bisa.read(dataA);
                int dataLenB = bisb.read(dataB);
                assert dataLenB == dataLenA;

                if (dataLenA < 0){ ret = true; }
                else if (Arrays.equals(dataA, 0, dataLenA, dataB, 0, dataLenA) == false){ ret = false; }

//                int dataA = bisa.read();
//                if (dataA < 0){ return false; }
//                int dataB = bisb.read();
//                if (dataA != dataB){ return false; }
            }


        }catch (IOException ex){
            throw new RuntimeException(String.format("Error comparing '%s' with '%s'.", a, b),ex);
        }

        assert ret != null;
        return ret;
    }

    private int tryGetTargetFileIndex(File[] trgFiles, File srcFile) {
        int ret = -1;

        String srcFileName = srcFile.getName();

        for (int i = 0; i < trgFiles.length; i++) {
            if (trgFiles[i].getName().equals(srcFileName)){
                ret = i;
                break;
            }
        }
        return ret;
    }
}
