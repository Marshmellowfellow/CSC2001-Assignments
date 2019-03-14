

JFLAGS = -g 
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $<

CLASSES = PowerAVLApp.java\
	  TreePrinter.java\
	  timeStamp.java\
	  textWrite.java\
	  Node.java\
	  opCount.java\
	  BinaryTree.java\
	  PowerArrayApp.java\
	  PowerBSTApp.java\


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f  *.class
