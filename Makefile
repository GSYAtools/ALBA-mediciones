# Makefile para el proyecto SecurityAlgorithms

# Nombre de la imagen Docker
IMAGE_NAME=security

# Ruta al JAR generado
JAR_PATH=SecurityAlgorithms/target/SecurityAlgorithms-1.0-jar-with-dependencies.jar

# Directorio de destino para Docker
DOCKER_DIR=docker

# Nombre del contenedor
CONTAINER_NAME=security-test

# Objetivo por defecto (ayuda)
.DEFAULT_GOAL := help

# Regla para mostrar ayuda
help:
	@echo "Makefile para la construcción y gestión de la aplicación Docker"
	@echo ""
	@echo "Comandos disponibles:"
	@echo "  make prepare       - Crea el directorio docker y genera el Dockerfile"
	@echo "  make copy-jar      - Copia el JAR en el directorio docker"
	@echo "  make build-image   - Construye la imagen Docker llamada '$(IMAGE_NAME)'"
	@echo "  make run           - Ejecuta el contenedor Docker desde la imagen '$(IMAGE_NAME)'"
	@echo "  make remove-image  - Elimina la imagen Docker '$(IMAGE_NAME)'"
	@echo "  make clean         - Limpia el directorio docker y elimina el contenedor '$(CONTAINER_NAME)'"

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
	cp $(JAR_PATH) $(DOCKER_DIR)

# Regla para construir la imagen Docker
build-image: prepare copy-jar
	docker build -t $(IMAGE_NAME) $(DOCKER_DIR)

# Regla para ejecutar el contenedor Docker
run:
	docker run -ti --name $(CONTAINER_NAME) $(IMAGE_NAME) java -jar $(notdir $(JAR_PATH))

# Regla para eliminar la imagen Docker
remove-image:
	docker rmi -f $(IMAGE_NAME)

# Regla para limpiar el directorio docker y eliminar el contenedor
clean:
	rm -rf $(DOCKER_DIR)
	docker rm -f $(CONTAINER_NAME) || true
	docker rmi -f $(IMAGE_NAME) || true
