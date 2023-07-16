# worple
 A Wordle solver written in Java. I wrote this entire thing in a weekend. I was orginally planning a sick graph theory method of getting the most probably answer but I ran out of time.

 This solver uses uses data I compiled of every possible wordle-legal word along with each letter and the letters ranked by how common they com after the first. This allows worple to cut processing time in half as the most common words are placed first while less common words take more time to calculate. This solver also handles yellow letters in a way that increases efficiency over my last wordle solver.

![w o r p l e](https://i.imgur.com/okOJebk.png)

# Quirks
This is not nearly as quirky as the last wordle solver I wrote in C. That being said, it does have one weird bug I could fix, but don't really have the time currently. If you enter a word with two of the same character and there is only one instance of the letter in the solution, with the guess not having it in the correct position (i.e. it is yellow), the solver will never get the correct answer. The way to counter this is to simply mark the duplicate as yellow when inputting it in the right column instead of grey (y instead of x). There are probably more bugs, but that's the one I found first.

# Inspiration
These are some pretty cool sites with some information I used to determine the letters.

[norvig](https://norvig.com/mayzner.html)

[norvid but one of the dead links](https://blogs.sas.com/content/iml/files/2014/09/bigrams.txt)

[probability calculations](https://www.exploredatabase.com/2020/04/bigram-probability-estimation-of-word-sequence-example.html)

# How to
Compile with `javac *.java`

Run with `java GUI`

Input guess on left, colored responses on right

Info button for more help, solve for answer, and reset for clear. If nothing happens close it and restart, there's probably something wrong with your inputs or some weird edge case I didn't think of.