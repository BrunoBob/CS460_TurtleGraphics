COMPIL = javac
FLAG =
NAME_CLIENT = TurtleClient
NAME_SERVER = TurtleServer

all: $(NAME_CLIENT).class $(NAME_SERVER).class

$(NAME_CLIENT).class: $(NAME_CLIENT).java clean
	$(COMPIL) $(FLAG) $(NAME_CLIENT).java

$(NAME_SERVER).class: $(NAME_SERVER).java TurtleCanvas.class
	$(COMPIL) $(FLAG) $(NAME_SERVER).java

TurtleCanvas.class: TurtleCanvas.java
	$(COMPIL) $(FLAG) TurtleCanvas.java

TurtleServerManager.class: TurtleServerManager.java
	$(COMPIL) $(FLAG) TurtleServerManager.java

clean:
	rm $(NAME_CLIENT).class $(NAME_SERVER).class TurtleCanvas.class TurtleServerManager.class
