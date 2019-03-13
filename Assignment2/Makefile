

JFLAGS = -g 
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $<

CLASSES = PowerAVLApp.java\
	  TreePrinter.java\
       timeStamp.java\
       opCount.java\


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -f  *.class
