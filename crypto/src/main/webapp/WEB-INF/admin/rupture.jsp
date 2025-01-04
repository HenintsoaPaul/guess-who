<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="itu.crypto.model.TerminationType" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Rupture de Contrat</title>
    <%@ include file="importation/link_header.jsp" %>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <h3 class="text-center mt-4">Rupture de Contrat</h3>
                    <p class="text-muted text-center">Effectuez une rupture de contrat pour un employé.</p>

                    <div class="row mt-5">
                        <div class="col-md-8 offset-md-2">
                            <div class="card shadow mb-4">
                                <div class="card-body">
                                    <form action="/ruptureConfirmation" method="post">
                                        <div class="form-group">
                                            <label for="termination_notice">Date de déclaration </label>
                                            <input type="date" id="termination_notice" name="termination_notice" class="form-control" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="termination_date">Date de Rupture</label>
                                            <input type="date" id="termination_date" name="termination_date" class="form-control" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="id_termination_type">Type de Rupture</label>
                                            <select id="id_termination_type" name="id_termination_type" class="form-control" required>
                                                <option value="" disabled selected>Choisissez un type de rupture</option>
                                                <%
                                                    for(TerminationType terminationType : (List<TerminationType>)(request.getAttribute("type_ruptures"))) {
                                                %>
                                                    <option value="<%= terminationType.getIdTerminationType() %>"> <%= terminationType.getDescription() %> </option>
                                                <%
                                                    }
                                                %>
                                                <!-- Ajouter dynamiquement les options selon les types de rupture disponibles -->
                                            </select>
                                            <input type="hidden" name="idEmp" value="${idEmp}">
                                        </div>

                                        <button type="submit" class="btn btn-danger btn-block"> Valider </button>
                                    </form>
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
