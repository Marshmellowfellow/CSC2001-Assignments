

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = timeStamp.java\
	  PowerArrayApp.java\

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
