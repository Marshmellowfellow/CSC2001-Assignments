SRCDIR = src
BINDIR = bin

JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $<

CLASSES = TreePrinter.java\
          textWrite.java\
          opCount.java\
          timeStamp.java\
          Node.java\
          BinaryTree.java\
	      PowerBSTApp.java\
          PowerAVLApp.java\


vpath %.java $(SRCDIR) /textWrite:$(SRCDIR)/Node:$(SRCDIR)/opCount:$(SRCDIR) /BinaryTree:$(SRCDIR)/PowerAVLApp:$(SRCDIR) /PowerBSTApp:$(SRCDIR)/timeStamp:$(SRCDIR)/TreePrinter:$(SRCDIR)
vpath %.class $(BINDIR) /textWrite:$(BINDIR)/Node:$(BINDIR) /opCount:$(BINDIR) /BinaryTree:$(BINDIR) /PowerAVLApp:$(BINDIR)/PowerBSTApp:$(BINDIR)/timeStamp:$(BINDIR)/TreePrinter:$(BINDIR)

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f  *.class 
	$(RM) -f  *.test
	$(RM) -f  *.test?
