<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Informations Employé</title>
    <%@ include file="importation/link_header.jsp" %>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <h3 class="text-center mt-4">Mes informations personnelles</h3>
                    <div class="col-8 mx-auto mt-4">
                        <p><strong>Nom :</strong> Dupont</p>
                        <p><strong>Prénom :</strong> Jean</p>
                        <p><strong>Email :</strong> jean.dupont@example.com</p>
                        <p><strong>Poste :</strong> Développeur</p>
                        <button class="btn btn-danger btn-block mt-4">Rupture de contrat</button>
                    </div>
                </div>
                <%@ include file="partial/footer.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="importation/link_footer.jsp" %>
</body>

</html>
