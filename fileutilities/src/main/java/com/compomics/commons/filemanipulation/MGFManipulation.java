package com.compomics.commons.filemanipulation;

import com.compomics.commons.IO.LineReader;
import com.compomics.commons.formatters.MGFFormatter;
import com.compomics.commons.interfaces.FileManager;
import com.compomics.commons.lists.ChainedList;
import com.compomics.commons.model.Spectrum;
import com.compomics.commons.model.exceptions.InvalidArgumentException;


import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Davy Maddelein on 08/05/2015.
 */
public class MGFManipulation {

    //could be non static

   static MGFFormatter mgfFormatter = new MGFFormatter();

    private MGFManipulation(){}


    public static boolean joinFiles(Writer outWriter, FileManager<?,Spectrum>... filesToJoin) throws IOException, InvalidArgumentException {


        for (FileManager<?,Spectrum> aFileManager : filesToJoin){
            for(Spectrum aSpectrum : aFileManager.retrieveAllFromStore()){
                outWriter.write(mgfFormatter.toFormat(aSpectrum));
            }
        }

//        Arrays.stream(filesToJoin).forEach(e -> {
//            try {
//                e.retrieveAllFromStore().stream().forEach(e2 -> {
//                    try {
//                        outWriter.write(MGFFormatter.toFormat(e2));
//                    } catch (IOException e1) {
//                        //report partial failure
//                    }
//                });
//            } catch (InvalidArgumentException|IOException e1) {
//                //report partial failure
//                e1.printStackTrace();
//            }
//        });
        return true;
    }

    public static boolean partitionFile(Path outputFolder, int partitionSize, Path fileToPartition) throws IOException {
        return partitionFile(outputFolder, partitionSize, "BEGIN IONS", fileToPartition);
    }


    public static boolean partitionFile(Path outputFolder, int partitionSize, String blockStartSeparator, Path fileToPartition) throws IOException {
        ChainedList<FileWriter> writers = new ChainedList<>();
        for (int i = 0; i < partitionSize; i++) {
            writers.add(new FileWriter(outputFolder.toString() + fileToPartition.getFileName() + "_" + i));
        }
        StringBuilder block = new StringBuilder();

        try (LineReader reader = new LineReader(new FileReader(fileToPartition.toFile()))){
            String readLine = reader.readLine();
            while (readLine != null) {
                if (readLine.equals(blockStartSeparator) && block.length() != 0) {
                    writers.getNext().write(block.toString());
                    block = new StringBuilder(readLine);
                } else {
                    block.append(readLine);
                }
            }
            if (block.length() > 0) {
                writers.getNext().write(block.toString());
            }
        } catch (IllegalAccessException e) {
            IOException ioe = new IOException("could not access one of the file writers");
            ioe.initCause(e);
            throw ioe;
        }
        for (Writer aWriter :  writers){
            aWriter.close();
        }
        return true;
    }

    public static boolean partitionFile(Path outputFolder, int partitionSize, FileManager<?,Spectrum> manager) throws IOException, InvalidArgumentException {
        List<Spectrum> allSpectra =  new ArrayList<>(manager.retrieveAllFromStore());
        int counter = allSpectra.size();
        BufferedWriter outWriter = new BufferedWriter(new FileWriter(outputFolder+"exported_spectra_0.mgf"));
        for (int i = 0; i < counter; i++) {
            if(partitionSize % i == 0){
                outWriter = new BufferedWriter(new FileWriter(outputFolder+"exported_spectra_"+i+".mgf"));
            }
            outWriter.write(mgfFormatter.toFormat(allSpectra.get(i)));
        }
        outWriter.close();
        return true;
    }
}
