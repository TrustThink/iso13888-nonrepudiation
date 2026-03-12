LIB=lib/*
OUT=out

build:
	mkdir -p $(OUT)
	javac -cp "$(LIB)" -d $(OUT) $(shell find src/main/java -name "*.java")

example: build
	javac -cp "$(LIB):$(OUT)" -d $(OUT) examples/CreateNrdtExample.java

nrotToken: build
	javac -cp "$(LIB):$(OUT)" -d $(OUT) examples/NrotExample.java

nrdtToken: build
	javac -cp "$(LIB):$(OUT)" -d $(OUT) examples/NrdtExample.java

runExample: example
	java -cp "$(LIB):$(OUT)" CreateNrdtExample


runNROT: nrotToken
	java -cp "$(LIB):$(OUT)" NrotExample

runNRDT: nrdtToken
	java -cp "$(LIB):$(OUT)" NrdtExample

clean:
	rm -rf $(OUT)

