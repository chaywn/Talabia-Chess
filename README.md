
## Compile and run instructions

The Talabia Chess game is a two-player chess game that is implemented as a GUI-based Java Application. To compile and run the application, ensure that JavaJDK is installed with JavaFx.

Below are the instructions for compiling and running the Talabia Chess game.

1. Open your **command prompt** or **terminal** window and _cd_ to the project folder  (/Talabia-Chess).
2. Run the following command to compile all .java files in the project folder:
    **javac main/*.java chessboard/*.java chessgame/*.java** **chesspiece/*.java player/*.java observer/*.java**
3. In the same directory, run the following command to run the Talabia Chess game:
    **java main/ChessGameContainer**

(Alternative: Using JAR file)

4. To compile and run the game using JARfile, go through Step 1 and Step 2 to compile all .java files in the project folder.
5. Still in the project directory, run the following command to create a JARfile (replace <jar-file-name> with the filename):
       **jar cfe <jar-file-name>.jar main.ChessGameContainer** **main/*.class chessboard/*.class chessgame/*.class** **chesspiece/*.class player/*.class observer/*.class** **Icons/BluePieces/*.png Icons/YellowPieces/*.png**
6. Finally, run the following command to run the Talabia Chess game (replace <jar-file-name> with the filename):
       **java -jar <jar-file-name>.jar**
7. Optionally, run the pre-created _Talabia-Chess.jar_ file in the project folder to instantly start the program.

## Game
### Start Game
![Start Game Screen](/Screenshots/start-game.png)  
To start the game, click the "New Game" button in the side panel.


### Play/MovePiece
![Move Piece Screen (GREEN)](/Screenshots/move-piece-green.png)  
![Move Piece Screen (RED)](/Screenshots/move-piece-red.png)  
<img src="/Screenshots/move-piece-red.png" width="100">
To play or move a chess piece, click and drag a chess piece of the current player on the board. When dragging a piece, the grid which the mouse pointer is hovering over will change its colour to green if it is a valid destination, indicating that the piece can move to that particular position. If the grid is an invalid destination, it will change its colour to red. 

Releasing the mouse drag at a valid (green) grid will move the chess piece onto its new destination. Else, the piece will return to its original position. 
The movement of each chess piece is as follow:
![Piece Movement Table](/Screenshots/piece-movement.png)  


### Switch Turn
![Side Panel](/Screenshots/switch-turn.png)  
Figure above shows the side panel update after a player played a move. The side panel contains game buttons and information about the current player. After playing a chess piece, the “Has played” property on the side panel will change to “true”, indicating that the current player has played a move. 

Once in this state, the current player cannot play or move another chess piece. The “Switch Turn” button will be enabled, allowing the current player to switch the turn to the opponent player. Switching turns will update the side panel again, flip the board to view from the opposite point of the view, and highlight the grid last played by the previous player, as shown below:  
![Board Flipl](/Screenshots/board-flip.png)  

### Save Game
![Save Game Screen](/Screenshots/save-game.png)  
To save a game, click the “Save Game” button to save the current game progress to a file.

### Load Game
![Load Game Screen](/Screenshots/load-game.png)  
To load a previously saved game, click the “Load Game” button to load the game data from a chosen save file.

### Piece Switch
![Piece Switch Message](/Screenshots/piece-switch-before.png)  
At every two turns, players will be notified that all Time pieces and Plus pieces will switch types. This is done automatically after each player has played two rounds. 

After piece switch:  
![Piece Switch Screen](/Screenshots/piece-switch-after.png)  

### Player Win
![Player Win Screen](/Screenshots/player-win.png)  
When one of the players has their Sun piece captured, the game ends and the opponent player is declared as the winner. Players can choose “Yes” to start a new game, or choose “No” to quit the game.

### Restart Game
![Restart Game Screen](/Screenshots/restart-game.png)  
If players wish to restart a game while a game is ongoing, click the “New Game” button, then choose “Yes” at the popup prompt to start a new game or choose “No” to cancel and retain the current progress.

### Exit Game
![Exit Game Screen](/Screenshots/exit-game.png)  
If players wish to exit the game, click the “Exit Game” button, then choose “Yes” at the popup prompt to exit the application or choose “No” to cancel and retain the current progress.

## Contributors
- Chay Wen Ning
- Melody Koh Si Jie
- Goh Shi Yi
- Choo Yun Yi
- Mohamed Kamal Eldin
