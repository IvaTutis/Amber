package com.itsallbinary.obrada_ulaznog_teksta;

import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.util.InvalidFormatException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Lematizator {

    private LemmatizerME lematizator;

    public Lematizator(){
        try{
            InputStream model = new FileInputStream("en-lemmatizer.bin");
            lematizator = new LemmatizerME(new LemmatizerModel(model));
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }

    }

    /**
     * Nađi korijen tokena koristeći tehnologije lematizacije biblioteke Apache OpenNLP
     *
     *
     * @param tokeni
     * @param posTags
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public String[] lematiziraj(String[] tokeni, String[] posTags){

            String[] lemmaTokeni = lematizator.lemmatize(tokeni, posTags);

            return lemmaTokeni;
    }
}