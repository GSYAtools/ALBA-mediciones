# Makefile para el proyecto SecurityAlgorithms
IMAGE_NAME=security
JAR_PATH=SecurityAlgorithms/target/SecurityAlgorithms-1.1-jar-with-dependencies.jar
APP_NAME=SecurityAlgorithms.jar
DOCKER_DIR=docker
CONTAINER_NAME=security-test
TEST_FILES=test_files

# Objetivo por defecto (ayuda)
.DEFAULT_GOAL := help

# Regla para crear el directorio docker y el Dockerfile
prepare: $(DOCKER_DIR)/Dockerfile

$(DOCKER_DIR)/Dockerfile: | $(DOCKER_DIR)
	echo "FROM openjdk:20" > $(DOCKER_DIR)/Dockerfile
	echo "COPY . /usr/app" >> $(DOCKER_DIR)/Dockerfile
	echo "WORKDIR /usr/app" >> $(DOCKER_DIR)/Dockerfile

# Regla para crear el directorio docker
$(DOCKER_DIR):
	mkdir -p $(DOCKER_DIR)

# Regla para copiar el JAR al directorio docker
copy-jar: $(DOCKER_DIR)
	cp $(JAR_PATH) $(DOCKER_DIR)/$(APP_NAME)
	cp $(TEST_FILES)/* $(DOCKER_DIR)

# Regla para construir la imagen Docker, el contenedor e iniciarlo
build: prepare copy-jar
	docker build -t $(IMAGE_NAME) $(DOCKER_DIR)
	docker run -d --name $(CONTAINER_NAME) $(IMAGE_NAME)
	
#Regla para detener el contenedor
stop:
	docker stop $(CONTAINER_NAME)

#Regla para probar AES
aes:
	docker start $(CONTAINER_NAME)
	docker run -i $(IMAGE_NAME) sh test_aes.sh

# Regla para limpiar el directorio docker y eliminar el contenedor
clean:
	rm -rf $(DOCKER_DIR)
	docker rm -f $(CONTAINER_NAME) || true
	docker rmi -f $(IMAGE_NAME) || true
