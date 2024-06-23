#!/bin/bash

# Bucles infinitamente para permitir múltiples ejecuciones
while true; do
  # Imprimir mensaje para indicar que está esperando una nueva instrucción
  echo "Esperando comandos... (Ingrese 'exit' para finalizar)"

  # Leer la instrucción del usuario
  read -p ">" cmd

  # Si el usuario escribe 'exit', terminar el bucle y cerrar el contenedor
  if [ "$cmd" == "exit" ]; then
    echo "Saliendo..."
    break
  fi

  # Ejecutar el comando especificado por el usuario
  # Aquí se asume que cmd es el comando completo como "java -jar <tu_programa.jar>"
  $cmd

  # Opcional: agregar un retardo antes de la siguiente iteración para evitar sobrecarga
  sleep 1
done
