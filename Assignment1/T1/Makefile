# Simple Makefile to compile A.java, B.java, C.java, with main()
# in C.java

BINDIR=./bin
SRCDIR=./src
DOCDIR=./javadocs


.SUFFIXES: .java .class

# General build rule: .java => .class
# This allows dependencies in different directories
# -cp => specified CLASSPATH
# -d  => where to write .class files

${BINDIR}/%.class: ${SRCDIR}/%.java
	javac $< -cp ${BINDIR} -d ${BINDIR} 

# first build rule
${BINDIR}/C.class: ${BINDIR}/A.class ${BINDIR}/B.class ${SRCDIR}/C.java


clean:
	rm -f ${BINDIR}/*.class

run:
	java -cp ./bin C

docs:
	javadoc  -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

cleandocs:
	rm -rf ${DOCDIR}/*


