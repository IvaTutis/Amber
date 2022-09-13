package chatbot;

import chatbot.obrada_ulaznog_teksta.DetektorRecenica;
import chatbot.obrada_ulaznog_teksta.Lematizator;
import chatbot.obrada_ulaznog_teksta.POS_Tagger;
import chatbot.obrada_ulaznog_teksta.Tokenizer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ovaj kod predstavlja prodajni chatbot program koji automatizira proces prodaje proizvoda,
 * te odgovara na često postavljena pitanja. Program koristi Apache OpenNLP biblioteku i pripadne NLP
 * tehnologije kako bi kategorizirao upit korisnika u jednu od kategorija na koje ima pripremljen odgovor.
 *
 * 
 * @author Iva Tutiš
 */
public class Amber {

	// Mapa koja definira odgovor na pitanje
	private static Map<String, List<String>> pitanjeOdgovor = new HashMap<>();

	/*
	 * Definiranje jednog odgovora za svaku kategoriju upita.
	 */
	static {
		pitanjeOdgovor.put("greeting", Arrays.asList("Greetings, weary traveler. What is it that interests you?",
				"Hello, my name is Amber. What can I help you with?"));
		pitanjeOdgovor.put("product-inquiry",
				Arrays.asList("The product I sell is a Hexaflexagon. " +
						"\n It is a flat model made out of paper that can be flexed or folded in certain ways " +
						"\n to reveal faces besides the two that were originally on the back and front."));
		pitanjeOdgovor.put("price-inquiry", Arrays.asList("Price of all my wares is $6.99. Best on the market!",
				"The hexaflexagon is priced at $6.99. I assure you, it's worth it!"));
		pitanjeOdgovor.put("conversation-continue", Arrays.asList("Can I help you with anything else?"));
		pitanjeOdgovor.put("conversation-complete", Arrays.asList("It was pleasant, speaking with you, " +
				"but I am afraid our time together ends now.", "I think we are done talking. Good bye!"));
		pitanjeOdgovor.put("swearword-block", Arrays.asList("I don't answer to those kinds of words."));
		pitanjeOdgovor.put("other-product-inquiry", Arrays.asList("At this time, I sell only one product."));
		pitanjeOdgovor.put("aesthetic-of-product", Arrays.asList("Hexaflexagons are *very* pretty, and I assure every customer of mine" +
				" that they will definitely only make their space a more indulgent aesthetic experience."));
		pitanjeOdgovor.put("purpose-of-product", Arrays.asList("Hexaflexagons combine math with entertaiment, " +
				"and thus make for a perfect tool to introduce youreself or your offspring" +
				" into the wild, fun world of applied mathematics."));
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

		// Stvori kategorizator
		Kategorizator kategorizator = new Kategorizator();

		// Čitaj unos korisnika sa konzole u while(true) petlji dok se zastavica za završetak razgovora ne podigne
		Scanner scanner = new Scanner(System.in);
		boolean razgovorJeGotov = false;
		while (true) {

			String dodatneInformacije = "\n";

			// Unos korisnika
			System.out.println("▁ ▂ ▄ ▅ ▆ ▇ █ \uD835\uDD50\uD835\uDD60\uD835\uDD66 █ ▇ ▆ ▅ ▄ ▂ ▁");
			String unosKorisnika = scanner.nextLine();

			// Razlama string unosKorisnika na rečenice
			DetektorRecenica detektor = new DetektorRecenica();
			String[] sentences = detektor.razlomiTesktNaRecenice(unosKorisnika);
			dodatneInformacije += "Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")) + "\n";

			String odgovor = "";

			// Program u petlji obrađuje rečenicu po rečenicu
			for (String sentence : sentences) {

				// Riječi u rečenici se odjeljuju jedna od druge koristeći tokenizer.
				Tokenizer tokenizer = new Tokenizer();
				String[] tokeni = tokenizer.tokeniziraj(sentence);
				dodatneInformacije += "Tokenizer : " + Arrays.stream(tokeni).collect(Collectors.joining(" | ")) + "\n";

				// Odvojene riječi se označavaju sa POS tagovima
				POS_Tagger posTagger = new POS_Tagger();
				String[] posTags = posTagger.tag(tokeni);
				dodatneInformacije += "POS Tags : " + Arrays.stream(posTags).collect(Collectors.joining(" | ")) + "\n";

				// Savaka riječ se lematizira kako bi ju bilo lakše kategorizirati
				Lematizator lematizator  = new Lematizator();
				String[] lemmaTokens = lematizator.lematiziraj(tokeni, posTags);
				dodatneInformacije += "Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")) + "\n";

				// Pronalazak najbolje kategorije u koju input rečenica spada
				String category = kategorizator.vratiKategoriju(lemmaTokens);
				dodatneInformacije += "Category: " + category + "\n";

				// Provjeravamo je li kategorija jednaka onoj za završetak razgovora
				if ("conversation-complete".equals(category)) razgovorJeGotov = true;

				// Odgovor se determinira iz kategorije.
				List<String> listaOdgovora = pitanjeOdgovor.get(category);
				Random random = new Random();
				odgovor = odgovor + " " + listaOdgovora.get(random.nextInt(listaOdgovora.size()));
			}

			//Isprintaj dodatne informacije
			System.out.println(dodatneInformacije);

			// Isprintaj odgovor chatbota Amber u konzolu
			System.out.println("▁ ▂ ▄ ▅ ▆ ▇ █ \uD835\uDD38\uD835\uDD5E\uD835\uDD53\uD835\uDD56\uD835\uDD63 █ ▇ ▆ ▅ ▄ ▂ ▁ ");
			System.out.println(odgovor);

			//Provjeri je li razgovor gotov
			if (razgovorJeGotov) break;
		}

	}

}
