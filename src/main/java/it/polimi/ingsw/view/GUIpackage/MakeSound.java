package it.polimi.ingsw.view.GUIpackage;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class MakeSound {

    private Clip clip;
    String settings;
    int generalVolume;

    public MakeSound (){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/settings.txt"), StandardCharsets.UTF_8))) {
            this.settings = br.readLine();
            this.generalVolume= Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public void playSound(String path, Float volume, boolean loop) {


        if(settings.equalsIgnoreCase("on")){
            volume=volume+this.generalVolume;

            //read audio data from whatever source (path)
            InputStream audioSrc = getClass().getResourceAsStream(path);

            //add buffer for mark/reset support (così funziona anche nel jar)
            InputStream bufferedIn = new BufferedInputStream(audioSrc);

            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                this.clip = AudioSystem.getClip();
                this.clip.open(audioInputStream);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);//per controllare il volume
            gainControl.setValue(volume); // Reduce volume by "volume" decibels.

            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.clip.start();
        }





    }


    public void stopSound(){
        if(this.settings.equalsIgnoreCase("on")){
            this.clip.stop();
        }
    }


}