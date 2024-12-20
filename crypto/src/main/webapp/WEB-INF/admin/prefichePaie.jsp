<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="itu.gestionrh.model.Contract" %>
<%@ page import="itu.gestionrh.model.Employe" %>
<%@ page import="itu.gestionrh.model.ContractType" %>
<%@ page import="itu.gestionrh.model.Quarter" %>
<%@ page import="itu.gestionrh.model.Role" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title> Liste Des Employées </title>
    <%@ include file="importation/link_header.jsp" %>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <%-- atao ato --%>
                    <div class="prefiche-paie">
                        <h1>Générer une Fiche de Paie</h1>
                        <form action="/generatePaySlip" method="post">
                            <label for="employe">Sélectionner un Employé :</label>
                            <select id="employe" name="employeId">
                                <!-- Options générées dynamiquement -->
                            </select>
                    
                            <label for="dateDebut">Date Début :</label>
                            <input type="date" id="dateDebut" name="dateDebut" required>
                    
                            <label for="dateFin">Date Fin :</label>
                            <input type="date" id="dateFin" name="dateFin" required>
                    
                            <button type="submit">Générer la Fiche de Paie</button>
                        </form>
                    </div>

                </div>
                <%@ include file="partial/footer.jsp" %>
            </div>
            <!-- main-panel ends -->
        </div>
        <!-- page-body-wrapper ends -->
    </div>
    <%@ include file="importation/link_footer.jsp" %>
</body>

</html>
