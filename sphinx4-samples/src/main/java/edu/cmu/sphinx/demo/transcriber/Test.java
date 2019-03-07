package edu.cmu.sphinx.demo.transcriber;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class Test
{

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("test.wav"));

        recognizer.startRecognition(stream);
        SpeechResult result;

        while ((result = recognizer.getResult()) != null) {
            String hypothesis = result.getHypothesis();
            System.out.format("Hypothesis: %s\n", hypothesis);

            removeWords("zero", result);
        }
        recognizer.stopRecognition();
    }

    public static void removeWords(String word, SpeechResult result)
    {
        String hypothesis = result.getHypothesis();
        String newHypothesis;
       /** if(hypothesis.contains(word))
        {
            System.out.println("The hypothesis contains the word " + word + ".");
        }
        else
        {
            System.out.println("The hypothesis does not contain the word.");
        }*/

        if(hypothesis.contains(word))
        {
            for(int i = 0; i < hypothesis.length() - word.length(); i++)
            {
                if(hypothesis.substring(i, i + word.length()-1).equals(word))
                {
                    newHypothesis = hypothesis.substring(0, i) + "REDACTED" + hypothesis.substring(i + word.length());
                }
            }

        }
        else
        {
            System.out.println("The hypothesis does not contain the word.");
        }
    }
}
