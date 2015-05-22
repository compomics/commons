package com.compomics.commons.filemanipulation;

import com.compomics.commons.interfaces.FileManager;

import java.io.Writer;
import java.nio.file.Path;

/**
 * Created by Davy Maddelein on 08/05/2015.
 */
public class FastaManipulation {

    public static boolean joinFiles(Writer outWriter, Path... filesToJoin){
        return false;
    }

    public static boolean joinFiles(Writer outWriter, FileManager... fileManagersToJoin){
        return false;
    }

    /**
     * splits a given file in a given amount of partials
     * @param outputFolder the folder to output the partitioned files in
     * @param partitionSize the amount of files that need to be generated
     * @param fileToSplit the file to split out
     * @return true if the partitioning was successful
     */
    public static boolean splitFile(Path outputFolder, int partitionSize, Path fileToSplit){
        return false;
    }
}
