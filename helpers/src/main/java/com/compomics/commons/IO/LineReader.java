package com.compomics.commons.IO;


import java.io.*;

/**
 * a reader that reads lines but does not remove the EOL character(s)
 * Created by Davy Maddelein on 05/05/2015.
 */
public class LineReader extends BufferedReader {
    public LineReader(Reader in, int sz) {
        super(in, sz);
    }

    public LineReader(Reader in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    /**
     * reads a line from the file in the underlying stream
     * @return a string or null if end of file
     * @throws IOException if the underlying stream could not be read
     */
    @Override
    public String readLine() throws IOException {

        String returnString = null;

        StringBuilder s = new StringBuilder();

        boolean eol = false;

        int readChar = read();

        while (readChar != -1) {

            s.append((char) readChar);

            if ((readChar == '\n') || (readChar == '\r')) {
                eol = true;
            }

            if (eol) {
                if (readChar == '\r') {
                    s.append((char) read());
                }
                returnString = s.toString();
                s = new StringBuilder();
                break;
            }
            readChar = read();
        }
        return returnString;
    }
}
