nombre = input()
html = open(nombre,"r")
salida = open("salida.html","w")
for linea in html:
    linea = linea.replace("\n","")
    s = 'out.println("'+linea+'");\n'
    salida.write(s)
html.close()
salida.close()
