package it.polimi.ingsw.view.GUIpackage;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MakeSound {

    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;


    public void playSound(String path) throws Exception {



        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];


        InputStream audioSrc = getClass().getResourceAsStream(path);
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        audioStream = AudioSystem.getAudioInputStream(bufferedIn);
        audioFormat = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceLine.open(audioFormat);
        sourceLine.start();


        while (nBytesRead != -1) {
            nBytesRead = audioStream.read(abData, 0, abData.length);
            if (nBytesRead >= 0) sourceLine.write(abData, 0, nBytesRead);
        }

        sourceLine.drain();
        sourceLine.close();
    }
}