<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Accueil </title>
    <%@ include file="importation/link_header.jsp" %>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <h3 class="text-center mt-4">Bienvenue sur votre espace Personnelles</h3>
                    <p class="text-muted text-center">Accédez rapidement aux informations essentielles</p>

                    <div class="row mt-5">
                        <div class="col-md-6">
                            <div class="card shadow mb-4">
                                <div class="card-body text-center">
                                    <i class="fe fe-calendar fe-32 text-info mb-3"></i>
                                    <h5 class="card-title">Demander un congé</h5>
                                    <p class="text-muted">Effectuez une nouvelle demande de congé.</p>
                                    <a href="/PreCongeEmploye" class="btn btn-info btn-sm">Faire une demande</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="card shadow mb-4">
                                <div class="card-body text-center">
                                    <i class="fe fe-user fe-32 text-warning mb-3"></i>
                                    <h5 class="card-title">Mes informations</h5>
                                    <p class="text-muted">Consultez vos informations personnelles.</p>
                                    <a href="/PreInformationEmploye" class="btn btn-warning btn-sm">Voir mes infos</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%@ include file="partial/footer.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="importation/link_footer.jsp" %>
</body>

</html>
