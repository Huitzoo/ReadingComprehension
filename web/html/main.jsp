<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html dir="ltr" lang="en">
    <head>
        <title>Iniciar Sesion</title>
        <link rel="icon" type="image/png" href="../img/fondo.png"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/estilo.css">
    </head>
    <body>
    <main role="main" class="container">
        <div class="container align-items-center content">
        <div class="row">
          <div class="col-sm">
          </div>
          <div class="col-sm top-buffer">
            <div class="jumbotron color-login"> 
              <h1>Inicia Sesión. </h1>
              <form method="get" action="servletAdmin">
                <p class="lead">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Nombre de Usuario:</label>
                        <input type="text" class="form-control"  placeholder="Ingresa tu nombre de usuario">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Contraseña:</label>
                        <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                        <small id="emailHelp" class="form-text text-muted">No muestres a nadie tu contraseÃ±a.</small>
                    </div>
                </p>
                <button type="submit" class="btn btn-primary">Submit</button>
             </form>
            </div>
          </div>
          <div class="col-sm"></div>
        </div>
        </div>
    </main>
    </body>    
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
</html>