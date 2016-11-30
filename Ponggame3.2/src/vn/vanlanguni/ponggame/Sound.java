package vn.vanlanguni.ponggame;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author Tin Dinh - T154168
 */
public class Sound {
    
    private Clip clip;
    
    public static final Sound bgMusic = new Sound(new File("sound/Pokemon.wav"));;
    public static final Sound hitPaddle = new Sound(new File("sound/applause.wav"));
    public static final Sound overPaddle = new Sound(new File("sound/CAR-DOOR.wav"));
    public static final Sound winPlayer = new Sound(new File("sound/crowdhomerunapplause.wav"));;
    public static final Sound hitBonus = new Sound(new File("sound/keyclick.wav"));
	/** Sound path */
    public Sound(File path){
        try{
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(path);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        }catch(Exception e){}
    }
    /** Sound play */
    public void play(){
        
            clip.setFramePosition(0);
            clip.start();
        
    }
    /** Sound stop */
    public void stop(){
        if(clip.isRunning()) clip.stop();
    }
    
   
    
}