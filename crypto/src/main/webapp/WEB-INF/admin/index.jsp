<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    Integer totalEmployes = 500;
    Integer demandesConge = 120;
    Integer employesSansContrat = 20;

%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Gestion RH - Accueil</title>
    <%@ include file="importation/link_header.jsp" %>
</head>

<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <div class="col-12">
                        <p class="text-muted text-center mt-2">Aperçu des fonctionnalités principales de gestion des ressources humaines</p>

                        <!-- Section des cartes de navigation rapide -->
                        <div class="row mt-5">
                            <!-- Ruptures/Renouvellements de contrat -->
                            <div class="col-md-4">
                                <div class="card shadow mb-4">
                                    <div class="card-body text-center">
                                        <i class="fe fe-file-text fe-32 text-primary mb-3"></i>
                                        <h5 class="card-title">Employés</h5>
                                        <p class="text-muted">Suivez les informations à concernant les employées.</p>
                                        <a href="${pageContext.request.contextPath}/Contrats" class="btn btn-primary btn-sm">Voir les employés</a>
                                    </div>
                                </div>
                            </div>

                            <!-- Liste des employés ayant passé l'entretien -->
                            <div class="col-md-4">
                                <div class="card shadow mb-4">
                                    <div class="card-body text-center">
                                        <i class="fe fe-user-check fe-32 text-success mb-3"></i>
                                        <h5 class="card-title">Candidats sans contrat</h5>
                                        <p class="text-muted">Consultez les employés ayant passé un entretien.</p>
                                        <a href="${pageContext.request.contextPath}/Candidats" class="btn btn-success btn-sm">Voir les candidats</a>
                                    </div>
                                </div>
                            </div>

                            <!-- Demandes de congé -->
                            <div class="col-md-4">
                                <div class="card shadow mb-4">
                                    <div class="card-body text-center">
                                        <i class="fe fe-calendar fe-32 text-danger mb-3"></i>
                                        <h5 class="card-title">Demandes de congé</h5>
                                        <p class="text-muted">Gérez les demandes de congé des employés.</p>
                                        <a href="${pageContext.request.contextPath}/DemandesConge" class="btn btn-danger btn-sm">Gérer les demandes</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Section des statistiques -->
                        <div class="card shadow mt-4">
                            <div class="card-header">
                                <strong class="card-title">Statistiques RH</strong>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="statistic-box">
                                            <h4><%= totalEmployes %></h4>
                                            <p class="text-muted">Total des employés</p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="statistic-box">
                                            <h4><%= demandesConge %></h4>
                                            <p class="text-muted">Demandes de congé en attente</p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="statistic-box">
                                            <h4><%= employesSansContrat %></h4>
                                            <p class="text-muted">Candidats sans contrat</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> <!-- .col-12 -->
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
