<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="itu.crypto.model.Contract" %>
<%@ page import="itu.crypto.model.Employe" %>
<%@ page import="itu.crypto.model.ContractType" %>
<%@ page import="itu.crypto.model.Quarter" %>
<%@ page import="itu.crypto.model.Role" %>
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
                    <div class="pay-slip">
                        <div class="header">
                            <h1>Fiche de Paie</h1>
                        </div>
                        <div class="content">
                            <div class="details-left">
                                <p>Nom et prénom : {{ nomPrenom }}</p>
                                <p>Matricule : {{ matricule }}</p>
                                <p>Fonction : {{ fonction }}</p>
                                <p>N° CNaPS : {{ cnaps }}</p>
                                <p>Date d'embauche : {{ dateEmbauche }}</p>
                                <p>Ancienneté : {{ anciennete }}</p>
                            </div>
                            <div class="details-right">
                                <p>Classification : {{ classification }}</p>
                                <p>Salaire de base : {{ salaireBase }}</p>
                                <p>Taux journalier : {{ tauxJournalier }}</p>
                                <p>Taux horaire : {{ tauxHoraire }}</p>
                                <p>Indice : {{ indice }}</p>
                            </div>
                        </div>
                        <hr>
                        <!-- Tableau des détails du salaire -->
                        <table>
                            <tr>
                                <th>Désignation</th>
                                <th>Nombre</th>
                                <th>Taux</th>
                                <th>Montant</th>
                            </tr>
                            <!-- Données dynamiques -->
                            {{#each details}}
                            <tr>
                                <td>{{ designation }}</td>
                                <td>{{ nombre }}</td>
                                <td>{{ taux }}</td>
                                <td>{{ montant }}</td>
                            </tr>
                            {{/each}}
                        </table>
                        <h5>Salaire brut : {{ salaireBrut }}</h5>
                        <h5>Total des retenues : {{ totalRetenues }}</h5>
                        <h5>Net à payer : {{ netAPayer }}</h5>
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
