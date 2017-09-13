# quakeLogParser
Hands-on project for an internship application @ In Loco Media

This application is a command-line application that can read a log file from 
the game Quake III specified by the user and then creates summaries of the games 
and/or classification rankings of the players based on their kill count through 
the games in the log file.  

The ranking list can also be parsed into a provided .html file located at 
resources/base-ranking-page.html. The base must contain a table with id="rankingTable". 
The ranking list will the be parsed as one line per player in the table and a copy 
of the page will be saved in the OUTPUT path provided (default is ./Ranking.html)  

usage: java -jar quakeLogParser.jar <FILENAME> [-hsr] [-w [-o <OUTPUT>]]  
 -h,--help          Displays the application description and the available  
                    options.  
 -s,--summary       Displays a summary of the games in the log file with  
                    information about kills and players.  
 -r,--ranking       Displays the ranking of the players in the log file  
                    based on their kills in all the games.  
 -w,--web-ranking   Creates a web page showing the ranking of the players  
                    basesd on their kills in all the games.  
 -o <arg>           The file path for the ranking web page to be created.  
