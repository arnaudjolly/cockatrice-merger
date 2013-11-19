# What the hell is this tool ?!

First, to see a use of this tool, you need to want to play Magic:The Gathering on the Internet with the Cockatrice software (that one that has stopped to be developped by its first developper team after they received a Cease-And-Desist letter from Wizards of The Coast).

So if you grabbed the sources in time and compiled it you should have noticed that Wizards tricked the card crawler oftenly used with Cockatrice: Oracle.
They tricked it by two means:
- changing some tags in the cards page for this tool to lose its tracks and go like Dave: CraAaaAazyyyyy by telling you that each set you downloaded has no card in it.
- then sometimes when you ask for a specific set on the gatherer website, you get an empty page.I think that it can happen if you ask for a suspectedly high amount of pages of that kind, BAM you received this empty page.

This tool can help you for this precise second problem as the first can be solved by jumping into the source code of the Oracle tool and change a string like "Name :" into "Name " or something like that.

# How can this tool help me with this blank page problem ?

So, we supposed you solved the first problem. You compiled the Oracle tool, run it, select all the sets you want to download and begin the downloading...

BAM! Some sets have 0 card in them and others seem fine. As you want a perfect card-database, you have some solutions:
- downloading the entire sets again hoping this time it will work for every sets
- asking for a complete {cards.xml} to a friend with the risk that he personified his sets 'shortname' and you will lose your picture settings (i.e. the pictures of cards you already have downloaded will stay in place but Cockatrice will forget them and redownload them as they are not from the same set for it)
- use this stupid but useful tool.

# I can't believe it! Tell me more!

Well, you can do this last step by:
- putting the generated cards.xml file that has every sets that contained cards from your last download
- downloading the failed sets again and repeat these 2 steps until you have no problems
- use this tool to merge all the cards.xml you collected from this loop.

# Ok, what do I need to run your tool ?

## To build it...
You need a Java IDE (Eclipse, IntelliJ Idea, Netbeans, VIm, don't take eMacs as it stinks! ;) ), a Java SDK and a configured Maven.

If this seems like hieroglyphs to you, just grab the JAR file at the root directory and follow the next paragraph.

## To run it...
You need Java as this tool is an executable JAR file and as we are true men we use the powerful command-line!

Get a prompt and type:
  java -jar <name-of-the-jar-file.jar> <file1> <file2> ...
where file1, file2, ... are the successive path of cards.xml files you collected before.

This tool will generate a cards.fused.xml that is what you are looking for till the beginning. Rename it in cards.xml and say to Cockatrice to use that file as a carddatabase and you got it!
