package com.itsallbinary.obrada_ulaznog_teksta;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class POS_Tagger {

    private POSTaggerME posTagger;

    public POS_Tagger(){
        try{
            InputStream model = new FileInputStream("en-pos-maxent.bin");
            posTagger = new POSTaggerME(new POSModel(model));
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }

    }

    /**
     * POS tagger koristi Apache OpenNLP tehnologiju kako bi pronašao Part-Of-Speech (POS) tag-ove svih tokena
     *
     *
     * @param tokeni
     * @return
     * @throws IOException
     */
    public String[] tag(String[] tokeni) throws IOException {

            // Pronađi Part-Of-Speech (POS) tag-ove svih tokena
            String[] posTags = posTagger.tag(tokeni);

            return posTags;

    }
}
