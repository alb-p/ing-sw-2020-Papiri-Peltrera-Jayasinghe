# Santorini
###Prova Finale di Ingegneria del Software - a.a. 2019-2020 

![alt text](src/main/resources/Home/Home_title.jpg?raw=true)
##About the project
<p> The aim of the project was to develop a software version of the board game <i>Santorini</i>.<br>
Santorini is an abstract strategy board game designed and released in 2004 by Gordon Hamilton <br>
and republished via Kickstarter in 2016 by Roxley Games. <br>
Inspired by the architecture of cliffside villages on Santorini Island in Greece,<br>
the game is played on a grid where each turn players build a town by placing building pieces up to three levels high.<br>
To win the game, players must move one of their two characters to the third level of the town.
</p>

###Features
<ul>
<li>Complete rules</li>
<li>CLI</li>
<li>GUI</li>
<li>Socket</li>
<li>Multiple matches</li>
<li>Advanced gods
    <ul>
      <li>Chronus</li>
      <li>Hestia</li>
      <li>Hypnus</li>
      <li>Poseidon</li>
      <li>Hestia</li>
      <li>Zues</li>
    </ul>
    </li>
    
</ul>

### Prerequisites
<p>The game requires Java 11 or later versions to run.<br>
UTF-8 support is strongly recommended for a more enjoyable CLI gaming experience.</p>

##How to run

#### Client

<pre> $ java -jar SantoriniClient.jar -cli ip-address port </pre>
<ul>
<li>type <code>-cli</code> to play by <i>Command Line Interface</i> [CLI]. <i>Graphical User Interface</i> [GUI] will be launched by default. </li>
<li>type the <code>ip-address</code> you want to connect to. <i>Localhost</i> server is chosen by default  </li>
<li>type the <code>port</code> you want to connect to. <i>4566</i> port is chosen by default</li>
</ul>
All flags are optional. With no flags will start GUI on localhost.

#### Server

<pre> $ java -jar SantoriniServer.jar port </pre>
<ul>
<li>type the <code>port</code> you want to connect to. <i>4566</i> port is chosen by default</li>
</ul>
All flags are optional. With no flags will star on localhost.


##Developers: Group GC20
<a href = "https://github.com/alb-p/"><b>10566115 Alberto Papiri</b> </a><br>
<a href = "https://github.com/gioelepeltrera/"><b>10560633 Giole Peltrera</b> </a><br>
<a href = "https://github.com/alb-p/"><b>10530600 Sandro Shamal Jajasynghe</b> </a>