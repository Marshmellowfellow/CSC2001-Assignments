

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = timeStamp.java\
	  textWrite.java\
	  Node.java\
	  BinaryTree.java\
	  opCount.java\
	  PowerArrayApp.java\
	  PowerBSTApp.java\
	 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
