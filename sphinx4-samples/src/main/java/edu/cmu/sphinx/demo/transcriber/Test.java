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
        if(hypothesis.contains(word))
        {
            while(hypothesis.contains(word))
            {
                hypothesis = hypothesis.substring(0, hypothesis.indexOf(word)) + "REDACTED" + hypothesis.substring(hypothesis.indexOf(word) + word.length());
            }
            System.out.println(hypothesis);
        }
        else
        {
            System.out.println("The hypothesis does not contain the word " + word);
        }
    }
}
