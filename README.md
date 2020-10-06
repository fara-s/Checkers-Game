# Checkers-Game


Aim:

The Aim of this project is to allow two players to play the game of draughts on different client interfaces via sockets instead of direct gameplay. Players are able to start up server then connect to the interface by launching two clients. The server starts new thread each time a client connects.



Program Rules:

• The server port is 8765
• The host is "127.0.0.1"
• Only two players may connect to the server at one time.
• Only the player whose turn it is , are able to select and move checkers
• Player 1 is the first player to connect to the server, is automatically White draught
and goes first.


Game Rules:

• 8x8 board
• 12 game pieces each
• Game play is held on grey tiles
• Players are able to move diagonally forwards until they reach the opposite side of
the board, they then are able to move diagonally forwards and backwards(King
piece)
• Either Player is able to start a new game in the middle of playing a game
• If a player is able to take oppositions piece they must make this move.
• Game is won/lost when a player has no pieces remaining




Mechanisms:

The Server class starts up a new Game and waits for client to connect. For each client connected the server sends over the initial game states of objects. The client uses these to display the interface to the user and the start of the game . Once a click on the board is made the client sends over these coordinates and the player number to the server. The server passes it to the game class where It is processed and a new UpdateMove object is made with the first round of game play. This UpdateMove object is sent to the Client to be used to display the updated interface. This is repeated for each click the user makes.
If the user selects the new game button this is sent to the server which initiates the newGame() method in the Game class and a new game is started.

• The Game, DraughtsLogic and Move class are used to process the game play.
• The ClickedCoordinate and UpdateMove classes are used to pass information back
and forward from clients and server.
2131725s Fara Stringfellow
• The GameContainer class is used to display the draughts board, moves and pieces and update it.
• The Client class displays the GUI using information coming from the Game class via the Server. It also processes button and mouse clicks and sends this information to the Server
• The ReadMove class reads the updates from the server and sends it to the Client
• The DraughtsClientRunner class represents each client connection with the Server
and allows for the tasks to be carried out identically by being implemented in an array in the Server class. This class process the information going to and from the Clients and sends it to the singular Server.
• The Server class controls the singular game being played and allows for the updates to be passed to each Client


Implementation:

The draughts game was designed following the Model View Controller design pattern, then split into client server format. I could not quite manage to convert the 2D integer array (board[][]) into an ArrayList and back again so as it could be sent over the server to the client. Due to this the actual movement of the checkers could not be seen in the client view. The checkers were moved in the Game class so game can be played and the moves available are shown but just cannot see the actual checkers. I do believe the game play is accurate however as nothing was changed in the game class to accommodate for the client and server implementation.
Apart from this all other requirements were met with the game simulating a Draughts Game.
