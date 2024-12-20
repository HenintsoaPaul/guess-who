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
                    <div class="col-lg-12 grid-margin stretch-card">
                      <div class="card">
                        <div class="card-body">
                          <h4 class="card-title">Liste des employées</h4>
                          <p class="card-description">
                            Cliquer sur <code>voir plus </code> pour voir plus d'information à propos de l'employée
                          </p>
                          <div class="table-responsive">
                            <table class="table table-striped">
                              <thead>
                                <tr>
                                  <th>
                                    Type de contrat
                                  </th>
                                  <th>
                                    Role
                                  </th>
                                  <th>
                                    Nom & prénom
                                  </th>
                                  <th>
                                    Salaire
                                  </th>
                                </tr>
                              </thead>
                              <tbody>
                                <%
                                    for( Contract contract : (List<Contract>)request.getAttribute("liste_employe") ){
                                %>
                                <tr>
                                    <td> <%= contract.getContractType().getDesce() %> </td>
                                    <td> <%= contract.getRole().getTitle() %> </td>
                                    <td> <%= contract.getEmploye().getFirstName() %> <%= " "%>  <%= contract.getEmploye().getName()%> </td>
                                    <td> <%= contract.getSalary()%> </td>
                                    <td> <a href="/PreInfEmploye?idContract=<%= contract.getIdContract() %>">Voir plus</a> </td>
                                </tr>
                                <%
                                    }
                                %>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
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
