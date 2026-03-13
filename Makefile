LIB=lib/*
OUT=out

build:
	mkdir -p $(OUT)
	javac -cp "$(LIB)" -d $(OUT) $(shell find src/main/java -name "*.java")

example: build
	javac -cp "$(LIB):$(OUT)" -d $(OUT) examples/CreateNrdtGeneration.java

nrotToken: build
	javac -cp "$(LIB):$(OUT)" -d $(OUT) examples/NrotGeneration.java

nrdtToken: build
	javac -cp "$(LIB):$(OUT)" -d $(OUT) examples/NrdtGeneration.java

runExample: example
	java -cp "$(LIB):$(OUT)" CreateNrdtGeneration


runNROT: nrotToken
	java -cp "$(LIB):$(OUT)" NrotGeneration $(filter-out $@,$(MAKECMDGOALS))

%:
	@:

runNRDT: nrdtToken
	java -cp "$(LIB):$(OUT)" NrdtGeneration $(filter-out $@,$(MAKECMDGOALS))

%:
	@:

clean:
	rm -rf $(OUT)

