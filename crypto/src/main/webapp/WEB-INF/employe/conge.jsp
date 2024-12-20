<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="importation/link_header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="itu.gestionrh.model.TypeLeave" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Demande de Congé</title>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <h3 class="text-center mt-4">Faire une demande de congé</h3>
                    <div class="col-8 mx-auto mt-4">
                        <form action="${pageContext.request.contextPath}/demandeCongeHandler" method="post">
                            <div class="form-group">
                                <label for="typeConge">Type de congé :</label>
                                <select class="form-control" id="typeConge" name="typeConge" required>
                                    <% 
                                        List<TypeLeave> typeLeaves = (List<TypeLeave>) request.getAttribute("typeleave");
                                            for (TypeLeave typeLeave : typeLeaves) { 
                                    %>
                                                <option value="<%= typeLeave.getIdTypeLeave() %>">
                                                    <%= typeLeave.getDescription() %>
                                                </option>
                                    <% 
                                            }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="dateDebut">Date de début :</label>
                                <input type="date" class="form-control" id="dateDebut" name="dateDebut" required>
                            </div>
                            <div class="form-group">
                                <label for="dateFin">Date de fin :</label>
                                <input type="date" class="form-control" id="dateFin" name="dateFin" required>
                            </div>
                            <button type="submit" class="btn btn-success btn-block">Envoyer la demande</button>
                        </form>
                    </div>
                </div>
                <%@ include file="partial/footer.jsp" %>
            </div>
        </div>
    </div>
    <%@ include file="importation/link_footer.jsp" %>
</body>

</html>
