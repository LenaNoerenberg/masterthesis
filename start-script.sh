#!/bin/bash
# bash command to open a new terminal
gnome-terminal
exec mvn spring-boot:run

# bash command to open a new terminal
gnome-terminal
cd webapp
exec npm start