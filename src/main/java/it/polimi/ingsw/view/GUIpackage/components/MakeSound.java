package it.polimi.ingsw.view.GUIpackage.components;

import javax.sound.sampled.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

/**
 * The type Make sound.
 * allows you to reproduce a sound
 */
public class MakeSound implements PropertyChangeListener {

    private Clip clip;
    String settings="On";
    int generalVolume=0;


    /**
     * Play sound.
     *
     * @param path   the path of the sound file
     * @param volume the volume level of the sound file. 0 is base value
     * @param loop   boolean that allow to play sound in loop
     */
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


    /**
     * Stop sound.
     */
    public void stopSound(){
        if(this.settings.equalsIgnoreCase("on")){
            this.clip.stop();
        }
    }

    /**
     * Handle the events launched
     * by the settings panel in homePanel class
     *
     * @param evt the property change event
     */

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase("SoundsOn")) {
           this.settings="On";
        }
        if (evt.getPropertyName().equalsIgnoreCase("SoundsOff")) {
            this.settings="Off";
        }

    }
}