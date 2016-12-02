COMPIL = javac
FLAG =
NAME_CLIENT = TurtleClient
NAME_SERVER = TurtleServer

all: $(NAME_CLIENT).class $(NAME_SERVER).class

$(NAME_CLIENT).class: $(NAME_CLIENT).java
	$(COMPIL) $(FLAG) $(NAME_CLIENT).java

$(NAME_SERVER).class: $(NAME_SERVER).java TurtleServerThread.class TurtleServerManager.class
	$(COMPIL) $(FLAG) $(NAME_SERVER).java

TurtleServerThread.class: TurtleServerThread.java
	$(COMPIL) $(FLAG) TurtleServerThread.java

TurtleServerManager.class: TurtleServerManager.java
	$(COMPIL) $(FLAG) TurtleServerManager.java

clean:
	rm *.class
