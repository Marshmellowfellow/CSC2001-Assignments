SRCDIR = src
BINDIR = bin
TEST = bin/test

JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $<

CLASSES = Graph.java\
          SimulatorOne.java\


vpath %.java $(SRCDIR) /Graph:$(SRCDIR)/SimulatorOne:$(SRCDIR)
vpath %.class $(BINDIR) /Graph:$(BINDIR)/SimulatorOne:$(BINDIR)

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f  $(BINDIR)/*.class

cleanresults:
	$(RM) -f  $(TEST)/*.txt 
