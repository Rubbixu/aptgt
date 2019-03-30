package edu.auburn.utils.scal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneticSets {
	/*private String[] Vowels={"A","a","E","e","I","i","O","o","U","u"};
	private String[] Consonants={"b","c","d","f","g","h","j","k","l","m","n","p","q","r","s","t","v","w","x","y","z",
			"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"};
	private String[] Number={"1","2","3","4","5","6","7","8","9","0"};
	*/
	
	//private String[] Vowels={"i","I","E","e","@","a","W","Y","^","c","O","o","U","u","X","x","R","N","M","L","-", "\u025B", "\u0153", "\u0251","e\u026A\u0361" };
	//private String[] Consonants={"p","t","k","b","d","g","C","J","s","S","z","Z","f","T","v","D","h","n","m","G","q",
			//"l","r","w","j","V", "\u02A7\u0361", "\u02A4\u0361", "\u0283", "\u0292", "\u03B8", "\u00F0", "\u014B", "\u026B", "\u027E", "\u0294", "\u029D", "\u0267"};
	//
	
	private String[] Vowels={"u", "ɚ", "ɪ","ə","ɑ","e","ʊ","a","ʌ","ɛ","æ","i","ɜ","u","ɔ","ɑ","e","ɪ","ɔ","o", "e\u0361ɪ", "ɑ\u0361u","ɑ\u0361ʊ", "ɑ\u0361ɪ", "ɔ\u0361ɪ", "o\u0361ʊ", "o\u0361u","m̩","n̩", "l̩", "o͡ʊ", "ɑ͡u", "o1515", "ɑ982", "a982", "e1483", "ɑʊ", "a͡u", "e͡ɪ" };//, "ɪr", "ɛr", "ʊr"};
		//
	private String[] Consonants={"ɡ", "t\u0361ʃ", "d\u0361ʒ", "p","r","t","f","θ","s","ʃ","b","d","g","v","z","ʒ","h","m","n","ŋ","l","r","j","w", "l","r","w", "ð", "d1523", "t1508", "tʃ", "k", "ɹ", "ɹ"};//, "dʒ","dr", "dz", "tr", "ts", "tʃ","ð"};
	private String[] Number={"1","2","3","4","5","6","7","8","9","0"};
	
	private String[] Dicritics1={"ˈ", "ˌ"};
	//private String[] Dicritics2={" ̃", " ͊", " ͋", "ː",  " ̥", " ̬", "ʰ", " ̝",  " ̞", " ̟", " ̠", " ̚", " ̤", " ̰", "m̩"};
	private String[] Dicritics2={"\u0303", "\u034A", "\u034B", "ː", "\u0325", "\u032C", "\u036A", "\u031D", "\u031E", "\u031F", "\u0320", "\u031A","\u0324", "\u0330", "\u0329", "ʰ"};
	
	private List<String> ConsonantList=Arrays.asList(Consonants);
	private List<String> VowelsList=Arrays.asList(Vowels);
	private List<String> NumberList=Arrays.asList(Number);
	private List<String> DicriticsList1=Arrays.asList(Dicritics1);
	private List<String> DicriticsList2=Arrays.asList(Dicritics2);
	
	private Set<String> vowelsSet = new HashSet<>(VowelsList);
	private Set<String> numberSet = new HashSet<>(NumberList);
	private Set<String> dicriticsSet1 = new HashSet<>(DicriticsList1);
	private Set<String> dicriticsSet2 = new HashSet<>(DicriticsList2);
	private Set<String> consonantSet = new HashSet<>(ConsonantList);
	public Set<String> getVowelsSet() {
		return vowelsSet;
	}
	public void setVowelsSet(Set<String> vowelsSet) {
		this.vowelsSet = vowelsSet;
	}
	public Set<String> getNumberSet() {
		return numberSet;
	}
	public void setNumberSet(Set<String> numberSet) {
		this.numberSet = numberSet;
	}
	public Set<String> getDicriticsSet1() {
		return dicriticsSet1;
	}
	public void setDicriticsSet1(Set<String> dicriticsSet1) {
		this.dicriticsSet1 = dicriticsSet1;
	}
	public Set<String> getDicriticsSet2() {
		return dicriticsSet2;
	}
	public void setDicriticsSet2(Set<String> dicriticsSet2) {
		this.dicriticsSet2 = dicriticsSet2;
	}
	public Set<String> getConsonantSet() {
		return consonantSet;
	}
	public void setConsonantSet(Set<String> consonantSet) {
		this.consonantSet = consonantSet;
	}
	
//	public static void main(String[] args) {
//		PhoneticSets pSet = new PhoneticSets();
//		for (String str : pSet.getVowelsSet()) {
//			System.out.println(str);
//		}
//		System.out.println("Dicritics type II:");
//		int i = 0;
//		for (String str : pSet.getDicriticsSet2()) {
//			System.out.println((i++) + " " + str);
//		}
//		
//		System.out.println("Consonant:");
//		i = 0;
//		for (String str : pSet.getConsonantSet()) {
//			System.out.println((i++) + " " + str);
//		}
//		
//		
//	}
	
	
	
	
	
	
  
	
	
	
	
}
