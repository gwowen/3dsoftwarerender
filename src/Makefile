OUTPUT_NAME = main

SRC_DIR := .
OBJ_DIR := ../obj
OUTPUT_DIR := ../

JAVA := javac

JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)
JAVAOBJ_FILES := $(addprefix $(OBJ_DIR)/,$(notdir $(JAVA_FILES:.java=.class)))

main: $(JAVAOBJ_FILES)

$(OBJ_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVA) $< -d $(OBJ_DIR)


clean:
	rm -f $(OBJ_DIR)/*
	rm -f $(OUTPUT_DIR)/$(OUTPUT_NAME)
