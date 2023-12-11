@echo off
setlocal
:TOP
echo Indique el nombre del fichero que desea analizar, dejar en blaco si quiere usar el fichero por defecto(prueba.txt).
set /p var=
java -jar procesadorBat.jar %var%
SET /P continuar= Quieres continuar haciendo pruebas? (Y/[N])?
IF /I "%continuar%" NEQ "Y" GOTO TERMINAR
GOTO TOP
:TERMINAR
endlocal

