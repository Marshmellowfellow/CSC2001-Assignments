SRCDIR = src
BINDIR = bin

JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $<

CLASSES = timeStamp.java\
	  textWrite.java\
	  Node.java\
	  opCount.java\
	  BinaryTree.java\
	  PowerArrayApp.java\
	  PowerBSTApp.java\
	
vpath %.java $(SRCDIR) /textWrite:$(SRCDIR)/Node:$(SRCDIR)/opCount:$(SRCDIR) /BinaryTree:$(SRCDIR)/PowerArrayApp:$(SRCDIR) /PowerBSTApp:$(SRCDIR)
vpath %.class $(BINDIR) /textWrite:$(BINDIR)/Node:$(BINDIR) /BinaryTree:$(BINDIR) /PowerArrayApp:$(BINDIR) /PowerBSTApp:$(BINDIR)/PowerBSTApp:$(BINDIR)

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f  $(BINDIR)/*.class
