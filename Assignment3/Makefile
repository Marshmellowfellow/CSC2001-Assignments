SRCDIR = src
BINDIR = bin
TEST = bin/test

JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $<

CLASSES = textWrite.java\
          opCount.java\
          timeStamp.java\
          CSVread.java\
          HashProbe.java\
          HashChain.java\


vpath %.java $(SRCDIR) /textWrite:$(SRCDIR)/opCount:$(SRCDIR)/timeStamp:$(SRCDIR)/CSVread:$(SRCDIR)/HashProbe:$(SRCDIR)/HashChain:$(SRCDIR)
vpath %.class $(BINDIR) /textWrite:$(BINDIR)/opCount:$(BINDIR) /timeStamp:$(BINDIR)/CSVread:$(BINDIR)/HashProbe:$(BINDIR)/HashChain:$(BINDIR)

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f  $(BINDIR)/*.class

cleanresults:
	$(RM) -f  $(TEST)/*.txt 
