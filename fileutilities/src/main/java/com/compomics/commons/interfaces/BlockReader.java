package com.compomics.commons.interfaces;

import com.compomics.commons.model.Range;
import com.compomics.commons.model.exceptions.MalformedFileException;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Davy Maddelein on 27/04/2015.
 */
public interface BlockReader<U> {

    void indexFileIntoBlocks(Path file, String blockSeparator) throws MalformedFileException, IOException;

    U readBlock(Range range) throws IOException;

}
