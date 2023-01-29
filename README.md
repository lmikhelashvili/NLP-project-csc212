# Text Analysis Tool for Natural Language Processing (NLP)

## Project for CSC 212 at Smith College, Fall 2020

## Authors: Lika Mikhelashvili, Olivia Anastassov

### Software:

To run the program, you will need to have java software installed on your computer.
After navigating to the folder that contains all the relevant java and text files, from the terminal
run the command:
        
   java project.java


### Abstract:


Comparing files of user’s choice. The program allows the user to add and remove as
many text files as they want into the program’s database, which is empty in the beginning. The
program has a pre-installed text file, called “wordstoavoid”, containing the basic words and
articles, such as “a”, “the”, “he”, “she”, etc. The program will read the files in the database,
ignoring the words in “wordstoavoid” text file and will count the frequencies of the important
vocabulary words in each text. The user will also be able to view the size of the author's
vocabulary for one or all text files in the database.

The user has the opportunity to view the words in each text sorted both alphabetically and
numerically (according to the frequency of usage). Moreover, the user will have the opportunity
to enter a word for which the search engine will then output the word’s frequency in either one or
all the files.

If the user loads multiple text files into the database, they will be able to see the words
that occur in each text file and see how many times those words have been repeated in each file.

The program can read any text file of the user’s choice. We will, however, provide the
texts we found interesting: Hamlet and The Tempest by William Shakespeare and will compare
the word usage in a comedy and tragedy.


### Files:

Text Files:

1. wordstoavoid.txt

2. the_tempest.txt

3. hamlet.txt

4. macbeth.txt

Java Files:

1. project.java
