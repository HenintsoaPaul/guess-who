<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="itu.crypto.model.Contract" %>
<%@ page import="itu.crypto.model.Employe" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Informations de l'Employé</title>
    <%@ include file="importation/link_header.jsp" %>
</head>
<body>
    <div class="container-scroller">
        <%@ include file="partial/navbar.jsp" %>
        <div class="container-fluid page-body-wrapper">
            <%@ include file="partial/sidebar.jsp" %>
            <div class="main-panel">
                <div class="content-wrapper">
                    <!-- Affichage des informations personnelles de l'employé -->
                    <div class="card">
                        <div class="card-header">
                            <h3>Informations personnelles</h3>
                        </div>
                        <div class="card-body">
                            <p><strong>Nom : </strong>${contract.employe.name}</p>
                            <p><strong>Prénom : </strong>${contract.employe.firstName}</p>
                            <p><strong>Email : </strong>${contract.employe.email}</p>
                            <p><strong>ID Employé : </strong>${contract.employe.idEmploye}</p>
                        </div>
                    </div>

                    <!-- Affichage des informations du contrat -->
                    <div class="card mt-4">
                        <div class="card-header">
                            <h3>Informations du contrat</h3>
                        </div>
                        <div class="card-body">
                            <p><strong>Nom du contrat : </strong>${contract.name}</p>
                            <p><strong>Date de début : </strong>${contract.startDate}</p>
                            <p><strong>Date de fin : </strong>${contract.endDate}</p>
                            <p><strong>Salaire : </strong>${contract.salary}</p>
                            <p><strong>Période d'essai : </strong>${contract.trial ? 'Oui' : 'Non'}</p>
                            <p><strong>Congés : </strong>${contract.leave}</p>
                            <p><strong>Type de contrat : </strong>${contract.contractType.desce}</p>
                            <p><strong>Rôle : </strong>${contract.role.title}</p>
                            <p>
                                <button type="button" class="btn bg-danger text-white my-4 mb-2" data-bs-toggle="modal" data-bs-target="#ruptureModal">
                                    Rupture de contrat
                                </button>
                            </p>
                        </div>
                    </div>
                </div>

                <%@ include file="partial/footer.jsp" %>
            </div>
        </div>
    </div>

    <!-- Modal Bootstrap -->
    <div class="modal fade" id="ruptureModal" tabindex="-1" aria-labelledby="ruptureModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="ruptureModalLabel">Confirmation de Rupture</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Êtes-vous sûr de vouloir rompre ce contrat ?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="button" class="btn btn-danger" onclick="window.location.href='/rupture?idEmp=${contract.employe.idEmploye}'">
                        Valider
                    </button>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="importation/link_footer.jsp" %>
    <!-- Inclure Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
