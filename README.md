<h1> Santorini</h1>
<h3>Prova Finale di Ingegneria del Software - A.A. 2019-2020 </h3>

![alt text](src/main/resources/Home/home_logo.jpg?raw=true)
<h2>About the project</h2>
<p> The aim of the project was to develop a software version of the board game <i>Santorini</i>.<br>
Santorini is an abstract strategy board game designed and released in 2004 by Gordon Hamilton <br>
and republished via Kickstarter in 2016 by Roxley Games. <br>
Inspired by the architecture of cliffside villages on Santorini Island in Greece,<br>
the game is played on a grid where each turn players build a town by placing building pieces up to three levels high.<br>
To win the game, players must move one of their two characters to the third level of the town.
</p>

<h4>Features</h4>
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
      <li>Zeus</li>
    </ul>
    </li>
</ul>

<h4>Documentation</h4>
<ul>
<li><a href = "https://github.com/alb-p/ing-sw-2020-Papiri-Peltrera-Jayasinghe/tree/master/deliveries/Javadoc"><b>JavaDoc</b> </a></li>
<li><a href = "https://github.com/alb-p/ing-sw-2020-Papiri-Peltrera-Jayasinghe/tree/master/deliveries/Coverage"><b>JavaDoc</b></a></li>
</ul>


<h2>How to run</h2> 
<h4> Prerequisites </h4>
<p>The game requires Java 11 or later versions to run.<br>
UTF-8 support is strongly recommended for a more enjoyable CLI gaming experience.</p>

<h4> Client </h4>

```bash
$ java -jar SantoriniClient.jar cli ip-address port
```
<ul>
<li>type <code>-cli</code> to play by <i>Command Line Interface</i> [CLI]. <i>Graphical User Interface</i> [GUI] will be launched by default. </li>
<li>type the <code>ip-address</code> you want to connect to. <i>Localhost</i> server is chosen by default  </li>
<li>type the <code>port</code> you want to connect to. <i>4566</i> port is chosen by default</li>
</ul>
All flags are optional. With no flags will start GUI on localhost.

<h4> Server </h4>

```bash
$ java -jar SantoriniServer.jar port
```
<ul>
<li>type the <code>port</code> you want to connect to. <i>4566</i> port is chosen by default</li>
</ul>
All flags are optional. With no flags the server will start on localhost.


<h2>Developers: Group GC20</h2>
<a href = "https://github.com/alb-p/"><b>10566115 Alberto Papiri</b> </a><br>
<a href = "https://github.com/gioelepeltrera/"><b>10560633 Gioele Peltrera</b> </a><br>
<a href = "https://github.com/sandroJaya/"><b>10530600 Sandro Shamal Jayasinghe</b> </a>