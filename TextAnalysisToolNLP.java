/* File: project/TextAnalysisToolNLP.java
Olivia Anastassov and Lika Mikhelashvili
Project for CSC212 Fall 2020
Text Analysis Tool for Natural Language Processing (NLP)
*/

import java.util.*;
import java.io.*;
public class TextAnalysisToolNLP{ //driver program
	static Scanner cin = new Scanner(System.in);
	public static void main (String [] args){
        String wordstoavoid = "words_avoid.txt";
		List <String> Files = new ArrayList<>(); //List of files entered by the user
        List <String> Avoid = new ArrayList <>(); //List of the words to avoid
		readAvoid(wordstoavoid, Avoid); //just reading the wordstoavoid file and creating the list of those words
		char op;
		do{
			System.out.println("Text Analysis Tool for NLP"+
			"\nMenu" +
			"\n1.Add text files to the database (make sure they are in the same folder as the program)"+
			"\n2.Remove text files from the database" +
			"\n3.Display the text files in the database" +
			"\n4.Display the list of words to avoid"+
			"\n5.Read the files in your database and sort the vocabulary alphabetically"+
			"\n6.Sort the vocabulary in the files in your database in decreasing frequency of usage"+
			"\n7.Search for a word in your files"+
			"\n8.Display the size of the authors vocabulary"+ 
			"\n9.Displays the words that the author uses in all the files in the database"+ 
			"\n0.Exit");
			op = cin.next().charAt(0);
			switch(op){
				case '1': add(Files); break; 
				case '2': remove(Files); break;
				case '3': System.out.println("Your files: " + Files); break;
				case '4': System.out.println(Avoid+ "\n\n"); break;
				case '5': sortAlphabet(Files, Avoid); break;
				case '6': SortFrequency(Files,Avoid); break;
				case '7': searchWord(Files,  Avoid); break;
				case '8': sizeofvocab(Files, Avoid); break; 
				case '9': intersecAll(Files, Avoid); break;
				case '0': System.out.print("Bye"); break;
				default: System.out.println("Incorrect input.");
			}//switch
		}while(op != '0');		
	}//main
static List<Item > intersec(List <Item> X, List <Item> Y){ //intersection of two arrayLists
	List <Item > Res = new ArrayList <>();//to put the intersected Item's 
	for(Item w : X){//looping through arrayList X
		for(Item y : Y){//looping through arrayList Y
			if(w.word.equals(y.word)) //if common words in the two arrayLists add to Res
				Res.add(w);
		}//for2
	}//for1
	return Res;
}//intersect
static void intersecAll(List <String> Files, List <String> Avoid){ //intersection of every text file in the database
	List <List <Item>> All = new ArrayList <>();//create an arrayList of arrayLists
	for(String w : Files){ //looping through every file in your database
		List <Item> fileList = read(w, Avoid); //read w and put it in a new list "fileList"
		All.add(fileList);// add to all
	}
	List <Item> Res = new ArrayList <>(); //List to put intersecting Item's inside
	Res = intersec(All.get(0), All.get(1));//intersection of the first two files in the database
	for(int i = 2; i < All.size(); i++){//finding intersections with Res and going through all the files in the database
		Res = intersec(Res, All.get(i));//intersected array
	}
	List <String> wordsIntersect = new ArrayList<>(); //to add only words from Res and get rid of frequencies
	for(Item w : Res){
		wordsIntersect.add(w.word);
	}
	Collections.sort(wordsIntersect);//sorting the intersected words alphabetically
	System.out.println("Intersection WORDS: \n" + wordsIntersect);
	int count = 0;
	for(List <Item> file : All){//looping through files inside All
		List <Item> A = new ArrayList<>();
		for(Item x : file){//looping through the specific file
			for(String w : wordsIntersect){ //go through the intersect list of words Res
				if(x.word.equals(w))
					A.add(x);
			}//for
		}//for
		Collections.sort(A, Item.wordComp);//sort the intersected words in each file alphabetically
		System.out.println(Files.get(count) + ": \n"+ A ); 
		count++;
	}//for
}//intersecAll
static void SortFrequency(List <String> Files, List <String> Avoid){//sorting in decreasing frequency
	if(Files.isEmpty())
	System.out.println("Empty database. Please add files first \n\n");
	else{
		System.out.print("Sort all files in the database or files of your choice? (all - 1, some - 2)");
		int n = cin.nextInt();
		if(n == 1){//if all the files
			for(int i = 0; i< Files.size(); i++){//loop through the files in the database
				List <Item> ResSort = read(Files.get(i),Avoid);//read the file
				Collections.sort(ResSort, Item.manyComp); //sort the list alphabetically
				System.out.print("\n\n" + Files.get(i) + " Sorted by Frequency: \n\n" + ResSort + "\n\n");
			}//for	
		}//if
		else{ 
			char op;
			do{
				int count = 0;//count to check if the file exists in the database and your folder
				System.out.print("Enter the file name: ");
				String name = cin.next();
				Scanner fin = null;
				try{fin = new Scanner(new File(name));}
				catch(IOException ex) {count++; System.out.print(ex);} //if the file is not in the database, count++
				if(count == 0){//if the file is in the database
					List <Item> ResSort = read(name, Avoid);
					Collections.sort(ResSort, Item.manyComp);//sort by frequency 
					System.out.print("\n\n" + name + " Sorted by Frequency: \n\n" + ResSort + "\n\n");
					}//if
				else{//if file not in the database
					System.out.println("Incorrect file name!");
				}//else
				System.out.print("Sort another file? (y/n) ");
				op = cin.next().charAt(0);
			}while(op != 'n');
		}//else2
	}//else1
}//sort by frequency
static void sizeofvocab(List <String> Files, List <String> Avoid){ //count the size of the author's vocabulary
	if(Files.isEmpty()){
		System.out.println("Your database is empty. Please add files first");
	}
	else{	
		System.out.print("Do you want to display the size of vocabulary in all files in the database or just one specific file? (1 - one file; 2 - all files) ");
		int n = cin.nextInt();
		if( n == 1){//if one file only
			int count = 0;
			System.out.print("Enter the file name: ");
			String filename = cin.next();
			Scanner fin = null;
			try{fin = new Scanner(new File(filename));}
			catch(IOException ex) {count++; System.out.println(ex);} //if file not in the database count++
			if(count == 0){//if file in database
				System.out.println("Vocabulary size: " + read(filename, Avoid).size()); //find the size of the arrayList returned by read()	
			}//if	
			else{//if file not in the database
				System.out.println("Incorrect text file name. Please try again.");
			}//else
		}//if
		if(n ==2){//if all the files
			int count = 0;
			for(int i = 0; i <Files.size(); i++){//looping through all the files in the database
				Scanner fin = null;
				try{fin = new Scanner(new File(Files.get(i)));}
				catch(IOException ex) {count++; System.out.print(ex);}	//if file not in the database count++
				System.out.println("File name: " + Files.get(i));
				if(count == 0){//if file in the database
				System.out.println("Vocabulary size: " + read(Files.get(i), Avoid).size());//find the size of the List returned by read()
				}//if
				else{//if file not in the database
					System.out.println("Incorrect text file name. Please try again.");
				}//else
			}//for
		}//if
	}//else1
}//size of vocabulary
static void searchWord(List <String> Files, List <String> Avoid){//user searches for a specific word in either one or all the files
	if(Files.isEmpty())//checks if the file is empty and if it is asks user to add a file first
		System.out.println("Your database is empty. Please add files first");
	else{	//the user can either choose to search for the word in one or all the files
		System.out.print("Do you want to search in all files in the database or just one specific file? (1 - one file; 2 - all files) ");
		int n = cin.nextInt();
		int count = 0;
		if( n == 1){//if the user chooses to search in only one file
			System.out.print("Enter the file name: ");
			String filename = cin.next();//converts all the letters to lower case
			Scanner fin = null;
			try{fin = new Scanner(new File(filename));}//checks if the file the user entered exists
			catch(IOException ex) {count++; System.out.print(ex);}//checks if the file is in the database
			if(count ==0){//the file is in the database
				List <Item> ResSearch = read(filename, Avoid);
				System.out.println("Enter the word you want to search in " + filename + ": ");
				String myword = cin.next().toLowerCase();	//the user enters what word they want to search for
				for(int i = 0; i <ResSearch.size(); i++){//loops through the file
					if(myword.equals(ResSearch.get(i).word))//if the word is in the file
						System.out.println(filename + ": " + myword + " found " + ResSearch.get(i).many + " times");//the word is printed with how many times it is in the text
				}//for
			}//if
			else{
				System.out.println("Incorrect text file name. Please try again.");
			}//else
		}//if
		else{	
			for(int i = 0; i < Files.size(); i++){//loops through all the files
				Scanner fin = null;
				try{fin = new Scanner(new File(Files.get(i)));}
				catch(IOException ex) {System.out.print(ex);}
				List <Item> ResSearch =read(Files.get(i),  Avoid);
				System.out.println("Enter the word you want to search in " + Files.get(i) + ": " );//asks user to enter the word they want to searh for
				String myword = cin.next().toLowerCase();	//user enters word they want to search for
				for(int k = 0; k <ResSearch.size(); k++){//loops though the files to find the word
					if(myword.equals(ResSearch.get(k).word))
						System.out.println(Files.get(i) + ": " + myword + " found " + ResSearch.get(k).many + " times");//outputs the word and the frequency
				}//for
			}//for
		}//else
	}//else
}//search for a word in the file	
static void sortAlphabet(List <String> Files, List <String> Avoid){//sorts the words in the file alphabetically
	if(Files.isEmpty())
		System.out.println("Empty database. Please add files first \n\n");
	else{
		System.out.print("Sort all files in the database or files of your choice? (all - 1, some - 2)");//the user can choose how many of the files they would like to display
		int n = cin.nextInt();
		if(n == 1){//if they want to display all of the files
			for(int i = 0; i< Files.size(); i++){//loops through the files
				List <Item> ResSort = read(Files.get(i),Avoid);
					Collections.sort(ResSort, Item.wordComp);//sorts the words in the file alphabetically  
				System.out.print("\n\n" + Files.get(i) + " Sorted Alphabetically: \n\n" + ResSort + "\n\n");
			}//for	
		}//if
		else{ //if the user only wants to sory one file alphabetically
			char op;
			do{
				int count = 0;
				System.out.print("Enter the file name: ");
				String name = cin.next();
				Scanner fin = null;
				try{fin = new Scanner(new File(name));}
				catch(IOException ex) {count++; System.out.print(ex);} //if the file is not in the database, count++
				if(count == 0){
					List <Item> ResSort = read(name, Avoid);
						Collections.sort(ResSort, Item.wordComp); //sorts the file alphabetically
					System.out.print("\n\n" + name + " Sorted Alphabetically: \n\n" + ResSort + "\n\n");
					}//if
				else{
					System.out.println("Incorrect file name!");
				}//else
				System.out.print("Sort another file? (y/n) ");//asks the user if they want to sort another file
				op = cin.next().charAt(0);
			}while(op != 'n');//exits the loop if the user says no
		}//else2
	}//else1
}//sortAlphabet
static int searchFile(String file, List <String> A) { //search for files in the data base. If the file is not there, return -1, otherwise return the index
	for (int i = 0; i < A.size() ; i++){//loops through the files int the database
		if (file.equals(A.get(i))) return i;//find the index of the file user entered and if it is found it returns the index of the file
	} return -1;//if no file found
}//searchFiles
static void add(List <String> A){ //Add files to the list
	int count = 0;
	System.out.print("Enter the file name: " );
	String filename = cin.next();
	Scanner fin = null; //check the file user entered exists
	try{fin = new Scanner(new File(filename));}
	catch(FileNotFoundException ex) {count++; System.out.println(ex + "\nPlease enter the correct file name\n\n");} //if user entered an incorrect file name dont add it
	if(count == 0){ //if user entered a correct file name add it
		A.add(filename);
		System.out.println("File Added. \n" + A + "\n\n");
	}//if
}//add
static void remove(List <String> A){ //remove files from the list
		System.out.print("which text file do you want to delete? (Enter the file name) ");
		String file = cin.next();
		int index = searchFile(file, A);//finds the index of the file the user wants to remove
		if(index>=0){//if the index is equal to or greater than 0
			A.remove(index);//the file is remmoved
			System.out.println("File " + file + " removed. \n" + A + "\n\n");//prints the file that was removed and all the updated database
		}//if	
		else
			System.out.println("Please enter a correct file name \n\n");
}//remove	
static List<Item> read(String filename, List <String> Avoid){//reads the text files int he database
		List <Item> Res = new ArrayList <>();//creates an empty arraylist for the text files entered
		Scanner fin = null;
		try{fin = new Scanner(new File(filename));}
		catch(FileNotFoundException ex) {System.out.print(ex);}
		while(fin.hasNext()){//reads the file
			String s = fin.next().toLowerCase(); //convert every word to lowercase
			s = checkPunctuation(s); //check if s contains punctuation marks and gets rid of them
			int i = myindexOf(s, Res); //index where the word is found
			if(i== -1){ //if the word is not in Res already
				if(avoidCheck(s, Avoid)) //if the word is not to be avoided  //changed this
					Res.add(new Item(s,1)); //add the word to Res 1 time
			}//if
			else Res.get(i).many++; //if the word is already in Res, many++
		}//while
		return Res;//returns the words and their frequencies in the file
}//read
static void readAvoid(String filename, List <String> A){//the words that should be avoided when reading the file
	Scanner fin = null;
	try{fin = new Scanner(new File(filename));}//reads all the words in the file
	catch(FileNotFoundException ex) {System.out.print(ex);}
	while(fin.hasNext()){
		String s = fin.next();
		A.add(s);//adds all the words to be avoided to an arrayList
	}//while
}//readAvoid
static String checkPunctuation(String s){//gets rid of all the extra punctuation marks at the end of the word
	List <Character> punc = new ArrayList<>();
	String punc2= "\"[]].,:;?!()_";//the punctuation marks that need to be avoided
	char c = s.charAt(s.length()-1); String cs = Character.toString(c);//
	if (punc2.indexOf(cs)>0){//last character is in punc
		s = s.substring(0,s.length()-1);
			return checkPunctuation(s);
	}//if
	return s;
}//checkPunctuation	
static boolean avoidCheck(String w, List <String> Avoid){ //if the word is conatined in the Avoid list, don't add to Res.
	String numbers = "0123456789-";
	if (Avoid.contains(w)) return false;
    if(w.charAt(0) == '-'|| w.charAt(0) == '_' || w.charAt(0) == '[' || w.charAt(0) == '&') //if the word is the speaker's name, don't add it to Res
  		return false;
	for(int i = 0; i< w.length(); i++){///loop through the word and if the word contains numbers return false
		if (numbers.indexOf(w.charAt(i))>0){
			return false;
		}//if
	}//for
	return true;
}//avoidCheck		
static int myindexOf(String w, List <Item> Res){
	for(int i = 0; i< Res.size(); i++){
		if(w.equals(Res.get(i).word)) return i;
	}//for	
	return -1;
}//myindexOf
}//class
class Item{
	String word; int many;
	Item (String ss, int n) {word = ss; many = n;} 
	public String getWord() { return word;}
	public int getMany() { return many;}
	public String toString() {return word + " " + many;}
	
	public static Comparator <Item> wordComp = new Comparator <Item>(){
		public int compare(Item w, Item k){
			return w.word.compareTo(k.word);
	}};//wordComp comparator

	public static Comparator <Item> manyComp = new Comparator <Item>(){
		public int compare(Item w, Item k){
			if (w.many < k.many) return 1;
			if (w.many > k.many) return -1;
			return 0;
	}};//manyComp comparator
}//class item